package com.planbookai.taskservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonManagedReference;
/**
 * Entity DeThi - Đề thi (Aggregate Root)
 * Khớp với table assessment.exams
 */
@Entity
@Table(name = "exams", schema = "assessment")
public class DeThi {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "title", nullable = false, length = 255)
    private String tieuDe;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String moTa;
    
    @Column(name = "subject", nullable = false, length = 50)
    private String monHoc;
    
    @Column(name = "grade", nullable = false)
    private Integer khoi; // 10, 11, 12
    
    @Column(name = "duration_minutes", nullable = false)
    private Integer thoiGianLamBai; // 15-180 phút
    
    @Column(name = "total_score", precision = 4, scale = 2)
    private BigDecimal tongDiem = BigDecimal.valueOf(10.00);
    
    @Column(name = "teacher_id", nullable = false)
    private UUID giaoVienId;
    
    @Column(name = "status", length = 20)
    private String trangThai = "DRAFT";
    
    @Column(name = "created_at")
    private LocalDateTime thoiGianTao;
    
    @Column(name = "updated_at")
    private LocalDateTime thoiGianCapNhat;
    
    // Relationship với CauHoiTrongDeThi
    @OneToMany(mappedBy = "deThi", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("deThi-cauHoi")
    private List<CauHoiTrongDeThi> danhSachCauHoi;
    
    // Constructors
    public DeThi() {
        this.thoiGianTao = LocalDateTime.now();
        this.thoiGianCapNhat = LocalDateTime.now();
        this.trangThai = "DRAFT";
        this.tongDiem = BigDecimal.valueOf(10.00);
    }
    
    public DeThi(String tieuDe, String moTa, String monHoc, Integer khoi, 
                 Integer thoiGianLamBai, UUID giaoVienId) {
        this();
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.monHoc = monHoc;
        this.khoi = khoi;
        this.thoiGianLamBai = thoiGianLamBai;
        this.giaoVienId = giaoVienId;
    }
    
    // Business Methods
    public void themCauHoi(CauHoi cauHoi, Integer thuTu, BigDecimal diem) {
        // Validation
        if (!"DRAFT".equals(this.trangThai)) {
            throw new IllegalStateException("Không thể thêm câu hỏi vào đề thi đã phát hành");
        }
        
        if (diem.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Điểm phải lớn hơn 0");
        }
        
        // Khởi tạo danh sách nếu chưa có
        if (this.danhSachCauHoi == null) {
            this.danhSachCauHoi = new ArrayList<>();
        }
        
        // Kiểm tra trùng lặp câu hỏi
        boolean daTonTai = this.danhSachCauHoi.stream()
            .anyMatch(ch -> ch.getCauHoi().getId().equals(cauHoi.getId()));
        
        if (daTonTai) {
            throw new IllegalArgumentException("Câu hỏi này đã có trong đề thi");
        }
        
        // Kiểm tra trùng lặp thứ tự
        boolean thuTuDaTonTai = this.danhSachCauHoi.stream()
            .anyMatch(ch -> ch.getThuTu().equals(thuTu));
        
        if (thuTuDaTonTai) {
            throw new IllegalArgumentException("Thứ tự " + thuTu + " đã được sử dụng");
        }
        
        // Tạo và thêm CauHoiTrongDeThi mới
        CauHoiTrongDeThi cauHoiTrongDeThi = new CauHoiTrongDeThi(this, cauHoi, thuTu, diem);
        this.danhSachCauHoi.add(cauHoiTrongDeThi);
        
        // Cập nhật tổng điểm
        capNhatTongDiem();
        this.thoiGianCapNhat = LocalDateTime.now();
    }
    
    // Business method để xóa câu hỏi
    public void xoaCauHoi(UUID cauHoiId) {
        if (!"DRAFT".equals(this.trangThai)) {
            throw new IllegalStateException("Không thể xóa câu hỏi khỏi đề thi đã phát hành");
        }
        
        if (this.danhSachCauHoi != null) {
            this.danhSachCauHoi.removeIf(ch -> ch.getCauHoi().getId().equals(cauHoiId));
            capNhatTongDiem();
            this.thoiGianCapNhat = LocalDateTime.now();
        }
    }
    
    // Business method để cập nhật thứ tự câu hỏi
    public void capNhatThuTuCauHoi(UUID cauHoiId, Integer thuTuMoi) {
        if (!"DRAFT".equals(this.trangThai)) {
            throw new IllegalStateException("Không thể cập nhật thứ tự khi đề thi đã phát hành");
        }
        
        // Kiểm tra thứ tự mới không trùng
        boolean thuTuDaTonTai = this.danhSachCauHoi.stream()
            .anyMatch(ch -> ch.getThuTu().equals(thuTuMoi) && 
                           !ch.getCauHoi().getId().equals(cauHoiId));
        
        if (thuTuDaTonTai) {
            throw new IllegalArgumentException("Thứ tự " + thuTuMoi + " đã được sử dụng");
        }
        
        // Cập nhật thứ tự
        this.danhSachCauHoi.stream()
            .filter(ch -> ch.getCauHoi().getId().equals(cauHoiId))
            .findFirst()
            .ifPresent(ch -> ch.capNhatThuTu(thuTuMoi));
        
        this.thoiGianCapNhat = LocalDateTime.now();
    }
    
    // Business method để lấy số câu hỏi hiện tại
    public int getSoCauHoi() {
        return this.danhSachCauHoi != null ? this.danhSachCauHoi.size() : 0;
    }
    
    public void capNhatTrangThai(String trangThaiMoi) {
        // Business rules for status transition
        if ("PUBLISHED".equals(trangThaiMoi) && !coTheDuocPheDuyet()) {
            throw new IllegalStateException("Đề thi chưa đủ điều kiện để phát hành");
        }
        
        this.trangThai = trangThaiMoi;
        this.thoiGianCapNhat = LocalDateTime.now();
    }
    
    public boolean coTheDuocPheDuyet() {
        return "DRAFT".equals(this.trangThai) 
            && this.danhSachCauHoi != null 
            && this.danhSachCauHoi.size() >= 10; // Tối thiểu 10 câu
    }
    
    public void capNhatTongDiem() {
        if (this.danhSachCauHoi != null) {
            BigDecimal tongDiemMoi = this.danhSachCauHoi.stream()
                .map(CauHoiTrongDeThi::getDiem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            this.tongDiem = tongDiemMoi;
            this.thoiGianCapNhat = LocalDateTime.now();
        }
    }
    
    public boolean coValidThoiGianLamBai() {
        return this.thoiGianLamBai >= 15 && this.thoiGianLamBai <= 180;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }
    
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    
    public String getMonHoc() { return monHoc; }
    public void setMonHoc(String monHoc) { this.monHoc = monHoc; }
    
    public Integer getKhoi() { return khoi; }
    public void setKhoi(Integer khoi) { this.khoi = khoi; }
    
    public Integer getThoiGianLamBai() { return thoiGianLamBai; }
    public void setThoiGianLamBai(Integer thoiGianLamBai) { this.thoiGianLamBai = thoiGianLamBai; }
    
    public BigDecimal getTongDiem() { return tongDiem; }
    public void setTongDiem(BigDecimal tongDiem) { this.tongDiem = tongDiem; }
    
    public UUID getGiaoVienId() { return giaoVienId; }
    public void setGiaoVienId(UUID giaoVienId) { this.giaoVienId = giaoVienId; }
    
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    
    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    
    public LocalDateTime getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(LocalDateTime thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }
    
    public List<CauHoiTrongDeThi> getDanhSachCauHoi() { return danhSachCauHoi; }
    public void setDanhSachCauHoi(List<CauHoiTrongDeThi> danhSachCauHoi) { this.danhSachCauHoi = danhSachCauHoi; }
}
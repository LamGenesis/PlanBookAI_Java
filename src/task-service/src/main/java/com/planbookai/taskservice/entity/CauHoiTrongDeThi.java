package com.planbookai.taskservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name = "exam_questions", schema = "assessment",
       uniqueConstraints = @UniqueConstraint(columnNames = {"exam_id", "question_order"}))
public class CauHoiTrongDeThi {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    @JsonBackReference("deThi-cauHoi")
    private DeThi deThi;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private CauHoi cauHoi;
    
    @Column(name = "question_order", nullable = false)
    private Integer thuTu;
    
    @Column(name = "points", precision = 4, scale = 2, nullable = false)
    private BigDecimal diem;
    
    @Column(name = "created_at")
    private LocalDateTime thoiGianTao;
    
    public CauHoiTrongDeThi() {
        this.thoiGianTao = LocalDateTime.now();
    }
    
    public CauHoiTrongDeThi(DeThi deThi, CauHoi cauHoi, Integer thuTu, BigDecimal diem) {
        this();
        this.deThi = deThi;
        this.cauHoi = cauHoi;
        this.thuTu = thuTu;
        this.diem = diem;
        
        // Business validation
        if (diem.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Điểm phải lớn hơn 0");
        }
        
        // Validation 1: Kiểm tra thứ tự hợp lệ
        if (thuTu <= 0) {
            throw new IllegalArgumentException("Thứ tự câu hỏi phải lớn hơn 0");
        }
        
        // Validation 2: Kiểm tra điểm không quá 10.0
        if (diem.compareTo(BigDecimal.valueOf(10.0)) > 0) {
            throw new IllegalArgumentException("Điểm một câu không được vượt quá 10.0");
        }
        
        // Validation 3: Kiểm tra đề thi ở trạng thái có thể chỉnh sửa
        if (deThi != null && !"DRAFT".equals(deThi.getTrangThai())) {
            throw new IllegalStateException("Chỉ có thể thêm câu hỏi vào đề thi ở trạng thái DRAFT");
        }
    }
    
    public boolean laDiemHopLe() {
        return this.diem.compareTo(BigDecimal.ZERO) > 0 && 
               this.diem.compareTo(BigDecimal.valueOf(10)) <= 0;
    }
    
    // Business method để kiểm tra tính hợp lệ tổng thể
    public boolean laHopLe() {
        return laDiemHopLe() && 
               laThuTuHopLe() && 
               laTrangThaiDeThiHopLe();
    }
    
    // Kiểm tra thứ tự hợp lệ
    public boolean laThuTuHopLe() {
        return this.thuTu != null && this.thuTu > 0;
    }
    
    // Kiểm tra trạng thái đề thi cho phép chỉnh sửa
    public boolean laTrangThaiDeThiHopLe() {
        return this.deThi == null || "DRAFT".equals(this.deThi.getTrangThai());
    }
    
    // Validation cho việc cập nhật điểm
    public void capNhatDiem(BigDecimal diemMoi) {
        if (diemMoi.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Điểm phải lớn hơn 0");
        }
        
        if (diemMoi.compareTo(BigDecimal.valueOf(10.0)) > 0) {
            throw new IllegalArgumentException("Điểm một câu không được vượt quá 10.0");
        }
        
        if (!laTrangThaiDeThiHopLe()) {
            throw new IllegalStateException("Không thể cập nhật điểm khi đề thi không ở trạng thái DRAFT");
        }
        
        this.diem = diemMoi;
    }
    
    // Validation cho việc cập nhật thứ tự
    public void capNhatThuTu(Integer thuTuMoi) {
        if (thuTuMoi <= 0) {
            throw new IllegalArgumentException("Thứ tự câu hỏi phải lớn hơn 0");
        }
        
        if (!laTrangThaiDeThiHopLe()) {
            throw new IllegalStateException("Không thể cập nhật thứ tự khi đề thi không ở trạng thái DRAFT");
        }
        
        this.thuTu = thuTuMoi;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public DeThi getDeThi() { return deThi; }
    public void setDeThi(DeThi deThi) { this.deThi = deThi; }
    
    public CauHoi getCauHoi() { return cauHoi; }
    public void setCauHoi(CauHoi cauHoi) { this.cauHoi = cauHoi; }
    
    public Integer getThuTu() { return thuTu; }
    public void setThuTu(Integer thuTu) { this.thuTu = thuTu; }
    
    public BigDecimal getDiem() { return diem; }
    public void setDiem(BigDecimal diem) { this.diem = diem; }
    
    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }
}
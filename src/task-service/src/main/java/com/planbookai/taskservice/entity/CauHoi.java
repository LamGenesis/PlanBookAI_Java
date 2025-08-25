package com.planbookai.taskservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity CauHoi - Câu hỏi trong ngân hàng câu hỏi
 * Khớp chính xác với table assessment.questions
 */
@Entity
@Table(name = "questions", schema = "assessment")
public class CauHoi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String noiDung;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LoaiCauHoi loai;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private MucDoKho mucDoKho;
    
    @Column(name = "subject", nullable = false, length = 50)
    private String monHoc;
    
    @Column(name = "topic", length = 255)
    private String chuDe;
    
    @Column(name = "correct_answer", length = 10)
    private String dapAnDung;
    
    @Column(name = "explanation", columnDefinition = "TEXT")
    private String giaiThich;
    
    @Column(name = "created_by", nullable = false)
    private UUID nguoiTao;
    
    @Column(name = "status", length = 20)
    private String trangThai = "ACTIVE";
    
    @Column(name = "created_at")
    private LocalDateTime thoiGianTao;
    
    @Column(name = "updated_at")  
    private LocalDateTime thoiGianCapNhat;
    
    // Relationship với LuaChonCauHoi (question_choices)
    @OneToMany(mappedBy = "cauHoi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("cauHoi-luaChon")
    private List<LuaChonCauHoi> danhSachLuaChon;
    
    // Constructors
    public CauHoi() {
        this.thoiGianTao = LocalDateTime.now();
        this.thoiGianCapNhat = LocalDateTime.now();
        this.trangThai = "ACTIVE";
    }
    
    public CauHoi(String noiDung, LoaiCauHoi loai, MucDoKho mucDoKho, String monHoc) {
        this();
        this.noiDung = noiDung;
        this.loai = loai;
        this.mucDoKho = mucDoKho;
        this.monHoc = monHoc;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { 
        this.noiDung = noiDung;
        this.thoiGianCapNhat = LocalDateTime.now();
    }
    
    public LoaiCauHoi getLoai() { return loai; }
    public void setLoai(LoaiCauHoi loai) { this.loai = loai; }
    
    public MucDoKho getMucDoKho() { return mucDoKho; }
    public void setMucDoKho(MucDoKho mucDoKho) { this.mucDoKho = mucDoKho; }
    
    public String getMonHoc() { return monHoc; }
    public void setMonHoc(String monHoc) { this.monHoc = monHoc; }
    
    public String getChuDe() { return chuDe; }
    public void setChuDe(String chuDe) { this.chuDe = chuDe; }
    
    public String getDapAnDung() { return dapAnDung; }
    public void setDapAnDung(String dapAnDung) { this.dapAnDung = dapAnDung; }
    
    public String getGiaiThich() { return giaiThich; }
    public void setGiaiThich(String giaiThich) { this.giaiThich = giaiThich; }
    
    public UUID getNguoiTao() { return nguoiTao; }
    public void setNguoiTao(UUID nguoiTao) { this.nguoiTao = nguoiTao; }
    
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    
    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    
    public LocalDateTime getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(LocalDateTime thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }
    
    public List<LuaChonCauHoi> getDanhSachLuaChon() { return danhSachLuaChon; }
    public void setDanhSachLuaChon(List<LuaChonCauHoi> danhSachLuaChon) { this.danhSachLuaChon = danhSachLuaChon; }
    
    // Business methods theo document
    public boolean kiemTraDapAn(String dapAn) {
        return this.dapAnDung != null && this.dapAnDung.equals(dapAn);
    }
    
    public void capNhatNoiDung(String noiDungMoi) {
        this.noiDung = noiDungMoi;
        this.thoiGianCapNhat = LocalDateTime.now();
    }
}
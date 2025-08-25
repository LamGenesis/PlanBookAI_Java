package com.planbookai.taskservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.annotation.JsonBackReference;
/**
 * Entity LuaChonCauHoi - Lựa chọn cho câu hỏi trắc nghiệm
 * Khớp chính xác với table assessment.question_choices
 */
@Entity
@Table(name = "question_choices", schema = "assessment")
public class LuaChonCauHoi {
    
    @Id
    @Column(name = "id")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference("cauHoi-luaChon")
    private CauHoi cauHoi;
    
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "choice_order", nullable = false,  length = 1)
	private String thuTu; // A, B, C, D
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String noiDung;
    
    @Column(name = "created_at")
    private LocalDateTime thoiGianTao;
    
    // Constructors
    public LuaChonCauHoi() {
        this.id = UUID.randomUUID();
        this.thoiGianTao = LocalDateTime.now();
    }
    
    public LuaChonCauHoi(CauHoi cauHoi, String thuTu, String noiDung) {
        this();
        this.cauHoi = cauHoi;
        this.thuTu = thuTu;
        this.noiDung = noiDung;
        validateThuTu();
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public CauHoi getCauHoi() { return cauHoi; }
    public void setCauHoi(CauHoi cauHoi) { this.cauHoi = cauHoi; }
    
    public String getThuTu() { return thuTu; }
    public void setThuTu(String thuTu) { 
        this.thuTu = thuTu;
        validateThuTu();
    }
    
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
    
    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    
    // Business methods
    private void validateThuTu() {
        if (thuTu != null && !thuTu.matches("[ABCD]")) {
            throw new IllegalArgumentException("Thứ tự lựa chọn chỉ được là A, B, C hoặc D");
        }
    }
    
    public boolean laDapAnDung() {
        return cauHoi != null && thuTu != null && thuTu.equals(cauHoi.getDapAnDung());
    }
    
    public String getThongTinDayDu() {
        return thuTu + ". " + noiDung;
    }
    
    @Override
    public String toString() {
        return getThongTinDayDu();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LuaChonCauHoi that = (LuaChonCauHoi) obj;
        return id != null && id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
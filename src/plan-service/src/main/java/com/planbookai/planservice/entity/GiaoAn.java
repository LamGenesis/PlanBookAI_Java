package com.planbookai.planservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode;
import java.util.UUID;

@Entity
@Table(name = "lesson_plans", schema = "content")
public class GiaoAn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String objectives;
    
    @Column(columnDefinition = "JSONB", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String content;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonHoc subject;
    
    @Column(nullable = false)
    private Integer grade;
    
    @Column(name = "teacher_id", nullable = false)
    private UUID teacherId;
    
    @Column(name = "template_id")
    private UUID templateId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrangThaiGiaoAn status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public GiaoAn() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = TrangThaiGiaoAn.DRAFT;
    }
    
    public GiaoAn(String title, String objectives, String content, 
                  MonHoc subject, Integer grade, UUID teacherId) {
        this();
        this.title = title;
        this.objectives = objectives;
        this.content = content;
        this.subject = subject;
        this.grade = grade;
        this.teacherId = teacherId;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getObjectives() {
        return objectives;
    }
    
    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public MonHoc getSubject() {
        return subject;
    }
    
    public void setSubject(MonHoc subject) {
        this.subject = subject;
    }
    
    public Integer getGrade() {
        return grade;
    }
    
    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    
    public UUID getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }
    
    public UUID getTemplateId() {
        return templateId;
    }
    
    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }
    
    public TrangThaiGiaoAn getStatus() {
        return status;
    }
    
    public void setStatus(TrangThaiGiaoAn status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Business methods
    public void taoTuMau(UUID templateId) {
        this.templateId = templateId;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void capNhatNoiDung(String title, String objectives, String content) {
        this.title = title;
        this.objectives = objectives;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void pheDuyet() {
        this.status = TrangThaiGiaoAn.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void danhDauDaSuDung() {
        this.status = TrangThaiGiaoAn.USED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void quayVeBanNhap() {
        this.status = TrangThaiGiaoAn.DRAFT;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean coTheChinhSua() {
        return this.status == TrangThaiGiaoAn.DRAFT;
    }
    
    public boolean daHoanThanh() {
        return this.status == TrangThaiGiaoAn.COMPLETED;
    }
}
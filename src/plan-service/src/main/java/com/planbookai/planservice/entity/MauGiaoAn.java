package com.planbookai.planservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lesson_templates", schema = "content")
public class MauGiaoAn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "template_content", columnDefinition = "JSONB", nullable = false)
    private String templateContent;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonHoc subject;
    
    @Column(nullable = false)
    private Integer grade;
    
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public MauGiaoAn() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public MauGiaoAn(String title, String description, String templateContent, 
                     MonHoc subject, Integer grade, UUID createdBy) {
        this();
        this.title = title;
        this.description = description;
        this.templateContent = templateContent;
        this.subject = subject;
        this.grade = grade;
        this.createdBy = createdBy;
        this.status = "ACTIVE";
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getTemplateContent() {
        return templateContent;
    }
    
    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
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
    
    public UUID getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
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
    public void capNhatNoiDung(String title, String description, String templateContent) {
        this.title = title;
        this.description = description;
        this.templateContent = templateContent;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void vohieuHoa() {
        this.status = "INACTIVE";
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean laHoatDong() {
        return "ACTIVE".equals(this.status);
    }
}

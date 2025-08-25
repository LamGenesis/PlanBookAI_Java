package com.planbookai.planservice.repository;

import com.planbookai.planservice.entity.MauGiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MauGiaoAnRepository extends JpaRepository<MauGiaoAn, UUID> {
    
    List<MauGiaoAn> findBySubject(MonHoc subject);
    
    List<MauGiaoAn> findByGrade(Integer grade);
    
    List<MauGiaoAn> findBySubjectAndGrade(MonHoc subject, Integer grade);
    
    List<MauGiaoAn> findByCreatedBy(UUID createdBy);
    
    List<MauGiaoAn> findByStatus(String status);
    
    @Query("SELECT m FROM MauGiaoAn m WHERE m.subject = :subject AND m.grade = :grade AND m.status = 'ACTIVE'")
    List<MauGiaoAn> findActiveTemplatesBySubjectAndGrade(@Param("subject") MonHoc subject, @Param("grade") Integer grade);
    
    @Query("SELECT m FROM MauGiaoAn m WHERE m.title LIKE %:keyword% OR m.description LIKE %:keyword%")
    List<MauGiaoAn> findByKeyword(@Param("keyword") String keyword);
}
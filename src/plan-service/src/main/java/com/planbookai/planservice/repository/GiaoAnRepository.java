package com.planbookai.planservice.repository;

import com.planbookai.planservice.entity.GiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import com.planbookai.planservice.entity.TrangThaiGiaoAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface GiaoAnRepository extends JpaRepository<GiaoAn, UUID> {
    
    List<GiaoAn> findByTeacherId(UUID teacherId);
    
    List<GiaoAn> findBySubject(MonHoc subject);
    
    List<GiaoAn> findByGrade(Integer grade);
    
    List<GiaoAn> findBySubjectAndGrade(MonHoc subject, Integer grade);
    
    List<GiaoAn> findByStatus(TrangThaiGiaoAn status);
    
    List<GiaoAn> findByTeacherIdAndStatus(UUID teacherId, TrangThaiGiaoAn status);
    
    @Query("SELECT g FROM GiaoAn g WHERE g.teacherId = :teacherId AND g.createdAt BETWEEN :startDate AND :endDate")
    List<GiaoAn> findByTeacherIdAndDateRange(@Param("teacherId") UUID teacherId, 
                                            @Param("startDate") LocalDateTime startDate, 
                                            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT g FROM GiaoAn g WHERE g.title LIKE %:keyword% OR g.objectives LIKE %:keyword%")
    List<GiaoAn> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT g FROM GiaoAn g WHERE g.teacherId = :teacherId AND (g.title LIKE %:keyword% OR g.objectives LIKE %:keyword%)")
    List<GiaoAn> findByTeacherIdAndKeyword(@Param("teacherId") UUID teacherId, @Param("keyword") String keyword);
    
    @Query("SELECT COUNT(g) FROM GiaoAn g WHERE g.teacherId = :teacherId AND g.status = :status")
    Long countByTeacherIdAndStatus(@Param("teacherId") UUID teacherId, @Param("status") TrangThaiGiaoAn status);
}
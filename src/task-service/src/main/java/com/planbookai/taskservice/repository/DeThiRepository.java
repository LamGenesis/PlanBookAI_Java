package com.planbookai.taskservice.repository;

import com.planbookai.taskservice.entity.DeThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface cho DeThi entity
 * Data access layer cho assessment.exams table
 */
@Repository
public interface DeThiRepository extends JpaRepository<DeThi, UUID> {
    
    // Tìm đề thi theo giáo viên và trạng thái
    List<DeThi> findByGiaoVienIdAndTrangThai(UUID giaoVienId, String trangThai);
    
    // Tìm đề thi theo môn học và khối
    List<DeThi> findByMonHocAndKhoi(String monHoc, Integer khoi);
    
    // Tìm đề thi theo giáo viên, môn học và trạng thái
    List<DeThi> findByGiaoVienIdAndMonHocAndTrangThai(UUID giaoVienId, String monHoc, String trangThai);
    
    // Tìm đề thi theo trạng thái
    List<DeThi> findByTrangThai(String trangThai);
    
    // Tìm đề thi theo giáo viên
    List<DeThi> findByGiaoVienId(UUID giaoVienId);
    
    // Custom query - Đếm số đề thi theo trạng thái
    @Query("SELECT COUNT(d) FROM DeThi d WHERE d.trangThai = :trangThai")
    Long countByTrangThai(@Param("trangThai") String trangThai);
    
    // Custom query - Tìm đề thi có thể sử dụng (PUBLISHED)
    @Query("SELECT d FROM DeThi d WHERE d.trangThai = 'PUBLISHED' AND d.monHoc = :monHoc ORDER BY d.thoiGianTao DESC")
    List<DeThi> findActiveExamsBySubject(@Param("monHoc") String monHoc);
    
    // Custom query - Tìm đề thi theo khoảng thời gian tạo
    @Query("SELECT d FROM DeThi d WHERE d.thoiGianTao BETWEEN :startDate AND :endDate ORDER BY d.thoiGianTao DESC")
    List<DeThi> findByCreatedDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // Custom query - Đếm đề thi theo giáo viên và trạng thái
    @Query("SELECT COUNT(d) FROM DeThi d WHERE d.giaoVienId = :giaoVienId AND d.trangThai = :trangThai")
    Long countByGiaoVienIdAndTrangThai(@Param("giaoVienId") UUID giaoVienId, @Param("trangThai") String trangThai);
    
    // Custom query - Tìm đề thi theo thời gian làm bài
    @Query("SELECT d FROM DeThi d WHERE d.thoiGianLamBai BETWEEN :minDuration AND :maxDuration AND d.trangThai = 'PUBLISHED'")
    List<DeThi> findByDurationRange(@Param("minDuration") Integer minDuration, @Param("maxDuration") Integer maxDuration);
    
    // Custom query - Tìm đề thi có tổng điểm cụ thể
    @Query("SELECT d FROM DeThi d WHERE d.tongDiem = :tongDiem AND d.trangThai = 'PUBLISHED'")
    List<DeThi> findByTotalScore(@Param("tongDiem") BigDecimal tongDiem);
    
    // Custom query - Thống kê đề thi theo môn học
    @Query("SELECT d.monHoc, COUNT(d) FROM DeThi d WHERE d.trangThai = 'PUBLISHED' GROUP BY d.monHoc")
    List<Object[]> getExamStatisticsBySubject();
    
    // Custom query - Tìm đề thi mới nhất của giáo viên
    @Query("SELECT d FROM DeThi d WHERE d.giaoVienId = :giaoVienId ORDER BY d.thoiGianCapNhat DESC")
    List<DeThi> findLatestByTeacher(@Param("giaoVienId") UUID giaoVienId);
    
    // Kiểm tra sự tồn tại đề thi theo tiêu đề và giáo viên
    boolean existsByTieuDeAndGiaoVienId(String tieuDe, UUID giaoVienId);
}
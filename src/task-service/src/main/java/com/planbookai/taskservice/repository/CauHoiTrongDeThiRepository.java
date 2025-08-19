package com.planbookai.taskservice.repository;

import com.planbookai.taskservice.entity.CauHoiTrongDeThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface cho CauHoiTrongDeThi entity
 * Data access layer cho assessment.exam_questions table
 */
@Repository
public interface CauHoiTrongDeThiRepository extends JpaRepository<CauHoiTrongDeThi, UUID> {
    
    // Lấy tất cả câu hỏi trong đề thi, sắp xếp theo thứ tự
    @Query("SELECT c FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId ORDER BY c.thuTu")
    List<CauHoiTrongDeThi> findByDeThiIdOrderByThuTu(@Param("deThiId") UUID deThiId);
    
    // Tìm câu hỏi trong đề thi theo thứ tự cụ thể
    @Query("SELECT c FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId AND c.thuTu = :thuTu")
    Optional<CauHoiTrongDeThi> findByDeThiIdAndThuTu(@Param("deThiId") UUID deThiId, @Param("thuTu") Integer thuTu);
    
    // Tìm theo exam và question để kiểm tra duplicate
    @Query("SELECT c FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId AND c.cauHoi.id = :cauHoiId")
    Optional<CauHoiTrongDeThi> findByDeThiIdAndCauHoiId(@Param("deThiId") UUID deThiId, @Param("cauHoiId") UUID cauHoiId);
    
    // Đếm số câu hỏi trong đề thi
    @Query("SELECT COUNT(c) FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId")
    Long countByDeThiId(@Param("deThiId") UUID deThiId);
    
    // Tính tổng điểm của đề thi
    @Query("SELECT SUM(c.diem) FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId")
    BigDecimal sumPointsByDeThiId(@Param("deThiId") UUID deThiId);
    
    // Lấy câu hỏi theo khoảng thứ tự
    @Query("SELECT c FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId AND c.thuTu BETWEEN :startOrder AND :endOrder ORDER BY c.thuTu")
    List<CauHoiTrongDeThi> findByDeThiIdAndThuTuRange(@Param("deThiId") UUID deThiId, 
                                                     @Param("startOrder") Integer startOrder, 
                                                     @Param("endOrder") Integer endOrder);
    
    // Kiểm tra sự tồn tại của câu hỏi trong đề thi
    boolean existsByDeThiIdAndCauHoiId(UUID deThiId, UUID cauHoiId);
    
    // Kiểm tra sự tồn tại của thứ tự trong đề thi
    boolean existsByDeThiIdAndThuTu(UUID deThiId, Integer thuTu);
    
    // Lấy thứ tự cao nhất trong đề thi
    @Query("SELECT MAX(c.thuTu) FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId")
    Integer findMaxThuTuByDeThiId(@Param("deThiId") UUID deThiId);
    
    // Lấy các câu hỏi có thứ tự lớn hơn một số cụ thể
    @Query("SELECT c FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId AND c.thuTu > :thuTu ORDER BY c.thuTu")
    List<CauHoiTrongDeThi> findByDeThiIdAndThuTuGreaterThan(@Param("deThiId") UUID deThiId, @Param("thuTu") Integer thuTu);
    
    // Xóa tất cả câu hỏi của một đề thi
    void deleteByDeThiId(UUID deThiId);
    
    // Lấy thống kê điểm theo đề thi
    @Query("SELECT MIN(c.diem), MAX(c.diem), AVG(c.diem) FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId")
    Object[] getScoreStatsByDeThiId(@Param("deThiId") UUID deThiId);
    
    // Tìm câu hỏi theo điểm số cụ thể
    @Query("SELECT c FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId AND c.diem = :diem ORDER BY c.thuTu")
    List<CauHoiTrongDeThi> findByDeThiIdAndDiem(@Param("deThiId") UUID deThiId, @Param("diem") BigDecimal diem);
    
    // Tìm câu hỏi trong khoảng điểm
    @Query("SELECT c FROM CauHoiTrongDeThi c WHERE c.deThi.id = :deThiId AND c.diem BETWEEN :minDiem AND :maxDiem ORDER BY c.thuTu")
    List<CauHoiTrongDeThi> findByDeThiIdAndDiemRange(@Param("deThiId") UUID deThiId, 
                                                    @Param("minDiem") BigDecimal minDiem, 
                                                    @Param("maxDiem") BigDecimal maxDiem);
    
    // Lấy danh sách exam_id từ question_id (để tìm đề thi chứa câu hỏi cụ thể)
    @Query("SELECT DISTINCT c.deThi.id FROM CauHoiTrongDeThi c WHERE c.cauHoi.id = :cauHoiId")
    List<UUID> findDeThiIdsByCauHoiId(@Param("cauHoiId") UUID cauHoiId);
}
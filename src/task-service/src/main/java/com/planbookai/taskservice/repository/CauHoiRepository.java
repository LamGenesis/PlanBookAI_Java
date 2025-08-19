package com.planbookai.taskservice.repository;

import com.planbookai.taskservice.entity.CauHoi;
import com.planbookai.taskservice.entity.MucDoKho;
import com.planbookai.taskservice.entity.LoaiCauHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface cho CauHoi entity
 * Data access layer cho assessment.questions table
 */
@Repository
public interface CauHoiRepository extends JpaRepository<CauHoi, UUID> {
    
    // Tìm câu hỏi theo môn học và độ khó
    List<CauHoi> findByMonHocAndMucDoKho(String monHoc, MucDoKho mucDoKho);
    
    // Tìm câu hỏi theo loại và chủ đề
    List<CauHoi> findByLoaiAndChuDe(LoaiCauHoi loai, String chuDe);
    
    // Tìm câu hỏi theo môn học và trạng thái
    List<CauHoi> findByMonHocAndTrangThai(String monHoc, String trangThai);
    
    // Tìm câu hỏi theo người tạo
    List<CauHoi> findByNguoiTao(UUID nguoiTao);
    
    // Custom query - Tìm câu hỏi cho tạo đề thi ngẫu nhiên
    @Query("SELECT c FROM CauHoi c WHERE c.monHoc = :monHoc AND c.mucDoKho = :mucDoKho AND c.trangThai = 'ACTIVE' ORDER BY FUNCTION('RANDOM')")
    List<CauHoi> findRandomQuestions(@Param("monHoc") String monHoc, @Param("mucDoKho") MucDoKho mucDoKho);
    
    // Custom query - Đếm câu hỏi theo người tạo và trạng thái
    @Query("SELECT COUNT(c) FROM CauHoi c WHERE c.nguoiTao = :nguoiTao AND c.trangThai = :trangThai")
    Long countByNguoiTaoAndTrangThai(@Param("nguoiTao") UUID nguoiTao, @Param("trangThai") String trangThai);
    
    // Custom query - Tìm câu hỏi theo nhiều tiêu chí
    @Query("SELECT c FROM CauHoi c WHERE c.monHoc = :monHoc AND (:loai IS NULL OR c.loai = :loai) AND (:mucDoKho IS NULL OR c.mucDoKho = :mucDoKho) AND c.trangThai = 'ACTIVE'")
    List<CauHoi> findByMultipleCriteria(@Param("monHoc") String monHoc, 
                                       @Param("loai") LoaiCauHoi loai, 
                                       @Param("mucDoKho") MucDoKho mucDoKho);
    
    // Đếm câu hỏi theo môn học
    Long countByMonHoc(String monHoc);
}
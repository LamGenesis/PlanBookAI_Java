package com.planbookai.userservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planbookai.userservice.entity.ThongTinNguoiDung;

public interface KhoThongTinNguoiDung extends JpaRepository<ThongTinNguoiDung, Long> {
    Optional<ThongTinNguoiDung> findByEmail(String email);

    Page<ThongTinNguoiDung> findByHoTenContainingIgnoreCase(String tuKhoa, Pageable pageable);

    Page<ThongTinNguoiDung> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    @Query("SELECT u FROM ThongTinNguoiDung u " +
       "WHERE lower(u.hoTen) LIKE lower(concat('%', :keyword, '%')) " +
       "   OR lower(u.email) LIKE lower(concat('%', :keyword, '%'))")
       Page<ThongTinNguoiDung> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}

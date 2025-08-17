package com.planbookai.userservice.repository;

import com.planbookai.userservice.entity.LichSuHoatDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhoLichSuHoatDong extends JpaRepository<LichSuHoatDong, Long> {
    List<LichSuHoatDong> findByNguoiDungId(Long nguoiDungId);
}

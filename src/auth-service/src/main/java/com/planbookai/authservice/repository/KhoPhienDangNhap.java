package com.planbookai.authservice.repository;

import com.planbookai.authservice.entity.PhienDangNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface KhoPhienDangNhap extends JpaRepository<PhienDangNhap, Long> {
    Optional<PhienDangNhap> findByRefreshToken(String refreshToken);
    List<PhienDangNhap> findByNguoiDungId(Long nguoiDungId);
    void deleteByRefreshToken(String refreshToken);
}

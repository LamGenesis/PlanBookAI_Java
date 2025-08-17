package com.planbookai.authservice.repository;

import com.planbookai.authservice.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KhoNguoiDung extends JpaRepository<NguoiDung, Long> {
    Optional<NguoiDung> findByEmail(String email);
    boolean existsByEmail(String email);
}

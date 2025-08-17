package com.planbookai.authservice.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "login_sessions", schema = "users")
public class PhienDangNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long nguoiDungId;

    @Column(length = 2048)
    private String refreshToken;

    private LocalDateTime thoiGianTao;

    private LocalDateTime thoiGianHetHan;

    @PrePersist
    public void prePersist() {
        thoiGianTao = LocalDateTime.now();
    }
}

package com.planbookai.authservice.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "users")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String matKhauMaHoa;

    @Column(nullable = false)
    private String hoTen;

    @Enumerated(EnumType.STRING)
    private VaiTro vaiTro; // ADMIN, MANAGER, STAFF, TEACHER

    @Column(nullable = false)
    @Builder.Default
    private Boolean trangThaiHoatDong = true;

    private LocalDateTime thoiGianTao;
    private LocalDateTime thoiGianCapNhat;

    @PrePersist
    public void prePersist() {
        thoiGianTao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        thoiGianCapNhat = LocalDateTime.now();
    }
}

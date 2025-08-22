package com.planbookai.userservice.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nguoi_dung")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ThongTinNguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hoTen;

    @Column(unique = true, nullable = false)
    private String email;

    private String matKhau;
    private LocalDateTime thoiGianCapNhat;

    @Builder.Default
    private Boolean trangThai = true;

    private String vaiTro;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LichSuHoatDong> lichSuHoatDong = new ArrayList<>();

    @OneToOne(mappedBy = "nguoiDung", cascade = CascadeType.ALL, orphanRemoval = true)
    private CauHinhCaNhan cauHinhCaNhan;
}


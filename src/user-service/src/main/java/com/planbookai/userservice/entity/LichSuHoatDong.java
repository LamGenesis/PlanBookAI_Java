package com.planbookai.userservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lich_su")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LichSuHoatDong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hanhDong;

    private LocalDateTime thoiGian;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private ThongTinNguoiDung nguoiDung;
}


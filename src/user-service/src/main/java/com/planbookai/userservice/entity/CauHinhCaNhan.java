package com.planbookai.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cau_hinh")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CauHinhCaNhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ngonNgu;

    private String theme;

    @OneToOne
    @JoinColumn(name = "nguoi_dung_id")
    private ThongTinNguoiDung nguoiDung;
}


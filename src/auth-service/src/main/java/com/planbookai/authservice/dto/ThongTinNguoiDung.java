package com.planbookai.authservice.dto;

import com.planbookai.authservice.entity.VaiTro;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongTinNguoiDung {
    private Long id;
    private String email;
    private String hoTen;
    private VaiTro vaiTro;
    private Boolean trangThaiHoatDong;
}

package com.planbookai.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YeuCauTaoNguoiDung {
    @NotBlank
    private String hoTen;
    @Email
    private String email;
    @NotBlank
    private String matKhau;
    private String vaiTro;
}


package com.planbookai.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class YeuCauDangKy {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String matKhau;

    @NotBlank
    private String hoTen;
}

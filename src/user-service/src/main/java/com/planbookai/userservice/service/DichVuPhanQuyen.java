package com.planbookai.userservice.service;

import com.planbookai.userservice.entity.ThongTinNguoiDung;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
public class DichVuPhanQuyen {

    public boolean coQuyenAdmin(ThongTinNguoiDung nguoiDung) {
        return nguoiDung.getVaiTro() != null && nguoiDung.getVaiTro().contains("ADMIN");
    }

    public boolean coQuyenGiaoVien(ThongTinNguoiDung nguoiDung) {
        return nguoiDung.getVaiTro() != null && nguoiDung.getVaiTro().contains("GIAO_VIEN");
    }

    public boolean coQuyenNhanVien(ThongTinNguoiDung nguoiDung) {
        return nguoiDung.getVaiTro() != null && nguoiDung.getVaiTro().contains("NHAN_VIEN");
    }

    public boolean coQuyenQuanLy(ThongTinNguoiDung nguoiDung) {
        return nguoiDung.getVaiTro() != null && nguoiDung.getVaiTro().contains("QUAN_LY");
    }

    public Long layUserId(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("Không xác định được user");
        }
        try {
            return Long.parseLong(authentication.getName());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Định dạng userId không hợp lệ trong Authentication");
        }
    }
}

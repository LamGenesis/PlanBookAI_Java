package com.planbookai.userservice.service;

import com.planbookai.userservice.entity.ThongTinNguoiDung;
import org.springframework.stereotype.Service;

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
}

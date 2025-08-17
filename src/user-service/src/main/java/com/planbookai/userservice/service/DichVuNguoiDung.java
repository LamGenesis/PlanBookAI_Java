package com.planbookai.userservice.service;

import com.planbookai.userservice.dto.YeuCauTaoNguoiDung;
import com.planbookai.userservice.entity.ThongTinNguoiDung;
import com.planbookai.userservice.repository.KhoThongTinNguoiDung;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DichVuNguoiDung {

    private final KhoThongTinNguoiDung khoThongTinNguoiDung;
    private final PasswordEncoder passwordEncoder;

    public Page<ThongTinNguoiDung> layDanhSachNguoiDung(int trang, int kichThuoc, String tuKhoa) {
        Pageable pageable = PageRequest.of(trang, kichThuoc);
        if (tuKhoa != null && !tuKhoa.isEmpty()) {
            return khoThongTinNguoiDung.findByEmailContainingIgnoreCase(tuKhoa, pageable);
        }
        return khoThongTinNguoiDung.findAll(pageable);
    }

    // ✅ Nhận trực tiếp DTO từ controller
    public ThongTinNguoiDung taoNguoiDung(YeuCauTaoNguoiDung yeuCau) {
        ThongTinNguoiDung nguoiDung = new ThongTinNguoiDung();
        nguoiDung.setEmail(yeuCau.getEmail());
        nguoiDung.setHoTen(yeuCau.getHoTen());
        nguoiDung.setMatKhau(passwordEncoder.encode(yeuCau.getMatKhau()));
        nguoiDung.setTrangThai(true);
        return khoThongTinNguoiDung.save(nguoiDung);
    }

    // Nếu muốn cho phép tạo trực tiếp bằng Entity
    public ThongTinNguoiDung taoNguoiDung(ThongTinNguoiDung nguoiDung) {
        nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
        return khoThongTinNguoiDung.save(nguoiDung);
    }

    public void capNhatTrangThai(Long id, Boolean trangThai) {
        ThongTinNguoiDung nguoiDung = khoThongTinNguoiDung.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + id));
        nguoiDung.setTrangThai(trangThai);
        khoThongTinNguoiDung.save(nguoiDung);
    }

    public ThongTinNguoiDung layThongTinCaNhan(String email) {
        return khoThongTinNguoiDung.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với email: " + email));
    }

    // ✅ Alias method cho controller
    public ThongTinNguoiDung timTheoEmail(String email) {
        return layThongTinCaNhan(email);
    }
}

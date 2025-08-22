package com.planbookai.userservice.service;

import com.planbookai.userservice.dto.YeuCauTaoNguoiDung;
import com.planbookai.userservice.entity.LichSuHoatDong;
import com.planbookai.userservice.entity.ThongTinNguoiDung;
import com.planbookai.userservice.repository.KhoLichSuHoatDong;
import com.planbookai.userservice.repository.KhoThongTinNguoiDung;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DichVuNguoiDung {

    private final KhoThongTinNguoiDung khoThongTin;
    private final KhoLichSuHoatDong khoLichSu;
    private final DichVuPhanQuyen dichVuPhanQuyen;

    public Page<ThongTinNguoiDung> layDanhSach(int trang, int kichThuoc, String tuKhoa) {
        Pageable pageable = PageRequest.of(trang, kichThuoc, Sort.by(Sort.Direction.DESC, "id"));
        if (tuKhoa == null || tuKhoa.isBlank()) {
            return khoThongTin.findAll(pageable);
        }
        return khoThongTin.searchByKeyword("%" + tuKhoa.trim().toLowerCase() + "%", pageable);
    }

    @Transactional
    public ThongTinNguoiDung taoNguoiDung(YeuCauTaoNguoiDung yeuCau) {
        ThongTinNguoiDung nd = ThongTinNguoiDung.builder()
                .email(yeuCau.getEmail())
                .hoTen(yeuCau.getHoTen())
                .vaiTro(yeuCau.getVaiTro())
                .trangThai(Boolean.TRUE.equals(yeuCau.getTrangThaiHoatDong()))
                .thoiGianCapNhat(LocalDateTime.now())
                .build();
        ThongTinNguoiDung saved = khoThongTin.save(nd);

        ghiLichSu(saved.getId(), "TAO_NGUOI_DUNG", "Tạo người dùng: " + saved.getEmail());
        // Gợi ý: gửi email thông báo, tạo cấu hình mặc định, v.v. (để config xử lý)
        return saved;
    }

    @Transactional
    public void capNhatTrangThai(Long id, Boolean trangThai) {
        ThongTinNguoiDung nd = khoThongTin.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng id=" + id));
        nd.setTrangThai(Boolean.TRUE.equals(trangThai));
        nd.setThoiGianCapNhat(LocalDateTime.now());
        khoThongTin.save(nd);
        ghiLichSu(id, "CAP_NHAT_TRANG_THAI", "Cập nhật trạng thái = " + nd.getTrangThai());
    }

    public ThongTinNguoiDung layThongTinCaNhan(Authentication auth) {
        Long userId = dichVuPhanQuyen.layUserId(auth);
        return khoThongTin.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hồ sơ cá nhân"));
    }

    private void ghiLichSu(Long userId, String hanhDong, String noiDung) {
        ThongTinNguoiDung nd = khoThongTin.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng id=" + userId));

        LichSuHoatDong ls = LichSuHoatDong.builder()
                .hanhDong(hanhDong)
                .thoiGian(LocalDateTime.now())
                .nguoiDung(nd) 
                .build();

        khoLichSu.save(ls);
    }

    public ThongTinNguoiDung timTheoEmail(String email) {
        return khoThongTin.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng với email: " + email));
    }
}

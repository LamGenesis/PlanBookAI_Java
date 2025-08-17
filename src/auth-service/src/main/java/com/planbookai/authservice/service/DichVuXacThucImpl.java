package com.planbookai.authservice.service;

import com.planbookai.authservice.dto.*;
import com.planbookai.authservice.entity.NguoiDung;
import com.planbookai.authservice.entity.PhienDangNhap;
import com.planbookai.authservice.repository.KhoNguoiDung;
import com.planbookai.authservice.repository.KhoPhienDangNhap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DichVuXacThucImpl implements DichVuXacThuc {

    private final KhoNguoiDung khoNguoiDung;
    private final KhoPhienDangNhap khoPhienDangNhap;
    private final PasswordEncoder passwordEncoder;
    private final DichVuJWT dichVuJWT;

    @Override
    @Transactional
    public PhanHoiDangNhap dangNhap(YeuCauDangNhap yeuCau) {
        NguoiDung nd = khoNguoiDung.findByEmail(yeuCau.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email hoặc mật khẩu không đúng"));

        if (!nd.getTrangThaiHoatDong()) {
            throw new IllegalStateException("Tài khoản đã bị vô hiệu hóa");
        }

        if (!passwordEncoder.matches(yeuCau.getMatKhau(), nd.getMatKhauMaHoa())) {
            throw new IllegalArgumentException("Email hoặc mật khẩu không đúng");
        }

        String accessToken = dichVuJWT.taoAccessToken(nd.getEmail());
        String refreshToken = dichVuJWT.taoRefreshToken(nd.getEmail());

        // lưu phiên đăng nhập (refresh token)
        PhienDangNhap phien = PhienDangNhap.builder()
                .nguoiDungId(nd.getId())
                .refreshToken(refreshToken)
                .build();
        khoPhienDangNhap.save(phien);

        ThongTinNguoiDung thongTin = ThongTinNguoiDung.builder()
                .id(nd.getId())
                .email(nd.getEmail())
                .hoTen(nd.getHoTen())
                .vaiTro(nd.getVaiTro())
                .trangThaiHoatDong(nd.getTrangThaiHoatDong())
                .build();

        return PhanHoiDangNhap.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(dichVuJWT.getAccessTokenExpirySeconds())
                .thongTinNguoiDung(thongTin)
                .build();
    }

    @Override
    @Transactional
    public void dangXuat(String bearerToken) {
        if (bearerToken == null) return;
        String token = bearerToken.replaceFirst("Bearer ", "");
        if (!dichVuJWT.validateToken(token)) return;
        String email = dichVuJWT.getEmailFromToken(token);
        khoNguoiDung.findByEmail(email).ifPresent(nd -> {
            // remove all refresh tokens for user
            khoPhienDangNhap.findByNguoiDungId(nd.getId()).forEach(khoPhienDangNhap::delete);
        });
    }

    @Override
    @Transactional
    public PhanHoiDangNhap lamMoiToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Refresh token không được để trống");
        }

        Optional<PhienDangNhap> phienOpt = khoPhienDangNhap.findByRefreshToken(refreshToken);
        if (phienOpt.isEmpty()) {
            throw new IllegalArgumentException("Refresh token không hợp lệ");
        }
        if (!dichVuJWT.validateToken(refreshToken)) {
            // xóa refresh token không hợp lệ
            khoPhienDangNhap.delete(phienOpt.get());
            throw new IllegalArgumentException("Refresh token đã hết hạn");
        }
        String email = dichVuJWT.getEmailFromToken(refreshToken);
        NguoiDung nd = khoNguoiDung.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));

        String newAccess = dichVuJWT.taoAccessToken(email);
        String newRefresh = dichVuJWT.taoRefreshToken(email);

        PhienDangNhap phien = phienOpt.get();
        phien.setRefreshToken(newRefresh);
        khoPhienDangNhap.save(phien);

        ThongTinNguoiDung thongTin = ThongTinNguoiDung.builder()
                .id(nd.getId())
                .email(nd.getEmail())
                .hoTen(nd.getHoTen())
                .vaiTro(nd.getVaiTro())
                .trangThaiHoatDong(nd.getTrangThaiHoatDong())
                .build();

        return PhanHoiDangNhap.builder()
                .accessToken(newAccess)
                .refreshToken(newRefresh)
                .expiresIn(dichVuJWT.getAccessTokenExpirySeconds())
                .thongTinNguoiDung(thongTin)
                .build();
    }

    @Override
    public void validateToken(String bearerToken) {
        if (bearerToken == null) throw new IllegalArgumentException("Token không được để trống");
        String token = bearerToken.replaceFirst("Bearer ", "");
        if (!dichVuJWT.validateToken(token)) {
            throw new IllegalArgumentException("Token không hợp lệ hoặc hết hạn");
        }
    }
}

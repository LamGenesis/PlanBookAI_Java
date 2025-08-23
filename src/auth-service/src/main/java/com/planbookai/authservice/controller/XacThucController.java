package com.planbookai.authservice.controller;

import com.planbookai.authservice.dto.*;
import com.planbookai.authservice.entity.NguoiDung;
import com.planbookai.authservice.repository.KhoNguoiDung;
import com.planbookai.authservice.service.DichVuJWT;
import com.planbookai.authservice.service.DichVuXacThuc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/xac-thuc")
@RequiredArgsConstructor
public class XacThucController {

    private final DichVuXacThuc dichVuXacThuc;
    private final DichVuJWT dichVuJWT;
    private final KhoNguoiDung khoNguoiDung;

    @PostMapping("/dang-ky")
    public ResponseEntity<ThongTinNguoiDung> dangKy(@Validated @RequestBody YeuCauDangKy yeuCau) {
        NguoiDung nd = dichVuXacThuc.dangKy(yeuCau);
        ThongTinNguoiDung thongTin = ThongTinNguoiDung.builder()
                .id(nd.getId())
                .email(nd.getEmail())
                .hoTen(nd.getHoTen())
                .vaiTro(nd.getVaiTro())
                .trangThaiHoatDong(nd.getTrangThaiHoatDong())
                .build();
        return ResponseEntity.ok(thongTin);
    }

    @PostMapping("/dang-nhap")
    public ResponseEntity<PhanHoiDangNhap> dangNhap(@Validated @RequestBody YeuCauDangNhap yeuCau) {
        PhanHoiDangNhap ph = dichVuXacThuc.dangNhap(yeuCau);
        return ResponseEntity.ok(ph);
    }

    @PostMapping("/dang-xuat")
    public ResponseEntity<Void> dangXuat(@RequestHeader(value = "Authorization", required = false) String token) {
        dichVuXacThuc.dangXuat(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/lam-moi-token")
    public ResponseEntity<PhanHoiDangNhap> lamMoiToken(@RequestBody String refreshToken) {
        // Expecting raw string body contains token (or a JSON string). Trim quotes if
        // present.
        String token = trimPossibleQuotes(refreshToken);
        PhanHoiDangNhap ph = dichVuXacThuc.lamMoiToken(token);
        return ResponseEntity.ok(ph);
    }

    @GetMapping("/kiem-tra-token")
    public ResponseEntity<ThongTinNguoiDung> kiemTraToken(@RequestHeader("Authorization") String token) {
        dichVuXacThuc.validateToken(token);
        String t = token.replaceFirst("Bearer ", "");
        String email = dichVuJWT.getEmailFromToken(t);
        NguoiDung nd = khoNguoiDung.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        ThongTinNguoiDung thongTin = ThongTinNguoiDung.builder()
                .id(nd.getId())
                .email(nd.getEmail())
                .hoTen(nd.getHoTen())
                .vaiTro(nd.getVaiTro())
                .trangThaiHoatDong(nd.getTrangThaiHoatDong())
                .build();
        return ResponseEntity.ok(thongTin);
    }

    private String trimPossibleQuotes(String s) {
        if (s == null)
            return null;
        s = s.trim();
        if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}

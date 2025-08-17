package com.planbookai.userservice.controller;

import com.planbookai.userservice.entity.ThongTinNguoiDung;
import com.planbookai.userservice.service.DichVuNguoiDung;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thong-tin-ca-nhan")
@RequiredArgsConstructor
public class ThongTinCaNhanController {

    private final DichVuNguoiDung dichVuNguoiDung;

    @GetMapping
    public ResponseEntity<ThongTinNguoiDung> layThongTinCaNhan(Authentication authentication) {
        String email = authentication.getName();
        ThongTinNguoiDung thongTin = dichVuNguoiDung.timTheoEmail(email);
        return ResponseEntity.ok(thongTin);
    }
}

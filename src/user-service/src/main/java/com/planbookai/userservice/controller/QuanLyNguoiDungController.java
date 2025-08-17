package com.planbookai.userservice.controller;

import com.planbookai.userservice.dto.YeuCauTaoNguoiDung;
import com.planbookai.userservice.entity.ThongTinNguoiDung;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planbookai.userservice.service.DichVuNguoiDung;

@RestController
@RequestMapping("/api/nguoi-dung")
@RequiredArgsConstructor
public class QuanLyNguoiDungController {
    private final DichVuNguoiDung dichVuNguoiDung;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ThongTinNguoiDung>> layDanhSachNguoiDung( 
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int kichThuoc,
            @RequestParam(required = false) String tuKhoa) {
        return ResponseEntity.ok(dichVuNguoiDung.layDanhSachNguoiDung(trang, kichThuoc, tuKhoa));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThongTinNguoiDung> taoNguoiDung(@RequestBody @Valid YeuCauTaoNguoiDung yeuCau) {
        return ResponseEntity.ok(dichVuNguoiDung.taoNguoiDung(yeuCau));
    }

    @PutMapping("/{id}/trang-thai")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> capNhatTrangThai(@PathVariable Long id, @RequestBody Boolean trangThai) {
        dichVuNguoiDung.capNhatTrangThai(id, trangThai);
        return ResponseEntity.ok().build();
    }
}


package com.planbookai.userservice.controller;

import com.planbookai.userservice.dto.YeuCauTaoNguoiDung;
import com.planbookai.userservice.entity.ThongTinNguoiDung;
import com.planbookai.userservice.service.DichVuNguoiDung;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(dichVuNguoiDung.layDanhSach(trang, kichThuoc, tuKhoa));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThongTinNguoiDung> taoNguoiDung(@RequestBody YeuCauTaoNguoiDung yeuCau) {
        ThongTinNguoiDung nd = dichVuNguoiDung.taoNguoiDung(yeuCau);
        return ResponseEntity.ok(nd);
    }

    @PutMapping("/{id}/trang-thai")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> capNhatTrangThai(@PathVariable Long id, @RequestBody Boolean trangThai) {
        dichVuNguoiDung.capNhatTrangThai(id, trangThai);
        return ResponseEntity.noContent().build();
    }
}

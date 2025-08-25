package com.planbookai.planservice.controller;

import com.planbookai.planservice.entity.GiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import com.planbookai.planservice.entity.TrangThaiGiaoAn;
import com.planbookai.planservice.service.GiaoAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/giao-an")
@CrossOrigin(origins = "*")
public class GiaoAnController {
    
    @Autowired
    private GiaoAnService giaoAnService;
    
    @PostMapping
    public ResponseEntity<GiaoAn> taoGiaoAn(@RequestBody GiaoAn giaoAn) {
        GiaoAn giaoAnMoi = giaoAnService.taoGiaoAn(giaoAn);
        return ResponseEntity.ok(giaoAnMoi);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GiaoAn> layGiaoAnTheoId(@PathVariable UUID id) {
        GiaoAn giaoAn = giaoAnService.layGiaoAnTheoId(id);
        return ResponseEntity.ok(giaoAn);
    }
    
    @GetMapping
    public ResponseEntity<Page<GiaoAn>> layDanhSachGiaoAn(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GiaoAn> giaoAnPage = giaoAnService.layDanhSachGiaoAn(pageable);
        return ResponseEntity.ok(giaoAnPage);
    }
    
    @GetMapping("/giao-vien/{giaoVienId}")
    public ResponseEntity<List<GiaoAn>> layGiaoAnTheoGiaoVien(@PathVariable UUID giaoVienId) {
        List<GiaoAn> giaoAnList = giaoAnService.layGiaoAnTheoGiaoVien(giaoVienId);
        return ResponseEntity.ok(giaoAnList);
    }
    
    @GetMapping("/mon-hoc/{monHoc}")
    public ResponseEntity<List<GiaoAn>> layGiaoAnTheoMonHoc(@PathVariable MonHoc monHoc) {
        List<GiaoAn> giaoAnList = giaoAnService.layGiaoAnTheoMonHoc(monHoc);
        return ResponseEntity.ok(giaoAnList);
    }
    
    @GetMapping("/lop/{lop}")
    public ResponseEntity<List<GiaoAn>> layGiaoAnTheoLop(@PathVariable Integer lop) {
        List<GiaoAn> giaoAnList = giaoAnService.layGiaoAnTheoLop(lop);
        return ResponseEntity.ok(giaoAnList);
    }
    
    @GetMapping("/trang-thai/{trangThai}")
    public ResponseEntity<List<GiaoAn>> layGiaoAnTheoTrangThai(@PathVariable TrangThaiGiaoAn trangThai) {
        List<GiaoAn> giaoAnList = giaoAnService.layGiaoAnTheoTrangThai(trangThai);
        return ResponseEntity.ok(giaoAnList);
    }
    
    @GetMapping("/tim-kiem")
    public ResponseEntity<List<GiaoAn>> timKiemGiaoAn(@RequestParam(required = false) String tuKhoa) {
        List<GiaoAn> giaoAnList = giaoAnService.timKiemGiaoAn(tuKhoa);
        return ResponseEntity.ok(giaoAnList);
    }
    
    @GetMapping("/giao-vien/{giaoVienId}/tim-kiem")
    public ResponseEntity<List<GiaoAn>> timKiemGiaoAnTheoGiaoVien(
            @PathVariable UUID giaoVienId,
            @RequestParam(required = false) String tuKhoa) {
        List<GiaoAn> giaoAnList = giaoAnService.timKiemGiaoAnTheoGiaoVien(giaoVienId, tuKhoa);
        return ResponseEntity.ok(giaoAnList);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GiaoAn> capNhatGiaoAn(@PathVariable UUID id, @RequestBody GiaoAn giaoAnMoi) {
        GiaoAn giaoAnCapNhat = giaoAnService.capNhatGiaoAn(id, giaoAnMoi);
        return ResponseEntity.ok(giaoAnCapNhat);
    }
    
    @PutMapping("/{id}/phe-duyet")
    public ResponseEntity<GiaoAn> pheDuyetGiaoAn(@PathVariable UUID id) {
        GiaoAn giaoAn = giaoAnService.pheDuyetGiaoAn(id);
        return ResponseEntity.ok(giaoAn);
    }
    
    @PutMapping("/{id}/da-su-dung")
    public ResponseEntity<GiaoAn> danhDauDaSuDung(@PathVariable UUID id) {
        GiaoAn giaoAn = giaoAnService.danhDauDaSuDung(id);
        return ResponseEntity.ok(giaoAn);
    }
    
    @PutMapping("/{id}/quay-ve-ban-nhap")
    public ResponseEntity<GiaoAn> quayVeBanNhap(@PathVariable UUID id) {
        GiaoAn giaoAn = giaoAnService.quayVeBanNhap(id);
        return ResponseEntity.ok(giaoAn);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaGiaoAn(@PathVariable UUID id) {
        giaoAnService.xoaGiaoAn(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/giao-vien/{giaoVienId}/thong-ke")
    public ResponseEntity<Long> demGiaoAnTheoTrangThai(
            @PathVariable UUID giaoVienId,
            @RequestParam TrangThaiGiaoAn trangThai) {
        Long soLuong = giaoAnService.demGiaoAnTheoGiaoVienVaTrangThai(giaoVienId, trangThai);
        return ResponseEntity.ok(soLuong);
    }
}
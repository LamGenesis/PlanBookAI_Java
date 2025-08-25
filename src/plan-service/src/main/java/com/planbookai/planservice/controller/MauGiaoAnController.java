package com.planbookai.planservice.controller;

import com.planbookai.planservice.entity.MauGiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import com.planbookai.planservice.service.MauGiaoAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mau-giao-an")
@CrossOrigin(origins = "*")
public class MauGiaoAnController {
    
    @Autowired
    private MauGiaoAnService mauGiaoAnService;
    
    @PostMapping
    public ResponseEntity<MauGiaoAn> taoMauGiaoAn(@RequestBody MauGiaoAn mauGiaoAn) {
        MauGiaoAn mauGiaoAnMoi = mauGiaoAnService.taoMauGiaoAn(mauGiaoAn);
        return ResponseEntity.ok(mauGiaoAnMoi);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MauGiaoAn> layMauGiaoAnTheoId(@PathVariable UUID id) {
        MauGiaoAn mauGiaoAn = mauGiaoAnService.layMauGiaoAnTheoId(id);
        return ResponseEntity.ok(mauGiaoAn);
    }
    
    @GetMapping
    public ResponseEntity<List<MauGiaoAn>> layTatCaMauGiaoAn() {
        List<MauGiaoAn> mauGiaoAnList = mauGiaoAnService.layTatCaMauGiaoAn();
        return ResponseEntity.ok(mauGiaoAnList);
    }
    
    @GetMapping("/mon-hoc/{monHoc}")
    public ResponseEntity<List<MauGiaoAn>> layMauGiaoAnTheoMonHoc(@PathVariable MonHoc monHoc) {
        List<MauGiaoAn> mauGiaoAnList = mauGiaoAnService.layMauGiaoAnTheoMonHoc(monHoc);
        return ResponseEntity.ok(mauGiaoAnList);
    }
    
    @GetMapping("/lop/{lop}")
    public ResponseEntity<List<MauGiaoAn>> layMauGiaoAnTheoLop(@PathVariable Integer lop) {
        List<MauGiaoAn> mauGiaoAnList = mauGiaoAnService.layMauGiaoAnTheoLop(lop);
        return ResponseEntity.ok(mauGiaoAnList);
    }
    
    @GetMapping("/tim-kiem")
    public ResponseEntity<List<MauGiaoAn>> timKiemMauGiaoAn(@RequestParam(required = false) String tuKhoa) {
        List<MauGiaoAn> mauGiaoAnList = mauGiaoAnService.timKiemMauGiaoAn(tuKhoa);
        return ResponseEntity.ok(mauGiaoAnList);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MauGiaoAn> capNhatMauGiaoAn(@PathVariable UUID id, @RequestBody MauGiaoAn mauGiaoAnMoi) {
        MauGiaoAn mauGiaoAnCapNhat = mauGiaoAnService.capNhatMauGiaoAn(id, mauGiaoAnMoi);
        return ResponseEntity.ok(mauGiaoAnCapNhat);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaMauGiaoAn(@PathVariable UUID id) {
        mauGiaoAnService.xoaMauGiaoAn(id);
        return ResponseEntity.ok().build();
    }
}
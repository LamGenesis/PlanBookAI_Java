package com.planbookai.taskservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cham-diem")
@CrossOrigin(origins = "*")
public class ChamDiemController {

    // POST /api/cham-diem/ocr - Chấm điểm bằng OCR
    @PostMapping("/ocr")
    public ResponseEntity<String> chamDiemOCR() {
        return ResponseEntity.ok("Chức năng chấm điểm OCR sẽ được implement sau");
    }

    // GET /api/cham-diem/ket-qua/{id} - Lấy kết quả chấm điểm
    @GetMapping("/ket-qua/{id}")
    public ResponseEntity<String> layKetQuaChamDiem(@PathVariable String id) {
        return ResponseEntity.ok("Kết quả chấm điểm sẽ được implement sau");
    }

    // PUT /api/cham-diem/sua-ket-qua/{id} - Sửa kết quả chấm điểm
    @PutMapping("/sua-ket-qua/{id}")
    public ResponseEntity<String> suaKetQuaChamDiem(@PathVariable String id) {
        return ResponseEntity.ok("Sửa kết quả chấm điểm sẽ được implement sau");
    }

    // GET /api/cham-diem/thong-ke - Thống kê kết quả
    @GetMapping("/thong-ke")
    public ResponseEntity<String> layThongKe() {
        return ResponseEntity.ok("Thống kê kết quả sẽ được implement sau");
    }

    // POST /api/cham-diem/xuat-excel - Xuất kết quả ra Excel
    @PostMapping("/xuat-excel")
    public ResponseEntity<String> xuatExcel() {
        return ResponseEntity.ok("Xuất Excel sẽ được implement sau");
    }
}
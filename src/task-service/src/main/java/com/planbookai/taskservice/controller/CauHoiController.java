package com.planbookai.taskservice.controller;

import com.planbookai.taskservice.entity.CauHoi;
import com.planbookai.taskservice.entity.LoaiCauHoi;
import com.planbookai.taskservice.entity.MucDoKho;
import com.planbookai.taskservice.service.CauHoiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cau-hoi")
@CrossOrigin(origins = "*")
public class CauHoiController {

    private final CauHoiService cauHoiService;

    public CauHoiController(CauHoiService cauHoiService) {
        this.cauHoiService = cauHoiService;
    }

    // GET /api/cau-hoi - Lấy danh sách câu hỏi
    @GetMapping
    public ResponseEntity<Page<CauHoi>> layDanhSachCauHoi(
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int kichThuoc) {
        Pageable pageable = PageRequest.of(trang, kichThuoc);
        Page<CauHoi> ketQua = cauHoiService.timKiem(null, null, null, null, null, pageable);
        return ResponseEntity.ok(ketQua);
    }

    // POST /api/cau-hoi - Tạo câu hỏi mới
    @PostMapping
    public ResponseEntity<CauHoi> taoCauHoi(@RequestBody CauHoi cauHoi) {
        CauHoi moi = cauHoiService.tao(cauHoi);
        return ResponseEntity.status(HttpStatus.CREATED).body(moi);
    }

    // GET /api/cau-hoi/{id} - Lấy chi tiết câu hỏi
    @GetMapping("/{id}")
    public ResponseEntity<CauHoi> layCauHoi(@PathVariable UUID id) {
        return cauHoiService.timTheoId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/cau-hoi/{id} - Cập nhật câu hỏi
    @PutMapping("/{id}")
    public ResponseEntity<CauHoi> capNhatCauHoi(@PathVariable UUID id, @RequestBody CauHoi cauHoi) {
        try {
            CauHoi daCapNhat = cauHoiService.capNhat(id, cauHoi);
            return ResponseEntity.ok(daCapNhat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/cau-hoi/{id} - Xóa câu hỏi
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaCauHoi(@PathVariable UUID id) {
        cauHoiService.xoa(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/cau-hoi/nhap-excel - Nhập câu hỏi từ Excel
    @PostMapping("/nhap-excel")
    public ResponseEntity<String> nhapExcel() {
        return ResponseEntity.ok("Chức năng nhập Excel sẽ được implement sau");
    }

    // GET /api/cau-hoi/tim-kiem - Tìm kiếm câu hỏi
    @GetMapping("/tim-kiem")
    public ResponseEntity<Page<CauHoi>> timKiemCauHoi(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) LoaiCauHoi loai,
            @RequestParam(required = false) MucDoKho doKho,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int kichThuoc) {
        
        Pageable pageable = PageRequest.of(trang, kichThuoc);
        Page<CauHoi> ketQua = cauHoiService.timKiem(subject, topic, loai, doKho, keyword, pageable);
        return ResponseEntity.ok(ketQua);
    }

    // GET /api/cau-hoi/chu-de - Lấy danh sách chủ đề
    @GetMapping("/chu-de")
    public ResponseEntity<List<String>> layChuDe() {
        return ResponseEntity.ok(List.of("Hóa học", "Vật lý", "Sinh học", "Toán học"));
    }
}
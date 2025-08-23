package com.planbookai.taskservice.controller;

import com.planbookai.taskservice.entity.DeThi;
import com.planbookai.taskservice.service.DeThiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/de-thi")
@CrossOrigin(origins = "*")
public class DeThiController {

    private final DeThiService deThiService;

    public DeThiController(DeThiService deThiService) {
        this.deThiService = deThiService;
    }

    // GET /api/de-thi - Lấy danh sách đề thi
    @GetMapping
    public ResponseEntity<Page<DeThi>> layDanhSachDeThi(
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int kichThuoc) {
        Pageable pageable = PageRequest.of(trang, kichThuoc);
        
        // Lấy tất cả đề thi (không filter theo user)
        Page<DeThi> ketQua = deThiService.layTatCaDeThi(pageable);
        return ResponseEntity.ok(ketQua);
            }        

    // POST /api/de-thi - Tạo đề thi mới
    @PostMapping
    public ResponseEntity<DeThi> taoDeThi(@RequestBody DeThi deThi) {
        try {
            DeThi moi = deThiService.tao(deThi);
            return ResponseEntity.status(HttpStatus.CREATED).body(moi);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/de-thi/{id} - Lấy chi tiết đề thi
    @GetMapping("/{id}")
    public ResponseEntity<DeThi> layDeThi(@PathVariable UUID id) {
        return deThiService.timTheoId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/de-thi/{id} - Cập nhật đề thi
    @PutMapping("/{id}")
    public ResponseEntity<DeThi> capNhatDeThi(@PathVariable UUID id, @RequestBody DeThi deThi) {
        try {
            DeThi daCapNhat = deThiService.capNhat(id, deThi);
            return ResponseEntity.ok(daCapNhat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/de-thi/{id} - Xóa đề thi
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaDeThi(@PathVariable UUID id) {
        deThiService.xoa(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/de-thi/tao-tu-dong - Tạo đề thi tự động
    @PostMapping("/tao-tu-dong")
    public ResponseEntity<String> taoTuDong() {
        return ResponseEntity.ok("Chức năng tạo đề thi tự động sẽ được implement sau");
    }

    // GET /api/de-thi/{id}/xuat-pdf - Xuất đề thi ra PDF 
    @GetMapping("/{id}/xuat-pdf")
    public ResponseEntity<String> xuatPDF(@PathVariable UUID id) {
        return ResponseEntity.ok("Chức năng xuất PDF sẽ được implement sau");
    }

    // POST /api/de-thi/{id}/them-cau-hoi - Thêm câu hỏi vào đề thi
    @PostMapping("/{id}/them-cau-hoi")
    public ResponseEntity<DeThi> themCauHoi(
            @PathVariable UUID id,
            @RequestParam UUID cauHoiId,
            @RequestParam int thuTu,
            @RequestParam BigDecimal diem) {
        try {
            DeThi daCapNhat = deThiService.themCauHoi(id, cauHoiId, thuTu, diem);
            return ResponseEntity.ok(daCapNhat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/de-thi/{id}/xoa-cau-hoi - Xóa câu hỏi khỏi đề thi
    @DeleteMapping("/{id}/xoa-cau-hoi")
    public ResponseEntity<DeThi> xoaCauHoi(
            @PathVariable UUID id,
            @RequestParam UUID cauHoiId) {
        try {
            DeThi daCapNhat = deThiService.xoaCauHoi(id, cauHoiId);
            return ResponseEntity.ok(daCapNhat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // POST /api/de-thi/{id}/publish - Publish đề thi
    @PostMapping("/{id}/publish")
    public ResponseEntity<DeThi> publishDeThi(@PathVariable UUID id) {
        try {
            DeThi daPublish = deThiService.publish(id);
            return ResponseEntity.ok(daPublish);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // POST /api/de-thi/{id}/unpublish - Unpublish đề thi
    @PostMapping("/{id}/unpublish")
    public ResponseEntity<DeThi> unpublishDeThi(@PathVariable UUID id) {
        try {
            DeThi daUnpublish = deThiService.unpublish(id);
            return ResponseEntity.ok(daUnpublish);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/de-thi/{id}/tong-diem - Lấy tổng điểm đề thi
    @GetMapping("/{id}/tong-diem")
    public ResponseEntity<BigDecimal> layTongDiem(@PathVariable UUID id) {
        BigDecimal tongDiem = deThiService.tongDiem(id);
        return ResponseEntity.ok(tongDiem);
    }

    // GET /api/de-thi/giao-vien/{teacherId} - Lấy đề thi theo giáo viên
    @GetMapping("/giao-vien/{teacherId}")
    public ResponseEntity<Page<DeThi>> layTheoGiaoVien(
            @PathVariable UUID teacherId,
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int kichThuoc) {
        
        Pageable pageable = PageRequest.of(trang, kichThuoc);
        Page<DeThi> ketQua = deThiService.timTheoGiaoVien(teacherId, pageable);
        return ResponseEntity.ok(ketQua);
    }

    // GET /api/de-thi/tim-kiem-nang-cao - Tìm kiếm nâng cao
    @GetMapping("/tim-kiem-nang-cao")
    public ResponseEntity<Page<DeThi>> timKiemNangCao(
            @RequestParam UUID teacherId,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) LocalDateTime tuNgay,
            @RequestParam(required = false) LocalDateTime denNgay,
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int kichThuoc) {
        
        Pageable pageable = PageRequest.of(trang, kichThuoc);
        Page<DeThi> ketQua = deThiService.timKiemNangCao(teacherId, subject, tuNgay, denNgay, pageable);
        return ResponseEntity.ok(ketQua);
    }
}
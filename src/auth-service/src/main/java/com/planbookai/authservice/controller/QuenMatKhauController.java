package com.planbookai.authservice.controller;

import com.planbookai.authservice.repository.KhoNguoiDung;
import com.planbookai.authservice.service.DichVuEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quen-mat-khau")
@RequiredArgsConstructor
public class QuenMatKhauController {

    private final KhoNguoiDung khoNguoiDung;
    private final DichVuEmail dichVuEmail;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/yeu-cau")
    public ResponseEntity<Void> yeuCauQuenMatKhau(@RequestParam String email) {
        khoNguoiDung.findByEmail(email).ifPresent(nguoiDung -> {
            // For now create a reset token (stub) and send email; in production use a stored token with expiry.
            String resetToken = "RESET-" + System.currentTimeMillis();
            String resetLink = "https://your-app/reset?token=" + resetToken;
            dichVuEmail.sendSimpleMessage(email, "Yêu cầu đặt lại mật khẩu", "Bấm vào: " + resetLink);
            
        });
        // Always return 204 to not reveal whether email exists
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/dat-lai")
    public ResponseEntity<Void> datLaiMatKhau(@RequestParam String token, @RequestParam String matKhauMoi) {
        // If implemented: find user -> encode passwordEncoder.encode(matKhauMoi) -> save user
        return ResponseEntity.noContent().build();
    }
}

package com.planbookai.authservice.service;

import org.springframework.stereotype.Service;

@Service
public class DichVuEmail {

    
    public void guiEmailQuenMatKhau(String email, String maOTP) {
        System.out.printf("Gửi email đến %s với OTP: %s%n", email, maOTP);
    }

    
    public void sendSimpleMessage(String to, String subject, String text) {
        
        System.out.printf("Gửi email đến %s | Tiêu đề: %s | Nội dung: %s%n", to, subject, text);
    }
}

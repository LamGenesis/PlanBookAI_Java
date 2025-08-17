package com.planbookai.authservice.service;

public interface DichVuEmail {
    void sendSimpleMessage(String to, String subject, String text);
}

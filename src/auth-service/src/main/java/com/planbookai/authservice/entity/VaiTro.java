package com.planbookai.authservice.entity;

public enum VaiTro {
    ADMIN("Quản trị viên"),
    MANAGER("Quản lý"),
    STAFF("Nhân viên"),
    TEACHER("Giáo viên");

    private final String tenHienThi;

    VaiTro(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }
}

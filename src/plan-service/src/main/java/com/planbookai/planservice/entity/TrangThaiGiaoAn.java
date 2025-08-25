package com.planbookai.planservice.entity;

public enum TrangThaiGiaoAn {
    DRAFT("Bản nháp"),
    COMPLETED("Hoàn thành"),
    USED("Đã sử dụng"),
    PUBLISHED("Đã xuất bản");

    private final String tenHienThi;

    TrangThaiGiaoAn(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }
}

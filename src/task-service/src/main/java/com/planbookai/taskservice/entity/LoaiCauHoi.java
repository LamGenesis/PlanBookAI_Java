package com.planbookai.taskservice.entity;

/**
 * Enum LoaiCauHoi - Loại câu hỏi
 * Khớp với database CHECK constraint: type IN ('MULTIPLE_CHOICE', 'ESSAY')
 */

public enum LoaiCauHoi {
    MULTIPLE_CHOICE("MULTIPLE_CHOICE", "Trắc nghiệm"),
    ESSAY("ESSAY", "Tự luận");

    private final String databaseValue;
    private final String moTa;
    
    LoaiCauHoi(String databaseValue, String moTa) {
        this.databaseValue = databaseValue;
        this.moTa = moTa;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public String getMoTa() {
        return moTa;
    }

    // Utility method để convert từ database value
    public static LoaiCauHoi fromDatabaseValue(String value) {
        for (LoaiCauHoi loai : values()) {
            if (loai.databaseValue.equals(value)) {
                return loai;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy LoaiCauHoi với value: " + value);
    }

    @Override
    public String toString() {
        return moTa;
    }
}

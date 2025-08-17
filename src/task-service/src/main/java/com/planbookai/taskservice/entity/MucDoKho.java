package com.planbookai.taskservice.entity;

/**
 * Enum MucDoKho - Mức độ khó của câu hỏi
 * Khớp với database CHECK constraint: difficulty IN ('EASY', 'MEDIUM', 'HARD', 'VERY_HARD')
 */
public enum MucDoKho {
    
    EASY("EASY", "Dễ", 1),
    MEDIUM("MEDIUM", "Trung bình", 2),
    HARD("HARD", "Khó", 3),
    VERY_HARD("VERY_HARD", "Rất khó", 4);
    
    private final String databaseValue;
    private final String moTa;
    private final int level;
    
    MucDoKho(String databaseValue, String moTa, int level) {
        this.databaseValue = databaseValue;
        this.moTa = moTa;
        this.level = level;
    }
    
    public String getDatabaseValue() {
        return databaseValue;
    }
    
    public String getMoTa() {
        return moTa;
    }
    
    public int getLevel() {
        return level;
    }
    
    // Utility method để convert từ database value
    public static MucDoKho fromDatabaseValue(String value) {
        for (MucDoKho mucDo : values()) {
            if (mucDo.databaseValue.equals(value)) {
                return mucDo;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy MucDoKho với value: " + value);
    }
    
    // Method để so sánh độ khó
    public boolean khoHon(MucDoKho other) {
        return this.level > other.level;
    }
    
    public boolean deHon(MucDoKho other) {
        return this.level < other.level;
    }
    
    @Override
    public String toString() {
        return moTa;
    }
}
package com.planbookai.planservice.entity;

public enum MonHoc {
    CHEMISTRY("Hóa học"), 
    PHYSICS("Vật lý"),    
    BIOLOGY("Sinh học"),   
    MATHEMATICS("Toán học"), 
    LITERATURE("Văn học"),   
    HISTORY("Lịch sử"),      
    GEOGRAPHY("Địa lý"),     
    FOREIGN_LANGUAGE("Ngoại ngữ"), 
    COMPUTER_SCIENCE("Tin học"),   
    PHYSICAL_EDUCATION("Thể dục"), 
    CIVIC_EDUCATION("Giáo dục công dân");
    
    private final String tenHienThi;
    
    MonHoc(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }
    
    public String getTenHienThi() {
        return tenHienThi;
    }
}
package com.planbookai.planservice.service;

import com.planbookai.planservice.entity.GiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import com.planbookai.planservice.entity.TrangThaiGiaoAn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface GiaoAnService {
    
    GiaoAn taoGiaoAn(GiaoAn giaoAn);
    
    GiaoAn layGiaoAnTheoId(UUID id);
    
    Page<GiaoAn> layDanhSachGiaoAn(Pageable pageable);
    
    List<GiaoAn> layGiaoAnTheoGiaoVien(UUID giaoVienId);
    
    List<GiaoAn> layGiaoAnTheoMonHoc(MonHoc monHoc);
    
    List<GiaoAn> layGiaoAnTheoLop(Integer lop);
    
    List<GiaoAn> layGiaoAnTheoTrangThai(TrangThaiGiaoAn trangThai);
    
    List<GiaoAn> layGiaoAnTheoGiaoVienVaTrangThai(UUID giaoVienId, TrangThaiGiaoAn trangThai);
    
    GiaoAn capNhatGiaoAn(UUID id, GiaoAn giaoAnMoi);
    
    void xoaGiaoAn(UUID id);
    
    GiaoAn pheDuyetGiaoAn(UUID id);
    
    GiaoAn danhDauDaSuDung(UUID id);
    
    GiaoAn quayVeBanNhap(UUID id);
    
    List<GiaoAn> timKiemGiaoAn(String tuKhoa);
    
    List<GiaoAn> timKiemGiaoAnTheoGiaoVien(UUID giaoVienId, String tuKhoa);
    
    Long demGiaoAnTheoGiaoVienVaTrangThai(UUID giaoVienId, TrangThaiGiaoAn trangThai);
    
    List<GiaoAn> layGiaoAnTheoGiaoVienVaKhoangThoiGian(UUID giaoVienId, LocalDateTime tuNgay, LocalDateTime denNgay);
}
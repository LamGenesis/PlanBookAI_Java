package com.planbookai.planservice.service;

import com.planbookai.planservice.entity.MauGiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import java.util.List;
import java.util.UUID;

public interface MauGiaoAnService {
    
    MauGiaoAn taoMauGiaoAn(MauGiaoAn mauGiaoAn);
    
    MauGiaoAn layMauGiaoAnTheoId(UUID id);
    
    List<MauGiaoAn> layTatCaMauGiaoAn();
    
    List<MauGiaoAn> layMauGiaoAnTheoMonHoc(MonHoc monHoc);
    
    List<MauGiaoAn> layMauGiaoAnTheoLop(Integer lop);
    
    List<MauGiaoAn> layMauGiaoAnTheoMonHocVaLop(MonHoc monHoc, Integer lop);
    
    List<MauGiaoAn> layMauGiaoAnTheoNguoiTao(UUID nguoiTaoId);
    
    MauGiaoAn capNhatMauGiaoAn(UUID id, MauGiaoAn mauGiaoAnMoi);
    
    void xoaMauGiaoAn(UUID id);
    
    List<MauGiaoAn> timKiemMauGiaoAn(String tuKhoa);
}
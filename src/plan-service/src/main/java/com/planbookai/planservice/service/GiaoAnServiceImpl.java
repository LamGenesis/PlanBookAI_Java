package com.planbookai.planservice.service;

import com.planbookai.planservice.entity.GiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import com.planbookai.planservice.entity.TrangThaiGiaoAn;
import com.planbookai.planservice.repository.GiaoAnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GiaoAnServiceImpl implements GiaoAnService {
    
    @Autowired
    private GiaoAnRepository giaoAnRepository;
    
    @Override
    public GiaoAn taoGiaoAn(GiaoAn giaoAn) {
        return giaoAnRepository.save(giaoAn);
    }
    
    @Override
    public GiaoAn layGiaoAnTheoId(UUID id) {
        return giaoAnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo án với ID: " + id));
    }
    
    @Override
    public Page<GiaoAn> layDanhSachGiaoAn(Pageable pageable) {
        List<GiaoAn> giaoAnList = giaoAnRepository.findAll();
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), giaoAnList.size());
        
        if (start > giaoAnList.size()) {
            return new PageImpl<>(List.of(), pageable, giaoAnList.size());
        }
        
        List<GiaoAn> pageContent = giaoAnList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, giaoAnList.size());
    }
    
    @Override
    public List<GiaoAn> layGiaoAnTheoGiaoVien(UUID giaoVienId) {
        return giaoAnRepository.findByTeacherId(giaoVienId);
    }
    
    @Override
    public List<GiaoAn> layGiaoAnTheoMonHoc(MonHoc monHoc) {
        return giaoAnRepository.findBySubject(monHoc);
    }
    
    @Override
    public List<GiaoAn> layGiaoAnTheoLop(Integer lop) {
        return giaoAnRepository.findByGrade(lop);
    }
    
    @Override
    public List<GiaoAn> layGiaoAnTheoTrangThai(TrangThaiGiaoAn trangThai) {
        return giaoAnRepository.findByStatus(trangThai);
    }
    
    @Override
    public List<GiaoAn> layGiaoAnTheoGiaoVienVaTrangThai(UUID giaoVienId, TrangThaiGiaoAn trangThai) {
        return giaoAnRepository.findByTeacherIdAndStatus(giaoVienId, trangThai);
    }
    
    @Override
    public GiaoAn capNhatGiaoAn(UUID id, GiaoAn giaoAnMoi) {
        GiaoAn giaoAnHienTai = layGiaoAnTheoId(id);
        
        if (giaoAnMoi.getTitle() != null) {
            giaoAnHienTai.setTitle(giaoAnMoi.getTitle());
        }
        if (giaoAnMoi.getObjectives() != null) {
            giaoAnHienTai.setObjectives(giaoAnMoi.getObjectives());
        }
        if (giaoAnMoi.getContent() != null) {
            giaoAnHienTai.setContent(giaoAnMoi.getContent());
        }
        if (giaoAnMoi.getSubject() != null) {
            giaoAnHienTai.setSubject(giaoAnMoi.getSubject());
        }
        if (giaoAnMoi.getGrade() != null) {
            giaoAnHienTai.setGrade(giaoAnMoi.getGrade());
        }
        
        return giaoAnRepository.save(giaoAnHienTai);
    }
    
    @Override
    public void xoaGiaoAn(UUID id) {
        GiaoAn giaoAn = layGiaoAnTheoId(id);
        giaoAnRepository.delete(giaoAn);
    }
    
    @Override
    public GiaoAn pheDuyetGiaoAn(UUID id) {
        GiaoAn giaoAn = layGiaoAnTheoId(id);
        giaoAn.pheDuyet();
        return giaoAnRepository.save(giaoAn);
    }
    
    @Override
    public GiaoAn danhDauDaSuDung(UUID id) {
        GiaoAn giaoAn = layGiaoAnTheoId(id);
        giaoAn.danhDauDaSuDung();
        return giaoAnRepository.save(giaoAn);
    }
    
    @Override
    public GiaoAn quayVeBanNhap(UUID id) {
        GiaoAn giaoAn = layGiaoAnTheoId(id);
        giaoAn.quayVeBanNhap();
        return giaoAnRepository.save(giaoAn);
    }
    
    @Override
    public List<GiaoAn> timKiemGiaoAn(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return giaoAnRepository.findAll();
        }
        return giaoAnRepository.findByKeyword(tuKhoa.trim());
    }
    
    @Override
    public List<GiaoAn> timKiemGiaoAnTheoGiaoVien(UUID giaoVienId, String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return layGiaoAnTheoGiaoVien(giaoVienId);
        }
        return giaoAnRepository.findByTeacherIdAndKeyword(giaoVienId, tuKhoa.trim());
    }
    
    @Override
    public Long demGiaoAnTheoGiaoVienVaTrangThai(UUID giaoVienId, TrangThaiGiaoAn trangThai) {
        return giaoAnRepository.countByTeacherIdAndStatus(giaoVienId, trangThai);
    }
    
    @Override
    public List<GiaoAn> layGiaoAnTheoGiaoVienVaKhoangThoiGian(UUID giaoVienId, LocalDateTime tuNgay, LocalDateTime denNgay) {
        return giaoAnRepository.findByTeacherIdAndDateRange(giaoVienId, tuNgay, denNgay);
    }
}
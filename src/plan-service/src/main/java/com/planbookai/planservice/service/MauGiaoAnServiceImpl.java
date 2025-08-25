package com.planbookai.planservice.service;

import com.planbookai.planservice.entity.MauGiaoAn;
import com.planbookai.planservice.entity.MonHoc;
import com.planbookai.planservice.repository.MauGiaoAnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MauGiaoAnServiceImpl implements MauGiaoAnService {
    
    @Autowired
    private MauGiaoAnRepository mauGiaoAnRepository;
    
    @Override
    public MauGiaoAn taoMauGiaoAn(MauGiaoAn mauGiaoAn) {
        return mauGiaoAnRepository.save(mauGiaoAn);
    }
    
    @Override
    public MauGiaoAn layMauGiaoAnTheoId(UUID id) {
        return mauGiaoAnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mẫu giáo án với ID: " + id));
    }
    
    @Override
    public List<MauGiaoAn> layTatCaMauGiaoAn() {
        return mauGiaoAnRepository.findAll();
    }
    
    @Override
    public List<MauGiaoAn> layMauGiaoAnTheoMonHoc(MonHoc monHoc) {
        return mauGiaoAnRepository.findBySubject(monHoc);
    }
    
    @Override
    public List<MauGiaoAn> layMauGiaoAnTheoLop(Integer lop) {
        return mauGiaoAnRepository.findByGrade(lop);
    }
    
    @Override
    public List<MauGiaoAn> layMauGiaoAnTheoMonHocVaLop(MonHoc monHoc, Integer lop) {
        return mauGiaoAnRepository.findBySubjectAndGrade(monHoc, lop);
    }
    
    @Override
    public List<MauGiaoAn> layMauGiaoAnTheoNguoiTao(UUID nguoiTaoId) {
        return mauGiaoAnRepository.findByCreatedBy(nguoiTaoId);
    }
    
    @Override
    public MauGiaoAn capNhatMauGiaoAn(UUID id, MauGiaoAn mauGiaoAnMoi) {
        MauGiaoAn mauGiaoAnHienTai = layMauGiaoAnTheoId(id);
        
        if (mauGiaoAnMoi.getTitle() != null) {
            mauGiaoAnHienTai.setTitle(mauGiaoAnMoi.getTitle());
        }
        if (mauGiaoAnMoi.getDescription() != null) {
            mauGiaoAnHienTai.setDescription(mauGiaoAnMoi.getDescription());
        }
        if (mauGiaoAnMoi.getTemplateContent() != null) {
            mauGiaoAnHienTai.setTemplateContent(mauGiaoAnMoi.getTemplateContent());
        }
        if (mauGiaoAnMoi.getSubject() != null) {
            mauGiaoAnHienTai.setSubject(mauGiaoAnMoi.getSubject());
        }
        if (mauGiaoAnMoi.getGrade() != null) {
            mauGiaoAnHienTai.setGrade(mauGiaoAnMoi.getGrade());
        }
        
        return mauGiaoAnRepository.save(mauGiaoAnHienTai);
    }
    
    @Override
    public void xoaMauGiaoAn(UUID id) {
        MauGiaoAn mauGiaoAn = layMauGiaoAnTheoId(id);
        mauGiaoAn.vohieuHoa();
        mauGiaoAnRepository.save(mauGiaoAn);
    }
    
    @Override
    public List<MauGiaoAn> timKiemMauGiaoAn(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return layTatCaMauGiaoAn();
        }
        return mauGiaoAnRepository.findByKeyword(tuKhoa.trim());
    }
}
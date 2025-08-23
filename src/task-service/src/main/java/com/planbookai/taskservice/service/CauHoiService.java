package com.planbookai.taskservice.service;

import java.util.Optional;
import java.util.UUID;

import com.planbookai.taskservice.entity.CauHoi;
import com.planbookai.taskservice.entity.LoaiCauHoi;
import com.planbookai.taskservice.entity.MucDoKho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CauHoiService {
    CauHoi tao(CauHoi cauHoi);
    CauHoi capNhat(UUID id, CauHoi data);
    Optional<CauHoi> timTheoId(UUID id);
    void xoa(UUID id);

    Page<CauHoi> timKiem(String subject, String topic, LoaiCauHoi loai, MucDoKho doKho, String keyword, Pageable pageable);
}

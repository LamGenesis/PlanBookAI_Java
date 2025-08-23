package com.planbookai.taskservice.service;

import com.planbookai.taskservice.entity.DeThi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface DeThiService {
	DeThi tao(DeThi deThi);
	DeThi capNhat(UUID id, DeThi data);
	Optional<DeThi> timTheoId(UUID id);
	DeThi themCauHoi(UUID deThiId, UUID cauHoiId, int thuTu, BigDecimal diem);
	DeThi xoaCauHoi(UUID deThiId, UUID cauHoiId);
	DeThi publish(UUID deThiId);
	DeThi unpublish(UUID deThiId);
	BigDecimal tongDiem(UUID deThiId);
	void xoa(UUID id);

	Page<DeThi> timTheoGiaoVien(UUID teacherId, Pageable pageable);
	Page<DeThi> timKiemNangCao(UUID teacherId, String subject, LocalDateTime tuNgay, LocalDateTime denNgay, Pageable pageable);
	Page<DeThi> layTatCaDeThi(Pageable pageable);
}
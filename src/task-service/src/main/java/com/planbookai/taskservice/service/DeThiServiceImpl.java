package com.planbookai.taskservice.service;

import com.planbookai.taskservice.entity.CauHoi;
import com.planbookai.taskservice.entity.DeThi;
import com.planbookai.taskservice.repository.CauHoiRepository;
import com.planbookai.taskservice.repository.CauHoiTrongDeThiRepository;
import com.planbookai.taskservice.repository.DeThiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DeThiServiceImpl implements DeThiService {

	private final DeThiRepository deThiRepository;
	private final CauHoiRepository cauHoiRepository;
	private final CauHoiTrongDeThiRepository cauHoiTrongDeThiRepository;

	public DeThiServiceImpl(DeThiRepository deThiRepository,
	                        CauHoiRepository cauHoiRepository,
	                        CauHoiTrongDeThiRepository cauHoiTrongDeThiRepository) {
		this.deThiRepository = deThiRepository;
		this.cauHoiRepository = cauHoiRepository;
		this.cauHoiTrongDeThiRepository = cauHoiTrongDeThiRepository;
	}

	@Override
	@Transactional
	public DeThi tao(DeThi deThi) {
		if (deThi.getGiaoVienId() == null) {
			throw new IllegalArgumentException("Thiếu teacherId");
		}
		if (!deThi.coValidThoiGianLamBai()) {
			throw new IllegalArgumentException("Thời gian làm bài không hợp lệ (15-180 phút)");
		}
		return deThiRepository.save(deThi);
	}

    @Override
	@Transactional
	public DeThi capNhat(UUID id, DeThi data) {
		DeThi existing = deThiRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("Không tìm thấy đề thi: " + id));

		// Chỉ cập nhật các trường có giá trị (không null)
		if (data.getTieuDe() != null) {
			existing.setTieuDe(data.getTieuDe());
		}
		if (data.getMoTa() != null) {
			existing.setMoTa(data.getMoTa());
		}
		if (data.getMonHoc() != null) {
			existing.setMonHoc(data.getMonHoc());
		}
		if (data.getKhoi() != null) {
			existing.setKhoi(data.getKhoi());
		}
		if (data.getThoiGianLamBai() != null) {
			existing.setThoiGianLamBai(data.getThoiGianLamBai());
		}
		if (data.getTongDiem() != null) {
			existing.setTongDiem(data.getTongDiem());
		}
		if (data.getGiaoVienId() != null) {
			existing.setGiaoVienId(data.getGiaoVienId());
		}

		// Cập nhật thời gian
		existing.setThoiGianCapNhat(LocalDateTime.now());

		// Cập nhật trạng thái nếu có
		if (data.getTrangThai() != null && !data.getTrangThai().equals(existing.getTrangThai())) {
			existing.capNhatTrangThai(data.getTrangThai());
		}

		return deThiRepository.save(existing);
	}

	@Override
	public Optional<DeThi> timTheoId(UUID id) {
		return deThiRepository.findById(id);
	}

	@Override
	@Transactional
	public DeThi themCauHoi(UUID deThiId, UUID cauHoiId, int thuTu, BigDecimal diem) {
		DeThi deThi = deThiRepository.findById(deThiId)
			.orElseThrow(() -> new NoSuchElementException("Không tìm thấy đề thi: " + deThiId));

		CauHoi cauHoi = cauHoiRepository.findById(cauHoiId)
			.orElseThrow(() -> new NoSuchElementException("Không tìm thấy câu hỏi: " + cauHoiId));

		deThi.themCauHoi(cauHoi, thuTu, diem);
		return deThiRepository.save(deThi);
	}

	@Override
	@Transactional
	public DeThi xoaCauHoi(UUID deThiId, UUID cauHoiId) {
		DeThi deThi = deThiRepository.findById(deThiId)
			.orElseThrow(() -> new NoSuchElementException("Không tìm thấy đề thi: " + deThiId));

		deThi.xoaCauHoi(cauHoiId);
		return deThiRepository.save(deThi);
	}

	@Override
	@Transactional
	public DeThi publish(UUID deThiId) {
		DeThi deThi = deThiRepository.findById(deThiId)
			.orElseThrow(() -> new NoSuchElementException("Không tìm thấy đề thi: " + deThiId));

		deThi.capNhatTrangThai("PUBLISHED");
		return deThiRepository.save(deThi);
	}

	@Override
	@Transactional
	public DeThi unpublish(UUID deThiId) {
		DeThi deThi = deThiRepository.findById(deThiId)
			.orElseThrow(() -> new NoSuchElementException("Không tìm thấy đề thi: " + deThiId));

		deThi.capNhatTrangThai("DRAFT");
		return deThiRepository.save(deThi);
	}

	@Override
	public BigDecimal tongDiem(UUID deThiId) {
		BigDecimal sum = cauHoiTrongDeThiRepository.sumPointsByDeThiId(deThiId);
		return sum != null ? sum : BigDecimal.ZERO;
	}

	@Override
	@Transactional
	public void xoa(UUID id) {
		cauHoiTrongDeThiRepository.deleteByDeThiId(id);
		deThiRepository.deleteById(id);
	}

	@Override
	public Page<DeThi> timTheoGiaoVien(UUID teacherId, Pageable pageable) {
		List<DeThi> all = deThiRepository.findByGiaoVienId(teacherId);
		return toPage(all, pageable);
	}

	@Override
	public Page<DeThi> timKiemNangCao(UUID teacherId, String subject, LocalDateTime tuNgay, LocalDateTime denNgay, Pageable pageable) {
		List<DeThi> list = deThiRepository.findLatestByTeacher(teacherId);

		List<DeThi> filtered = list.stream()
			.filter(d -> subject == null || subject.equalsIgnoreCase(d.getMonHoc()))
			.filter(d -> tuNgay == null || (d.getThoiGianTao() != null && !d.getThoiGianTao().isBefore(tuNgay)))
			.filter(d -> denNgay == null || (d.getThoiGianTao() != null && !d.getThoiGianTao().isAfter(denNgay)))
			.collect(Collectors.toList());

		return toPage(filtered, pageable);
	}

	private Page<DeThi> toPage(List<DeThi> data, Pageable pageable) {
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), data.size());
		List<DeThi> content = start >= data.size() ? Collections.emptyList() : data.subList(start, end);
		return new PageImpl<>(content, pageable, data.size());
	}

	@Override
	public Page<DeThi> layTatCaDeThi(Pageable pageable) {
    	return deThiRepository.findAll(pageable);
	}
}
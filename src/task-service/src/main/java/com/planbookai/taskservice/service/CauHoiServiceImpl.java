package com.planbookai.taskservice.service;

import com.planbookai.taskservice.entity.CauHoi;
import com.planbookai.taskservice.entity.LoaiCauHoi;
import com.planbookai.taskservice.entity.MucDoKho;
import com.planbookai.taskservice.repository.CauHoiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CauHoiServiceImpl implements CauHoiService {

	private final CauHoiRepository cauHoiRepository;

	public CauHoiServiceImpl(CauHoiRepository cauHoiRepository) {
		this.cauHoiRepository = cauHoiRepository;
	}

	@Override
	@Transactional
	public CauHoi tao(CauHoi cauHoi) {
		return cauHoiRepository.save(cauHoi);
	}

	@Override
	@Transactional
	public CauHoi capNhat(UUID id, CauHoi data) {
		return cauHoiRepository.findById(id).map(existing -> {
			existing.setNoiDung(data.getNoiDung());
			existing.setLoai(data.getLoai());
			existing.setMucDoKho(data.getMucDoKho());
			existing.setMonHoc(data.getMonHoc());
			existing.setChuDe(data.getChuDe());
			existing.setDapAnDung(data.getDapAnDung());
			existing.setGiaiThich(data.getGiaiThich());
			existing.setTrangThai(data.getTrangThai());
			return cauHoiRepository.save(existing);
		}).orElseThrow(() -> new NoSuchElementException("Không tìm thấy câu hỏi: " + id));
	}

	@Override
	public Optional<CauHoi> timTheoId(UUID id) {
		return cauHoiRepository.findById(id);
	}

	@Override
	@Transactional
	public void xoa(UUID id) {
		cauHoiRepository.deleteById(id);
	}

	@Override
	public Page<CauHoi> timKiem(String subject, String topic, LoaiCauHoi loai, MucDoKho doKho, String keyword, Pageable pageable) {
		// Thay thế logic để handle null subject
		List<CauHoi> base;
		
		if (subject != null) {
			base = cauHoiRepository.findByMultipleCriteria(subject, loai, doKho);
		} else {
			// Nếu subject null, lấy tất cả câu hỏi
			base = cauHoiRepository.findAll();
		}

		List<CauHoi> filtered = base.stream()
			.filter(c -> topic == null || (c.getChuDe() != null && c.getChuDe().toLowerCase().contains(topic.toLowerCase())))
			.filter(c -> keyword == null || (c.getNoiDung() != null && c.getNoiDung().toLowerCase().contains(keyword.toLowerCase())))
			.collect(Collectors.toList());

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), filtered.size());
		List<CauHoi> pageContent = start >= filtered.size() ? Collections.emptyList() : filtered.subList(start, end);
		return new PageImpl<>(pageContent, pageable, filtered.size());
	}
}
package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.DangerGoodsCheckDetail;
import com.jxqixin.trafic.model.DangerGoodsCheck;
import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckDetailRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckRepository;
import com.jxqixin.trafic.service.IDangerGoodsCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DangerGoodsCheckServiceImpl extends CommonServiceImpl<DangerGoodsCheck> implements IDangerGoodsCheckService {
	@Autowired
	private DangerGoodsCheckRepository templateRepository;
	@Autowired
	private DangerGoodsCheckDetailRepository dangerGoodsCheckDetailRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findDangerGoodsChecks(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(pageable);
	}

	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}
	@Override
	public void updateDetails(String id, List<DangerGoodsCheckDetailRecord> detailList) {
		dangerGoodsCheckDetailRepository.deleteById(id);
		dangerGoodsCheckDetailRepository.saveAll(detailList);
	}
}

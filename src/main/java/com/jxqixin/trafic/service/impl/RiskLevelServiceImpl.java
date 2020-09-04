package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.RiskLevelDto;
import com.jxqixin.trafic.model.RiskLevel;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.RiskLevelRepository;
import com.jxqixin.trafic.service.IRiskLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@Service
@Transactional
public class RiskLevelServiceImpl extends CommonServiceImpl<RiskLevel> implements IRiskLevelService {
	@Autowired
	private RiskLevelRepository riskLevelRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return riskLevelRepository;
	}

	@Override
	public void deleteById(String id) {
		riskLevelRepository.deleteById(id);
	}
	@Override
	public Page findRiskLevels(RiskLevelDto riskLevelDto) {
		Pageable pageable = PageRequest.of(riskLevelDto.getPage(),riskLevelDto.getLimit(), Sort.Direction.DESC,"createDate");
		return riskLevelRepository.findAll(pageable);
	}
}

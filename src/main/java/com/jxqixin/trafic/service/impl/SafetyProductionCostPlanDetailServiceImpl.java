package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.SafetyProductionCostPlanDetailDto;
import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SafetyProductionCostPlanDetailRepository;
import com.jxqixin.trafic.service.ISafetyProductionCostPlanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Service
@Transactional
public class SafetyProductionCostPlanDetailServiceImpl extends CommonServiceImpl<SafetyProductionCostPlanDetail> implements ISafetyProductionCostPlanDetailService {
	@Autowired
	private SafetyProductionCostPlanDetailRepository safetyProductionCostPlanDetailRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return safetyProductionCostPlanDetailRepository;
	}
	@Override
	public void deleteById(String id) {
		safetyProductionCostPlanDetailRepository.deleteById(id);
	}
	@Override
	public Page findSafetyProductionCostPlanDetails(SafetyProductionCostPlanDetailDto safetyProductionCostPlanDetailDto) {
		Pageable pageable = PageRequest.of(safetyProductionCostPlanDetailDto.getPage(),safetyProductionCostPlanDetailDto.getLimit(), Sort.Direction.DESC,"billingDate");
		return safetyProductionCostPlanDetailRepository.findAll(pageable);
	}
}

package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCost;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SafetyProductionCostRepository;
import com.jxqixin.trafic.service.ISafetyProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Service
@Transactional
public class SafetyProductionCostServiceImpl extends CommonServiceImpl<SafetyProductionCost> implements ISafetyProductionCostService {
	@Autowired
	private SafetyProductionCostRepository safetyProductionCostRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return safetyProductionCostRepository;
	}
	@Override
	public SafetyProductionCost findByOrgAndYear(Org org, int intYear) {
		if(org == null) return null;
		return safetyProductionCostRepository.findByOrgIdAndYear(org.getId(),intYear);
	}
}

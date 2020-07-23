package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.model.SafetyInvestmentCategory;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SafetyInvestmentCategoryRepository;
import com.jxqixin.trafic.service.ISafetyInvestmentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class SafetyInvestmentCategoryServiceImpl extends CommonServiceImpl<SafetyInvestmentCategory> implements ISafetyInvestmentCategoryService {
	@Autowired
	private SafetyInvestmentCategoryRepository safetyInvestmentCategoryRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return safetyInvestmentCategoryRepository;
	}
}

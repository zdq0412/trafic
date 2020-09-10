package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyInvestmentCategory;
import com.jxqixin.trafic.model.SafetyProductionCost;
import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SafetyInvestmentCategoryRepository;
import com.jxqixin.trafic.repository.SafetyProductionCostPlanRepository;
import com.jxqixin.trafic.repository.SafetyProductionCostRepository;
import com.jxqixin.trafic.service.ISafetyProductionCostPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SafetyProductionCostPlanServiceImpl extends CommonServiceImpl<SafetyProductionCostPlan> implements ISafetyProductionCostPlanService {
	@Autowired
	private SafetyProductionCostPlanRepository safetyProductionCostPlanRepository;
	@Autowired
	private SafetyProductionCostRepository safetyProductionCostRepository;
	@Autowired
	private SafetyInvestmentCategoryRepository safetyInvestmentCategoryRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return safetyProductionCostPlanRepository;
	}

	@Override
	public List<SafetyProductionCostPlan> findAllPlans(Org org) {
		if(org!=null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			List<SafetyProductionCostPlan> list =  safetyProductionCostPlanRepository.findAllByOrgId(org.getId(),calendar.get(Calendar.YEAR));
			if(CollectionUtils.isEmpty(list)){
				SafetyProductionCost safetyProductionCost = new SafetyProductionCost();
				safetyProductionCost.setOrg(org);
				safetyProductionCost.setSafetyYear(calendar.get(Calendar.YEAR));
				safetyProductionCost = (SafetyProductionCost) safetyProductionCostRepository.save(safetyProductionCost);

				//添加安全生产费用使用计划
				List<SafetyInvestmentCategory> categories = safetyInvestmentCategoryRepository.findAll();
				if(!CollectionUtils.isEmpty(categories)){
					List<SafetyProductionCostPlan>  plans = new ArrayList<>();
					for(SafetyInvestmentCategory category : categories){
						SafetyProductionCostPlan productionCostPlan = new SafetyProductionCostPlan();
						productionCostPlan.setName(category.getName());
						productionCostPlan.setSerialNo(category.getSerialNo());
						productionCostPlan.setSafetyProductionCost(safetyProductionCost);

						plans.add(productionCostPlan);
					}
					safetyProductionCostPlanRepository.saveAll(plans);
				}
				list =  safetyProductionCostPlanRepository.findAllByOrgId(org.getId(),calendar.get(Calendar.YEAR));
			}else{
				return list;
			}
		}
		return new ArrayList<>();
	}
}

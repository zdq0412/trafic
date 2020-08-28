package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.*;
import com.jxqixin.trafic.service.ISafetyProductionCostService;
import com.jxqixin.trafic.vo.CostTotalVo;
import com.jxqixin.trafic.vo.SafetyProductionCostPlanVo;
import com.jxqixin.trafic.vo.SafetyProductionCostVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SafetyProductionCostServiceImpl extends CommonServiceImpl<SafetyProductionCost> implements ISafetyProductionCostService {
	@Autowired
	private SafetyProductionCostRepository safetyProductionCostRepository;
	@Autowired
	private SafetyInvestmentCategoryRepository safetyInvestmentCategoryRepository;
	@Autowired
	private SafetyProductionCostPlanRepository safetyProductionCostPlanRepository;
	@Autowired
	private SafetyProductionCostPlanDetailRepository safetyProductionCostPlanDetailRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return safetyProductionCostRepository;
	}
	@Override
	public SafetyProductionCost findByOrgAndYear(Org org, int intYear) {
		if(org == null) return null;
		SafetyProductionCost safetyProductionCost = safetyProductionCostRepository.findByOrgIdAndYear(org.getId(),intYear);
		if(safetyProductionCost == null){
			safetyProductionCost = new SafetyProductionCost();
			safetyProductionCost.setOrg(org);
			safetyProductionCost.setSafetyYear(intYear);

			//查询上一年实际可用和实际使用
			SafetyProductionCost lastYear = safetyProductionCostRepository.findByOrgIdAndYear(org.getId(),intYear-1);
			if(lastYear!=null){
				//查询实际使用,根据企业ID和年份
				Double lastActualUse = safetyProductionCostPlanDetailRepository.findSumByOrgIdAndYear(org.getId(),intYear-1);
				if(lastActualUse!=null){
					if(lastYear.getCurrentYearActualCost().longValue()-lastActualUse>0){
						safetyProductionCost.setLastYearCarryCost(new BigDecimal(lastYear.getCurrentYearActualCost().longValue()-lastActualUse));
					}
				}else {
					safetyProductionCost.setLastYearCarryCost(lastYear.getCurrentYearActualCost());
				}
			}

			safetyProductionCost.setCurrentYearActualCost(safetyProductionCost.getLastYearCarryCost());
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
		}

		//根据生产费用ID查找生产费用计划
		List<SafetyProductionCostPlan> productionCostPlans = safetyProductionCostPlanRepository.findBySafetyProductionCostId(safetyProductionCost.getId());
		safetyProductionCost.setPlans(productionCostPlans);
		return safetyProductionCost;
	}

	@Override
	public void updateCostAndPlans(SafetyProductionCost cost, List<SafetyProductionCostPlan> plans) {
		SafetyProductionCost safetyProductionCost = (SafetyProductionCost) safetyProductionCostRepository.findById(cost.getId()).get();
		if(!safetyProductionCost.isFillIn()) {
			safetyProductionCost.setCreateDate(new Date());
			safetyProductionCost.setFillIn(true);
		}
		safetyProductionCost.setCurrentYearActualCost(cost.getCurrentYearActualCost());
		safetyProductionCost.setCurrentYearCost(cost.getCurrentYearCost());
		safetyProductionCost.setLastYearActualIncome(cost.getLastYearActualIncome());
		safetyProductionCost.setLastYearCarryCost(cost.getLastYearCarryCost());

		safetyProductionCostRepository.save(safetyProductionCost);

		//更新安全费用使用计划
		updateSafetyProductionCostPlan(plans);
	}

	@Override
	public SafetyProductionCostVo findByOrgAndDate(Org org, Date d, String type) {
		if(org == null)return null;
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		//查询安全生产费用对象
		SafetyProductionCost cost = safetyProductionCostRepository.findByOrgIdAndYear(org.getId(),c.get(Calendar.YEAR));
		SafetyProductionCostVo vo = new SafetyProductionCostVo();
		//查询所有类别
		List<SafetyProductionCostPlan> plans = null;
		if(cost == null){
			cost = new SafetyProductionCost();
			List<SafetyInvestmentCategory> list = safetyInvestmentCategoryRepository.findAll();
			if(!CollectionUtils.isEmpty(list)){
				plans = new ArrayList<>();
				for(SafetyInvestmentCategory category : list){
					SafetyProductionCostPlan plan = new SafetyProductionCostPlan();
					BeanUtils.copyProperties(category,plan);
					plan.setPlanCost(new BigDecimal("0.00"));
					plans.add(plan);
				}
			}
		}else{
			plans = safetyProductionCostPlanRepository.findBySafetyProductionCostId(cost.getId());
		}
		BeanUtils.copyProperties(cost,vo);
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTime(now);

		List<Object[]> details = null;
		switch (type){
			case "year":{
				details = safetyProductionCostPlanDetailRepository.findByOrgIdAndYearGroupByPlanId(org.getId(),c.get(Calendar.YEAR));
				//本年度截止月日已投入使用的各类安全生产费用合计
				Double total = safetyProductionCostPlanDetailRepository.findSumByOrgIdAndDate(org.getId(),c.get(Calendar.YEAR),now);
				if(total==null)total=0d;
				vo.setTotal(total);
				break;
			} case "month":{
				details = safetyProductionCostPlanDetailRepository.findByOrgIdAndDateGroupByPlanId(org.getId(),c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1);
				Double total = safetyProductionCostPlanDetailRepository.findSumByOrgIdAndMonth(org.getId(),c.get(Calendar.MONTH)+1,now);
				if(total==null)total=0d;
				vo.setTotal(total);
				break;
			}
		}
		if(!CollectionUtils.isEmpty(plans)){
			for(SafetyProductionCostPlan plan : plans){
				plan.setPlanCost(new BigDecimal(0));
			}
			if(!CollectionUtils.isEmpty(details)){
				for(SafetyProductionCostPlan plan : plans){
					for(Object[] obj : details){
						if(plan.getId().equals(obj[0])){
							plan.setPlanCost(new BigDecimal(obj[1]==null?"0":obj[1]+""));
						}
					}
				}
			}
		}
		vo.setPlans(plans);
		return vo;
	}
	@Override
	public CostTotalVo findTotal(Org org, int year) {
		if(org == null)return null;
		//查询安全生产费用对象
		SafetyProductionCost cost = safetyProductionCostRepository.findByOrgIdAndYear(org.getId(),year);
		CostTotalVo vo = new CostTotalVo();
		//查询所有类别
		List<SafetyProductionCostPlan> planList = null;
		if(cost == null){
			cost = new SafetyProductionCost();
			List<SafetyInvestmentCategory> list = safetyInvestmentCategoryRepository.findAll();
			if(!CollectionUtils.isEmpty(list)){
				planList = new ArrayList<>();
				for(SafetyInvestmentCategory category : list){
					SafetyProductionCostPlan plan = new SafetyProductionCostPlan();
					BeanUtils.copyProperties(category,plan);
					plan.setPlanCost(new BigDecimal("0.00"));
					planList.add(plan);
				}
			}
		}else{
			planList = safetyProductionCostPlanRepository.findBySafetyProductionCostId(cost.getId());
		}
		BeanUtils.copyProperties(cost,vo);
		//查询所有类别
		//List<SafetyProductionCostPlan> planList = safetyProductionCostPlanRepository.findBySafetyProductionCostId(cost.getId());
		//本年度实际使用支出的安全费用
		Double total = safetyProductionCostPlanDetailRepository.findSumByOrgIdAndYear(org.getId(),year);
		if(total==null) total = 0d;
		vo.setCurrentYearActualUsed(new BigDecimal(total));
		BigDecimal costLeft = vo.getCurrentYearActualCost().subtract(vo.getCurrentYearActualUsed());
		vo.setCostLeft(costLeft.doubleValue()<0?new BigDecimal("0.00"):costLeft);

		List<Object[]> totalUse = safetyProductionCostPlanRepository.findTotalUse(org.getId(),year);
		List<SafetyProductionCostPlanVo> planVos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(totalUse)){
			for(Object[] objArr:totalUse){
				SafetyProductionCostPlanVo planVo = new SafetyProductionCostPlanVo();
				planVo.setName(objArr[0]+"");
				planVo.setOneMonth(objArr[1]==null?0:Double.parseDouble(objArr[1]+""));
				planVo.setTwoMonth(objArr[2]==null?0:Double.parseDouble(objArr[2]+""));
				planVo.setThreeMonth(objArr[3]==null?0:Double.parseDouble(objArr[3]+""));
				planVo.setFourMonth(objArr[4]==null?0:Double.parseDouble(objArr[4]+""));
				planVo.setFiveMonth(objArr[5]==null?0:Double.parseDouble(objArr[5]+""));
				planVo.setSixMonth(objArr[6]==null?0:Double.parseDouble(objArr[6]+""));
				planVo.setSevenMonth(objArr[7]==null?0:Double.parseDouble(objArr[7]+""));
				planVo.setEightMonth(objArr[8]==null?0:Double.parseDouble(objArr[8]+""));
				planVo.setNineMonth(objArr[9]==null?0:Double.parseDouble(objArr[9]+""));
				planVo.setTenMonth(objArr[10]==null?0:Double.parseDouble(objArr[10]+""));
				planVo.setElevenMonth(objArr[11]==null?0:Double.parseDouble(objArr[11]+""));
				planVo.setTwelveMonth(objArr[12]==null?0:Double.parseDouble(objArr[12]+""));
				planVo.setId(objArr[13]+"");

				planVos.add(planVo);
			};
		}
		vo.setPlans(planVos);

		if(!CollectionUtils.isEmpty(planList)){
			for(SafetyProductionCostPlan plan : planList){
				boolean isExist = false;
				for(SafetyProductionCostPlanVo planVo : planVos){
					if(plan.getId().equals(planVo.getId())){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					SafetyProductionCostPlanVo planVo = new SafetyProductionCostPlanVo();
					planVo.setId(plan.getId());
					planVo.setName(plan.getName());

					planVos.add(planVo);
				}
			}
		}
		return vo;
	}

	/**
	 * 更新生产费用使用计划(计划费用)
	 * @param plans
	 */
	private void updateSafetyProductionCostPlan(List<SafetyProductionCostPlan> plans) {
		if(!CollectionUtils.isEmpty(plans)){
			List<SafetyProductionCostPlan> list = new ArrayList<>();

			plans.forEach(plan ->{
				SafetyProductionCostPlan p = (SafetyProductionCostPlan) safetyProductionCostPlanRepository.findById(plan.getId()).get();
				p.setPlanCost(plan.getPlanCost());
				list.add(p);
			});

			safetyProductionCostPlanRepository.saveAll(list);
		}
	}
}

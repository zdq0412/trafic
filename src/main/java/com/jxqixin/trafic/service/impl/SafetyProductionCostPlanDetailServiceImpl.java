package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.SafetyProductionCostPlanDetailDto;
import com.jxqixin.trafic.model.Org;
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

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
	public Page findSafetyProductionCostPlanDetails(SafetyProductionCostPlanDetailDto safetyProductionCostPlanDetailDto, Org org) {
		Pageable pageable = PageRequest.of(safetyProductionCostPlanDetailDto.getPage(),safetyProductionCostPlanDetailDto.getLimit(), Sort.Direction.DESC,"billingDate");
		return safetyProductionCostPlanDetailRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<SafetyProductionCostPlanDetail,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}
}

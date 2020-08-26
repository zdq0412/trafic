package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.StandardizationDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import com.jxqixin.trafic.model.Standardization;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.StandardizationRepository;
import com.jxqixin.trafic.service.IStandardizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StandardizationServiceImpl extends CommonServiceImpl<Standardization> implements IStandardizationService {
	@Autowired
	private StandardizationRepository standardizationRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return standardizationRepository;
	}
	@Override
	public Page findStandardizations(StandardizationDto standardizationDto,Org org) {
		Pageable pageable = PageRequest.of(standardizationDto.getPage(),standardizationDto.getLimit(), Sort.Direction.DESC,"createDate");
		return standardizationRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<Standardization,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}


	@Override
	public void deleteById(String id) {
		standardizationRepository.deleteById(id);
	}
}

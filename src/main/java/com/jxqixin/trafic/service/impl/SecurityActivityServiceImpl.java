package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.SecurityActivityDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import com.jxqixin.trafic.model.SecurityActivity;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SecurityActivityRepository;
import com.jxqixin.trafic.service.ISecurityActivityService;
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
public class SecurityActivityServiceImpl extends CommonServiceImpl<SecurityActivity> implements ISecurityActivityService {
	@Autowired
	private SecurityActivityRepository securityBuildRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return securityBuildRepository;
	}
	@Override
	public Page findSecurityActivitys(SecurityActivityDto securityBuildDto,Org org) {
		Pageable pageable = PageRequest.of(securityBuildDto.getPage(),securityBuildDto.getLimit(), Sort.Direction.DESC,"createDate");
		return securityBuildRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<SecurityActivity,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}
	@Override
	public void deleteById(String id) {
		securityBuildRepository.deleteById(id);
	}
}

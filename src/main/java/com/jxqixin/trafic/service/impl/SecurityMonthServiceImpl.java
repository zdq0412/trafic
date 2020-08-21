package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.SecurityMonthDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SecurityMonth;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SecurityMonthRepository;
import com.jxqixin.trafic.service.ISecurityMonthService;
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
public class SecurityMonthServiceImpl extends CommonServiceImpl<SecurityMonth> implements ISecurityMonthService {
	@Autowired
	private SecurityMonthRepository securityBuildRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return securityBuildRepository;
	}
	@Override
	public Page findSecurityMonths(SecurityMonthDto securityBuildDto) {
		Pageable pageable = PageRequest.of(securityBuildDto.getPage(),securityBuildDto.getLimit(), Sort.Direction.DESC,"createDate");
		return securityBuildRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(securityBuildDto.getOrgId())){
					Join<SecurityMonth, Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),securityBuildDto.getOrgId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		securityBuildRepository.deleteById(id);
	}
}

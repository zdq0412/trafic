package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.SecurityBuildDto;
import com.jxqixin.trafic.model.SecurityBuild;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SecurityBuildRepository;
import com.jxqixin.trafic.service.ISecurityBuildService;
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
public class SecurityBuildServiceImpl extends CommonServiceImpl<SecurityBuild> implements ISecurityBuildService {
	@Autowired
	private SecurityBuildRepository securityBuildRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return securityBuildRepository;
	}
	@Override
	public Page findSecurityBuilds(SecurityBuildDto securityBuildDto) {
		Pageable pageable = PageRequest.of(securityBuildDto.getPage(),securityBuildDto.getLimit(), Sort.Direction.DESC,"createDate");
		return securityBuildRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(securityBuildDto.getOrgId())){
					Join<SecurityBuild, Org> orgJoin = root.join("org",JoinType.INNER);
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

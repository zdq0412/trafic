package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.SecurityCheckDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SecurityCheckRepository;
import com.jxqixin.trafic.repository.SecurityCheckTemplateRepository;
import com.jxqixin.trafic.service.ISecurityCheckService;
import org.springframework.beans.BeanUtils;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SecurityCheckServiceImpl extends CommonServiceImpl<SecurityCheck> implements ISecurityCheckService {
	@Autowired
	private SecurityCheckRepository templateRepository;
	@Autowired
	private SecurityCheckTemplateRepository securityCheckTemplateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findSecurityChecks(SecurityCheckDto securityCheckDto,Org org) {
		Pageable pageable = PageRequest.of(securityCheckDto.getPage(),securityCheckDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<SecurityCheck,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}

	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}

	@Override
	public SecurityCheck importTemplate(String templateId, Org org, String currentUsername) {
		SecurityCheckTemplate template = (SecurityCheckTemplate) securityCheckTemplateRepository.findById(templateId).get();
		SecurityCheck securityCheck = new SecurityCheck();
		BeanUtils.copyProperties(template,securityCheck);
		if(org!=null){
			securityCheck.setOrg(org);
		}
		securityCheck.setUrl(null);
		securityCheck.setRealPath(null);
		securityCheck.setCreateDate(new Date());
		securityCheck.setCreator(currentUsername);
		securityCheck = (SecurityCheck) templateRepository.save(securityCheck);
		return securityCheck;
	}
}

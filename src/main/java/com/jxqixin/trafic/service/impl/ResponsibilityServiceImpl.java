package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.ResponsibilityRepository;
import com.jxqixin.trafic.repository.ResponsibilityTemplateRepository;
import com.jxqixin.trafic.service.IResponsibilityService;
import com.jxqixin.trafic.util.FileUtil;
import org.springframework.beans.BeanUtils;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ResponsibilityServiceImpl extends CommonServiceImpl<Responsibility> implements IResponsibilityService {
	@Autowired
	private ResponsibilityRepository templateRepository;
	@Autowired
	private ResponsibilityTemplateRepository responsibilityTemplateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findResponsibilitys(NameDto nameDto, Org org) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		if (org == null) {
			return templateRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<>();
					if (!StringUtils.isEmpty(nameDto.getName())) {
						list.add(criteriaBuilder.like(root.get("name"), "%" + nameDto.getName() + "%"));
					}
					list.add(criteriaBuilder.isNull(root.get("org")));
					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		} else {
			return templateRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

					List<Predicate> list = new ArrayList<>();
					if (!StringUtils.isEmpty(nameDto.getName())) {
						list.add(criteriaBuilder.like(root.get("name"), "%" + nameDto.getName() + "%"));
					}
					Join<Responsibility, Org> orgJoin = root.join("org", JoinType.LEFT);
					list.add(criteriaBuilder.or(criteriaBuilder.equal(orgJoin.get("id"), org.getId())));

					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}
	}

	@Override
	public Responsibility findByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		Responsibility responsibility = (Responsibility) templateRepository.findById(id).get();
		if(!StringUtils.isEmpty(responsibility.getRealPath())){
			FileUtil.deleteFile(responsibility.getRealPath());
		}
		templateRepository.deleteById(id);
	}

	@Override
	public void importTemplate(String templateId, Org org, String currentUsername) {
		ResponsibilityTemplate template = (ResponsibilityTemplate) responsibilityTemplateRepository.findById(templateId).get();
		Responsibility responsibility = new Responsibility();
		BeanUtils.copyProperties(template,responsibility);
		if(org!=null){
			responsibility.setOrg(org);
		}
		responsibility.setUrl(null);
		responsibility.setRealPath(null);
		responsibility.setCreateDate(new Date());
		responsibility.setCreator(currentUsername);
		templateRepository.save(responsibility);
	}
}

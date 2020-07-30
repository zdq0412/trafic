package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckDetailRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckTemplateRepository;
import com.jxqixin.trafic.service.IDangerGoodsCheckTemplateService;
import com.jxqixin.trafic.util.FileUtil;
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
public class DangerGoodsCheckTemplateServiceImpl extends CommonServiceImpl<DangerGoodsCheckTemplate> implements IDangerGoodsCheckTemplateService {
	@Autowired
	private DangerGoodsCheckTemplateRepository templateRepository;
	@Autowired
	private DangerGoodsCheckDetailRepository dangerGoodsCheckDetailRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findDangerGoodsCheckTemplates(NameDto nameDto,Org org) {
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
					//企业字段不为空的
					if (!StringUtils.isEmpty(nameDto.getName())) {
						list.add(criteriaBuilder.like(root.get("name"), "%" + nameDto.getName() + "%"));
					}
					Join<DangerGoodsCheckTemplate, Org> orgJoin = root.join("org", JoinType.LEFT);
					list.add(criteriaBuilder.or(criteriaBuilder.equal(orgJoin.get("id"), org.getId()), criteriaBuilder.isNull(root.get("org"))));
					//企业类别
					if (org.getOrgCategory() != null) {
						Join<DangerGoodsCheckTemplate, OrgCategory> orgCategoryJoin = root.join("orgCategory", JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("orgCategory")), criteriaBuilder.equal(orgCategoryJoin.get("id"), org.getOrgCategory().getId())));
					}
					//省市区
					if (org.getProvince() != null) {
						Join<DangerGoodsCheckTemplate, Category> provinceJoin = root.join("province", JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("province")), criteriaBuilder.equal(provinceJoin.get("id"), org.getProvince().getId())));
					}
					if (org.getCity() != null) {
						Join<DangerGoodsCheckTemplate, Category> cityJoin = root.join("city", JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("city")), criteriaBuilder.equal(cityJoin.get("id"), org.getCity().getId())));
					}
					if (org.getRegion() != null) {
						Join<DangerGoodsCheckTemplate, Category> regionJoin = root.join("region", JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("region")), criteriaBuilder.equal(regionJoin.get("id"), org.getRegion().getId())));
					}

					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}
	}

	@Override
	public DangerGoodsCheckTemplate findByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		DangerGoodsCheckTemplate dangerGoodsCheckTemplate = (DangerGoodsCheckTemplate) dangerGoodsCheckDetailRepository.findById(id).get();
		if(!StringUtils.isEmpty(dangerGoodsCheckTemplate.getRealPath())){
			FileUtil.deleteFile(dangerGoodsCheckTemplate.getRealPath());
		}
		templateRepository.deleteById(id);
	}

	@Override
	public void updateDetails(String id, List<DangerGoodsCheckDetail> detailList) {
		dangerGoodsCheckDetailRepository.deleteByTemplateId(id);
		dangerGoodsCheckDetailRepository.saveAll(detailList);
	}
}

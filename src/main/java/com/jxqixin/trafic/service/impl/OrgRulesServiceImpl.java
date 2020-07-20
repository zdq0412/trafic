package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.NoticeRepository;
import com.jxqixin.trafic.repository.OrgRulesRepository;
import com.jxqixin.trafic.repository.RulesRepository;
import com.jxqixin.trafic.service.IOrgRulesService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrgRulesServiceImpl extends CommonServiceImpl<OrgRules> implements IOrgRulesService {
	@Autowired
	private OrgRulesRepository orgRulesRepository;
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private RulesRepository rulesRepository;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy");
	@Override
	public CommonRepository getCommonRepository() {
		return orgRulesRepository;
	}

	@Override
	public Page findOrgRules(NameDto nameDto, Org org) {
		Pageable pageable = PageRequest.of(nameDto.getPage(), nameDto.getLimit(), Sort.Direction.DESC, "rules.publishDate");
		if (org == null) {
			return orgRulesRepository.findAll(new Specification() {
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
			return orgRulesRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

					List<Predicate> list = new ArrayList<>();
					//企业字段不为空的
					if (!StringUtils.isEmpty(nameDto.getName())) {
						list.add(criteriaBuilder.like(root.get("name"), "%" + nameDto.getName() + "%"));
					}

					Join<OrgRules, Org> orgJoin = root.join("org", JoinType.INNER);
					Join<OrgLaw, Rules> lawJoin = root.join("rules", JoinType.INNER);
					list.add(criteriaBuilder.and(criteriaBuilder.equal(lawJoin.get("province"), orgJoin.get("province")),
							criteriaBuilder.equal(lawJoin.get("city"), orgJoin.get("city")),
							criteriaBuilder.equal(lawJoin.get("region"), orgJoin.get("region")), criteriaBuilder.equal(orgJoin.get("id"), org.getId())));

					OrgCategory orgCategory = org.getOrgCategory();
					if (orgCategory != null) {
						Join<Law, OrgCategory> lawOrgCategoryJoin = lawJoin.join("orgCategory", JoinType.INNER);
						Join<Org, OrgCategory> orgOrgCategoryJoin = orgJoin.join("orgCategory", JoinType.INNER);
						list.add(criteriaBuilder.equal(orgOrgCategoryJoin.get("id"), lawOrgCategoryJoin.get("id")));
					}
					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}
	}

	@Override
	public void deleteById(String id) {
		OrgRules orgRules = (OrgRules) orgRulesRepository.findById(id).get();
		orgRulesRepository.deleteById(id);

		rulesRepository.deleteById(orgRules.getRules().getId());
	}

	@Override
	public void publishRules(String id) {
		OrgRules orgRules = (OrgRules) orgRulesRepository.findById(id).get();
		orgRules.setSended(true);
		orgRulesRepository.save(orgRules);
		//向notice中添加一条记录
		Notice notice = new Notice();
		BeanUtils.copyProperties(orgRules.getRules(),notice);
		notice.setOrg(orgRules.getOrg());
		noticeRepository.save(notice);
	}
}

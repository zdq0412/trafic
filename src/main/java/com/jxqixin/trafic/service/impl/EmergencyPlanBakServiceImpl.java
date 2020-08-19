package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.EmergencyPlanBakDto;
import com.jxqixin.trafic.model.EmergencyPlanBak;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.EmergencyPlanBakRepository;
import com.jxqixin.trafic.repository.PreplanDrillRecordRepository;
import com.jxqixin.trafic.service.IEmergencyPlanBakService;
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
public class EmergencyPlanBakServiceImpl extends CommonServiceImpl<EmergencyPlanBak> implements IEmergencyPlanBakService {
	@Autowired
	private EmergencyPlanBakRepository emergencyPlanBakRepository;
	@Autowired
	private PreplanDrillRecordRepository preplanDrillRecordRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return emergencyPlanBakRepository;
	}
	@Override
	public Page findEmergencyPlanBaks(EmergencyPlanBakDto emergencyPlanBakDto, Org org) {
		Pageable pageable = PageRequest.of(emergencyPlanBakDto.getPage(),emergencyPlanBakDto.getLimit(), Sort.Direction.DESC,"createDate");
		return emergencyPlanBakRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(emergencyPlanBakDto.getOrgId())){
					Join<EmergencyPlanBak,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		preplanDrillRecordRepository.deleteByEmergencyPlanBakId(id);
		emergencyPlanBakRepository.deleteById(id);
	}
}

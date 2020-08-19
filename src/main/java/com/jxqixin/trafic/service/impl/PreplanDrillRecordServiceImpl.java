package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.PreplanDrillRecordDto;
import com.jxqixin.trafic.model.EmergencyPlanBak;
import com.jxqixin.trafic.model.PreplanDrillRecord;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.PreplanDrillRecordRepository;
import com.jxqixin.trafic.service.IPreplanDrillRecordService;
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
public class PreplanDrillRecordServiceImpl extends CommonServiceImpl<PreplanDrillRecord> implements IPreplanDrillRecordService {
	@Autowired
	private PreplanDrillRecordRepository preplanDrillRecordRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return preplanDrillRecordRepository;
	}
	@Override
	public Page findPreplanDrillRecords(PreplanDrillRecordDto preplanDrillRecordDto) {
		Pageable pageable = PageRequest.of(preplanDrillRecordDto.getPage(),preplanDrillRecordDto.getLimit(), Sort.Direction.DESC,"createDate");
		return preplanDrillRecordRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				Join<PreplanDrillRecord, EmergencyPlanBak> emergencyPlanBakJoin = root.join("emergencyPlanBak",JoinType.INNER);
				if(!StringUtils.isEmpty(preplanDrillRecordDto.getEmergencyPlanBakId())){
					list.add(criteriaBuilder.equal(emergencyPlanBakJoin.get("id"),preplanDrillRecordDto.getEmergencyPlanBakId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		preplanDrillRecordRepository.deleteById(id);
	}

	@Override
	public Long queryCountByEmergencyPlanBakId(String emergencyPlanBakId) {
		return preplanDrillRecordRepository.queryCountByEmergencyPlanBakId(emergencyPlanBakId);
	}
}

package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.FourRecordDto;
import com.jxqixin.trafic.model.FourRecord;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import com.jxqixin.trafic.repository.FourRecordRepository;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.service.IFourRecordService;
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
public class FourRecordServiceImpl extends CommonServiceImpl<FourRecord> implements IFourRecordService {
	@Autowired
	private FourRecordRepository fourRecordRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return fourRecordRepository;
	}
	@Override
	public Page findFourRecords(FourRecordDto fourRecordDto,Org org) {
		Pageable pageable = PageRequest.of(fourRecordDto.getPage(),fourRecordDto.getLimit(), Sort.Direction.DESC,"createDate");
		return fourRecordRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<FourRecord,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}
	@Override
	public void deleteById(String id) {
		fourRecordRepository.deleteById(id);
	}
}

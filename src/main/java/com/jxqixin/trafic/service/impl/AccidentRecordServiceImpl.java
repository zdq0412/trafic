package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.AccidentRecordDto;
import com.jxqixin.trafic.model.AccidentRecord;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.AccidentRecordRepository;
import com.jxqixin.trafic.service.IAccidentRecordService;
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
public class AccidentRecordServiceImpl extends CommonServiceImpl<AccidentRecord> implements IAccidentRecordService {
	@Autowired
	private AccidentRecordRepository accidentRecordRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return accidentRecordRepository;
	}
	@Override
	public Page findAccidentRecords(AccidentRecordDto accidentRecordDto) {
		Pageable pageable = PageRequest.of(accidentRecordDto.getPage(),accidentRecordDto.getLimit(), Sort.Direction.DESC,"createDate");
		return accidentRecordRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(accidentRecordDto.getOrgId())){
					Join<AccidentRecord, Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),accidentRecordDto.getOrgId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		accidentRecordRepository.deleteById(id);
	}
}

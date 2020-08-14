package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.InductionTrainingDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.InductionTraining;
import com.jxqixin.trafic.model.JobHistory;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.InductionTrainingRepository;
import com.jxqixin.trafic.service.IInductionTrainingService;
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
public class InductionTrainingServiceImpl extends CommonServiceImpl<InductionTraining> implements IInductionTrainingService {
	@Autowired
	private InductionTrainingRepository inductionTrainingRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return inductionTrainingRepository;
	}

	@Override
	public Page findInductionTrainings(InductionTrainingDto inductionTrainingDto) {
		Pageable pageable = PageRequest.of(inductionTrainingDto.getPage(),inductionTrainingDto.getLimit(), Sort.Direction.DESC,"createDate");
		return inductionTrainingRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(inductionTrainingDto.getEmpId())){
					Join<InductionTraining, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),inductionTrainingDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}

	@Override
	public void deleteById(String id) {
		InductionTraining inductionTraining = (InductionTraining) inductionTrainingRepository.findById(id).get();
		inductionTraining.setDeleted(true);
		inductionTrainingRepository.save(inductionTraining);
	}
}
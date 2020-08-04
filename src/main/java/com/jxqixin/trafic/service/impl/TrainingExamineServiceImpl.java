package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.TrainingExamineDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.IDCard;
import com.jxqixin.trafic.model.TrainingExamine;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.TrainingExamineRepository;
import com.jxqixin.trafic.service.ITrainingExamineService;
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
public class TrainingExamineServiceImpl extends CommonServiceImpl<TrainingExamine> implements ITrainingExamineService {
	@Autowired
	private TrainingExamineRepository trainingExamineRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return trainingExamineRepository;
	}

	@Override
	public Page findTrainingExamines(TrainingExamineDto trainingExamineDto) {
		Pageable pageable = PageRequest.of(trainingExamineDto.getPage(),trainingExamineDto.getLimit(), Sort.Direction.DESC,"createDate");
		return trainingExamineRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(trainingExamineDto.getEmpId())){
					Join<TrainingExamine, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),trainingExamineDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		TrainingExamine trainingExamine = (TrainingExamine) trainingExamineRepository.findById(id).get();
		trainingExamine.setDeleted(true);

		trainingExamineRepository.save(trainingExamine);
	}
}

package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.constant.EmpArchiveType;
import com.jxqixin.trafic.dto.SafetyResponsibilityAgreementDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.model.SafetyResponsibilityAgreement;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SafetyResponsibilityAgreementRepository;
import com.jxqixin.trafic.service.IEmployeeService;
import com.jxqixin.trafic.service.ISafetyResponsibilityAgreementService;
import com.jxqixin.trafic.util.StringUtil;
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
public class SafetyResponsibilityAgreementServiceImpl extends CommonServiceImpl<SafetyResponsibilityAgreement> implements ISafetyResponsibilityAgreementService {
	@Autowired
	private SafetyResponsibilityAgreementRepository safetyResponsibilityAgreementRepository;
	@Autowired
	private IEmployeeService employeeService;
	@Override
	public CommonRepository getCommonRepository() {
		return safetyResponsibilityAgreementRepository;
	}

	@Override
	public Page findSafetyResponsibilityAgreements(SafetyResponsibilityAgreementDto safetyResponsibilityAgreementDto) {
		Pageable pageable = PageRequest.of(safetyResponsibilityAgreementDto.getPage(),safetyResponsibilityAgreementDto.getLimit(), Sort.Direction.DESC,"createDate");
		return safetyResponsibilityAgreementRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(safetyResponsibilityAgreementDto.getEmpId())){
					Join<SafetyResponsibilityAgreement, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),safetyResponsibilityAgreementDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}

	@Override
	public void deleteById(String id) {
		SafetyResponsibilityAgreement safetyResponsibilityAgreement = (SafetyResponsibilityAgreement) safetyResponsibilityAgreementRepository.findById(id).get();
		safetyResponsibilityAgreement.setDeleted(true);
		safetyResponsibilityAgreementRepository.save(safetyResponsibilityAgreement);

		setArchiveCode(safetyResponsibilityAgreement);
	}

	@Override
	public void addSafetyResponsibilityAgreement(SafetyResponsibilityAgreement safetyResponsibilityAgreement) {
		addObj(safetyResponsibilityAgreement);

		setArchiveCode(safetyResponsibilityAgreement);
	}

	/**
	 * 设置档案码
	 * @param safetyResponsibilityAgreement
	 */
	private void setArchiveCode(SafetyResponsibilityAgreement safetyResponsibilityAgreement){
		Long count = safetyResponsibilityAgreementRepository.count((root, criteriaQuery, criteriaBuilder) -> {
			Join<Resume,Employee> employeeJoin = root.join("employee",JoinType.INNER);
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("deleted"),false),criteriaBuilder.equal(employeeJoin.get("id"),safetyResponsibilityAgreement.getEmployee().getId()));
		});
		if(count == null)count = 0l;
		Employee employee = employeeService.queryObjById(safetyResponsibilityAgreement.getEmployee().getId());
		employee.setArchiveCode(StringUtil.handleEmpArchiveCode(EmpArchiveType.SAFETYRESPONSIBILITYAGREEMENT,employee.getArchiveCode(),count.intValue()));
		employeeService.updateObj(employee);
	}
}

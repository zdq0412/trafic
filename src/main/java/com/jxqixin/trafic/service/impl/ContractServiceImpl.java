package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.ContractDto;
import com.jxqixin.trafic.model.Contract;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.ContractRepository;
import com.jxqixin.trafic.service.IContractService;
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
public class ContractServiceImpl extends CommonServiceImpl<Contract> implements IContractService {
	@Autowired
	private ContractRepository contractRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return contractRepository;
	}
	@Override
	public Page findContracts(ContractDto contractDto) {
		Pageable pageable = PageRequest.of(contractDto.getPage(),contractDto.getLimit(), Sort.Direction.DESC,"createDate");
		return contractRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(contractDto.getEmpId())){
					Join<Contract, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),contractDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}


	@Override
	public void deleteById(String id) {
		Contract contract = (Contract) contractRepository.findById(id).get();
		contract.setDeleted(true);

		contractRepository.save(contract);
	}
}

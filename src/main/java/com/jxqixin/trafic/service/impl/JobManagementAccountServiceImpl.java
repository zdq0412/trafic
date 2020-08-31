package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.JobManagementAccountDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JobManagementAccount;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.JobManagementAccountRepository;
import com.jxqixin.trafic.service.IJobManagementAccountService;
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
public class JobManagementAccountServiceImpl extends CommonServiceImpl<JobManagementAccount> implements IJobManagementAccountService {
	@Autowired
	private JobManagementAccountRepository jobManagementAccountRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return jobManagementAccountRepository;
	}
	@Override
	public Page findJobManagementAccounts(JobManagementAccountDto jobManagementAccountDto, Org org) {
		Pageable pageable = PageRequest.of(jobManagementAccountDto.getPage(),jobManagementAccountDto.getLimit(), Sort.Direction.DESC,"createDate");
		return jobManagementAccountRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<JobManagementAccount,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				if(!StringUtils.isEmpty(jobManagementAccountDto.getJobManagementAccountTypeId())){
					Join<JobManagementAccount, Category> categoryJoin = root.join("type",JoinType.INNER);
					list.add(criteriaBuilder.equal(categoryJoin.get("id"),jobManagementAccountDto.getJobManagementAccountTypeId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}
	@Override
	public void deleteById(String id) {
		jobManagementAccountRepository.deleteById(id);
	}
}

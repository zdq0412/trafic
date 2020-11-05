package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.constant.EmpArchiveType;
import com.jxqixin.trafic.dto.JobHistoryDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.IDCard;
import com.jxqixin.trafic.model.JobHistory;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.JobHistoryRepository;
import com.jxqixin.trafic.service.IEmployeeService;
import com.jxqixin.trafic.service.IJobHistoryService;
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
public class JobHistoryServiceImpl extends CommonServiceImpl<JobHistory> implements IJobHistoryService {
	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	@Autowired
	private IEmployeeService employeeService;
	@Override
	public CommonRepository getCommonRepository() {
		return jobHistoryRepository;
	}
	@Override
	public Page findJobHistorys(JobHistoryDto jobHistoryDto) {
		Pageable pageable = PageRequest.of(jobHistoryDto.getPage(),jobHistoryDto.getLimit(), Sort.Direction.DESC,"createDate");
		return jobHistoryRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(jobHistoryDto.getEmpId())){
					Join<JobHistory, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),jobHistoryDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}

	@Override
	public void deleteById(String id) {
		JobHistory jobHistory = (JobHistory) jobHistoryRepository.findById(id).get();
		jobHistory.setDeleted(true);
		jobHistoryRepository.save(jobHistory);

		setArchiveCode(jobHistory);
	}

	@Override
	public void addJobHistory(JobHistory jobHistory) {
		addObj(jobHistory);
		setArchiveCode(jobHistory);
	}

	/**
	 * 设置档案码
	 * @param jobHistory
	 */
	private void setArchiveCode(JobHistory jobHistory){
		Long count = jobHistoryRepository.count((root, criteriaQuery, criteriaBuilder) -> {
			Join<JobHistory,Employee> employeeJoin = root.join("employee",JoinType.INNER);
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("deleted"),false),criteriaBuilder.equal(employeeJoin.get("id"),jobHistory.getEmployee().getId()));
		});
		if(count == null)count = 0l;
		Employee employee = employeeService.queryObjById(jobHistory.getEmployee().getId());
		employee.setArchiveCode(StringUtil.handleEmpArchiveCode(EmpArchiveType.JOBHISTORY,employee.getArchiveCode(),count.intValue()));
		employeeService.updateObj(employee);
	}
}

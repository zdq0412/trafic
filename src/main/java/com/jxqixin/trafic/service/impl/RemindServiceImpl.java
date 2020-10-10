package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.RemindDto;
import com.jxqixin.trafic.mapper.RemindMapper;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.RemindRepository;
import com.jxqixin.trafic.service.IRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class RemindServiceImpl extends CommonServiceImpl<Remind> implements IRemindService {
	@Autowired
	private RemindRepository remindRepository;
	@Resource
	private RemindMapper remindMapper;
	@Override
	public CommonRepository getCommonRepository() {
		return remindRepository;
	}
	@Override
	public Page findReminds(RemindDto remindDto, Org org) {
		Pageable pageable = PageRequest.of(remindDto.getPage(),remindDto.getLimit(), Sort.Direction.DESC,"createDate");
		return remindRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(org!=null){
					Join<AccidentRecord,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}
				list.add(criteriaBuilder.equal(root.get("deleted"),true));
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		Remind remind = (Remind) remindRepository.findById(id).get();
		remind.setDeleted(true);
		remindRepository.save(remind);
	}
	@Override
	public List<OrgDoc> findWarningOrgDoc() {
		return remindMapper.findWarningOrgDoc();
	}
	@Override
	public List<Contract> findWarningContract() {
		return remindMapper.findWarningContract();
	}
	@Override
	public List<QualificationDocument> findWarningQualificationDocument() {
		return remindMapper.findWarningQualificationDocument();
	}
	@Override
	public void saveAll(List<Remind> reminds) {
		remindRepository.saveAll(reminds);
	}
	@Override
	public void updateRemind2Expired() {
		remindMapper.updateRemind2Expired();
	}
	@Override
	public List<OrgDoc> findExpiredOrgDoc() {
		return remindMapper.findExpiredOrgDoc();
	}
	@Override
	public List<QualificationDocument> findExpiredQualificationDocument() {
		return remindMapper.findExpiredQualificationDocument();
	}
	@Override
	public List<Contract> findExpiredContract() {
		return remindMapper.findExpiredContract();
	}
	@Override
	public List<Meeting> findCurrentMonthMeeting(String orgId) {
		return remindMapper.findCurrentMonthMeeting(orgId);
	}

	@Override
	public List<Training> findCurrentMonthTraining(String orgId) {
		return remindMapper.findCurrentMonthTraining(orgId);
	}

	@Override
	public List<SecurityCheck> findCurrentMonthSecurityCheck(String orgId) {
		return remindMapper.findCurrentMonthSecurityCheck(orgId);
	}
	@Override
	public List<EmergencyPlanDrill> findPreHalfYearEmergencyPlanDrill(String orgId) {
		return remindMapper.findPreHalfYearEmergencyPlanDrill(orgId);
	}
	@Override
	public void updateEmergencyPlanDrill2Expired() {
		remindMapper.updateEmergencyPlanDrill2Expired();
	}

	@Override
	public List<SecurityExamination> findCurrentYearSecurityExamination(String orgId) {
		return remindMapper.findCurrentYearSecurityExamination(orgId);
	}
	@Override
	public List<GoalExamination> findCurrentYearGoalExamination(String orgId) {
		return remindMapper.findCurrentYearGoalExamination(orgId);
	}
	@Override
	public List<HealthyRecord> findCurrentYearHealthyRecord(String orgId) {
		return remindMapper.findCurrentYearHealthyRecord(orgId);
	}
	@Override
	public List<Standardization> findCurrentYearStandardization(String orgId) {
		return remindMapper.findCurrentYearStandardization(orgId);
	}
	@Override
	public void updateAccountOfYear2Expired() {
		remindMapper.updateAccountOfYear2Expired();
	}
	@Override
	public void updatePreAccountOfMonth2Expired() {
		remindMapper.updatePreAccountOfMonth2Expired();
	}
	@Override
	public List<Remind> findAllReminds(Org org) {
		return remindRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(org!=null){
					list.add(criteriaBuilder.equal(root.get("orgId"),org.getId()));
				}

				Date now = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.YEAR,-1);

				list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"),c.getTime()));

				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		});
	}
	@Override
	public Org findOrgByOrgDocId(String orgDocId) {
		return remindMapper.findOrgByOrgDocId(orgDocId);
	}
	@Override
	public Employee findEmployeeByContractId(String contractId) {
		return remindMapper.findEmployeeByContractId(contractId);
	}
	@Override
	public Org findOrgByEmployeeId(String employeeId) {
		return remindMapper.findOrgByEmployeeId(employeeId);
	}
	@Override
	public Employee findEmployeeByQualificationDocumentId(String qualificationDocumentId) {
		return remindMapper.findEmployeeByQualificationDocumentId(qualificationDocumentId);
	}
	@Override
	public void deleteRemind(String id) {
		Date now = new Date();
		//根据ID查找
		Remind remind = (Remind) remindRepository.findById(id).get();
		//资质文件过期、月计划、半年计划、年计划
		if(!StringUtils.isEmpty(remind.getSrcId())){ //资质文件
			Date endDate = remindMapper.findEndDateById(remind.getSrcId(),remind.getTableName());
			if(endDate!=null) {
				if (endDate.getTime() < now.getTime()) {
					throw new RuntimeException();
				}
			}
		}else{
			Date endDate = remind.getEndDate();
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			switch(remind.getType()){
				case "月计划":{
					c.set(Calendar.DAY_OF_MONTH,1);
					Date beginDate = c.getTime();
					Long count = remindMapper.findPlanBetween(beginDate,endDate,remind.getColName(),remind.getTableName());
					if(count== null || count==0){
						throw new RuntimeException();
					}
					break;
				}
				case "半年计划":{
					c.add(Calendar.MONTH,-6);
					c.set(Calendar.DAY_OF_MONTH,1);
					Date beginDate = c.getTime();
					Long count = remindMapper.findPlanBetween(beginDate,endDate,remind.getColName(),remind.getTableName());
					if(count== null || count==0){
						throw new RuntimeException();
					}
					break;
				}
				case "年计划":{
					c.set(Calendar.MONTH,1);
					c.set(Calendar.DAY_OF_MONTH,1);
					Date beginDate = c.getTime();
					Long count = remindMapper.findPlanBetween(beginDate,endDate,remind.getColName(),remind.getTableName());
					if(count== null || count==0){
						throw new RuntimeException();
					}
					break;
				}
			}
		}
		//已处理，执行删除操作
		remind.setDeleted(true);
		remindRepository.save(remind);
	}
}

package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.constant.EmpArchiveType;
import com.jxqixin.trafic.dto.ResumeDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.ResumeRepository;
import com.jxqixin.trafic.service.IEmployeeService;
import com.jxqixin.trafic.service.IResumeService;
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
public class ResumeServiceImpl extends CommonServiceImpl<Resume> implements IResumeService {
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private IEmployeeService employeeService;
	@Override
	public CommonRepository getCommonRepository() {
		return resumeRepository;
	}
	@Override
	public Page findResumes(ResumeDto resumeDto) {
		Pageable pageable = PageRequest.of(resumeDto.getPage(),resumeDto.getLimit(), Sort.Direction.DESC,"createDate");
		return resumeRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(resumeDto.getEmpId())){
					Join<Resume, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),resumeDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		Resume resume = (Resume) resumeRepository.findById(id).get();
		resume.setDeleted(true);
		resumeRepository.save(resume);

		setArchiveCode(resume);
	}
	@Override
	public void addResume(Resume resume) {
		addObj(resume);
		setArchiveCode(resume);
	}
	/**
	 * 设置档案码
	 * @param resume
	 */
	private void setArchiveCode(Resume resume){
		Long count = resumeRepository.count((root, criteriaQuery, criteriaBuilder) -> {
			Join<Resume,Employee> employeeJoin = root.join("employee",JoinType.INNER);
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("deleted"),false),criteriaBuilder.equal(employeeJoin.get("id"),resume.getEmployee().getId()));
		});
		if(count == null)count = 0l;
		Employee employee = employeeService.queryObjById(resume.getEmployee().getId());
		employee.setArchiveCode(StringUtil.handleEmpArchiveCode(EmpArchiveType.RESUME,employee.getArchiveCode(),count.intValue()));
		employeeService.updateObj(employee);
	}
}

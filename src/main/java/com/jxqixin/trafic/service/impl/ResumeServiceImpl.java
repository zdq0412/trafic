package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.ResumeDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.IDCard;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.repository.*;
import com.jxqixin.trafic.service.IResumeService;
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
	}
}

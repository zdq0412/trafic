package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DepartmentRepository;
import com.jxqixin.trafic.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class DepartmentServiceImpl extends CommonServiceImpl<Department> implements IDepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return departmentRepository;
	}

	@Override
	public Page findDepartments(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return departmentRepository.findAll(new NameSpecification(nameDto),pageable);
	}

	@Override
	public Department findByName(String name) {
		return departmentRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {

	}
}

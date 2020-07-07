package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.EmployeeRepository;
import com.jxqixin.trafic.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class EmployeeServiceImpl extends CommonServiceImpl<Employee> implements IEmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return employeeRepository;
	}

	@Override
	public void deleteById(String id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Page findEmployees(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return employeeRepository.findAll(new NameSpecification(nameDto),pageable);
	}
}

package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.EmployeePosition;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.EmployeePositionRepository;
import com.jxqixin.trafic.service.IEmployeePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeePositionServiceImpl extends CommonServiceImpl<EmployeePosition> implements IEmployeePositionService {
	@Autowired
	private EmployeePositionRepository employeePositionRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return employeePositionRepository;
	}

	@Override
	public List<String> findIdsByEmployeeId(String employeeId) {
		return employeePositionRepository.findIdsByEmployeeId(employeeId);
	}
}

package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.PositionDto;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.EmployeePosition;
import com.jxqixin.trafic.model.Position;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.EmployeePositionRepository;
import com.jxqixin.trafic.repository.EmployeeRepository;
import com.jxqixin.trafic.repository.PositionRepository;
import com.jxqixin.trafic.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PositionServiceImpl extends CommonServiceImpl<Position> implements IPositionService {
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeePositionRepository employeePositionRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return positionRepository;
	}

	@Override
	public Page findPositions(PositionDto positionDto) {
		Pageable pageable = PageRequest.of(positionDto.getPage(),positionDto.getLimit());
		return positionRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();

				if(!StringUtils.isEmpty(positionDto.getDepartmentId())){
					Join<Position, Department> departmentJoin = root.join("department",JoinType.INNER);
					list.add(criteriaBuilder.equal(departmentJoin.get("id"),positionDto.getDepartmentId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}

	@Override
	public void deleteById(String id) {
		//根据职位ID查找员工人数
		Long count = employeeRepository.findCountByPositionId(id);
		if(count!=null && count>0){
			throw new RuntimeException("该职位下存在人员，不允许删除!");
		}

		positionRepository.deleteById(id);
	}

	@Override
	public List<Position> findByDepartmentId(String departmentId) {
		return positionRepository.findByDepartmentId(departmentId);
	}

	@Override
	public void assign2Employee(String[] positionIdArray, String employeeId) {
		if(positionIdArray!=null && positionIdArray.length>0) {
			employeePositionRepository.deleteByEmployeeId(employeeId);
			List<EmployeePosition> list = new ArrayList<>();
			Employee employee = new Employee();
			employee.setId(employeeId);
			for (int i = 0; i < positionIdArray.length; i++) {
				Position position = new Position();
				position.setId(positionIdArray[i]);
				EmployeePosition employeePosition = new EmployeePosition();
				employeePosition.setEmployee(employee);
				employeePosition.setPosition(position);
				list.add(employeePosition);
			}
			employeePositionRepository.saveAll(list);
		}
	}
}

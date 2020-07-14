package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DepartmentRepository;
import com.jxqixin.trafic.repository.EmployeeRepository;
import com.jxqixin.trafic.repository.PositionRepository;
import com.jxqixin.trafic.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl extends CommonServiceImpl<Department> implements IDepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return departmentRepository;
	}

	@Override
	public Page findDepartments(NameDto nameDto,Org org) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		Page page =  departmentRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(!StringUtils.isEmpty(nameDto.getName())){
					list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName() +"%"));
				}

				if(org != null){
					Join<Department,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				list.add(criteriaBuilder.isNull(root.get("parent")));
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);

		if(page.getTotalElements()>0){
			page.getContent().forEach(department -> {
				buildChildren((Department) department);
			});
		}

		return page;
	}

	@Override
	public Department findByName(String name,Org org) {
		if(org==null){
			org = new Org();
		}
		return departmentRepository.findByName(name,org.getId());
	}

	@Override
	public void deleteById(String id) {
		//根据部门ID查找员工，如果部门下存在员工，则不允许删除
		Long employeeCount = employeeRepository.findCountByDepartmentId(id);
		if(employeeCount!=null && employeeCount>0){
			throw new RuntimeException("部门下存在员工，不允许删除!");
		}

		//删除部门下的职位
		positionRepository.deleteByDepartmentId(id);

		departmentRepository.deleteById(id);
	}
	@Override
	public List<Department> findTree(Org org) {
		//查找所有根部门
		if(org==null){
			return new ArrayList<>();
		}
		List<Department> roots = departmentRepository.findRoots(org.getId());
		//构建树形结构
		if(!CollectionUtils.isEmpty(roots)){
			roots.forEach(department -> {
				buildChildren(department);
			});
		}
		return roots;
	}

	@Override
	public List<String> findParent(String id) {
		List<String> list = new ArrayList<>();
		Department department = (Department) departmentRepository.findById(id).get();
		while(department!=null){
			list.add(department.getId());
			department = department.getParent();
		}
		Collections.reverse(list);
		return list;
	}

	/**
	 * 构建子部门
	 * @param department
	 */
	private void buildChildren(Department department){
		//根据id查找子类别
		List<Department> children = departmentRepository.findByParentId(department.getId());
		if(!CollectionUtils.isEmpty(children)){
			department.setChildren(children);
			children.forEach(c -> {
				buildChildren(c);
			});
		}else{
			return ;
		}
	}
}

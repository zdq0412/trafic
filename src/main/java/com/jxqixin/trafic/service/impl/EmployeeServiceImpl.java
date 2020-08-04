package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.EmployeeDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.EmployeeRepository;
import com.jxqixin.trafic.repository.UserRepository;
import com.jxqixin.trafic.service.IEmployeeService;
import com.jxqixin.trafic.util.IdCardUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl extends CommonServiceImpl<Employee> implements IEmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private UserRepository userRepository;
	@Value("${defaultPassword}")
	private String defaultPassword;
	@Override
	public CommonRepository getCommonRepository() {
		return employeeRepository;
	}

	@Override
	public void deleteById(String id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Page findEmployees(NameDto nameDto,Org org) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return employeeRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(org!=null) {
					Join<Employee, Org> orgJoin = root.join("org", JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				if(!StringUtils.isEmpty(nameDto.getName())){
					list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName() +"%"));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void addEmployee(EmployeeDto employeeDto, Org org) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDto,employee);
		employee.setOrg(org);
		//手机号不空，创建用户，手机号作为用户名
		if(!StringUtils.isEmpty(employeeDto.getTel())){
			//该手机号已被其他用户使用
			User user = userRepository.findByUsername(employeeDto.getTel());
			if(user!=null){
				throw new RuntimeException("手机号已被使用!");
			}

			//根据手机号查找人员
			User employee1 = userRepository.findByUsername(employeeDto.getTel());
			if(employee1!=null){
				throw new RuntimeException("手机号已被使用!");
			}

			 user = new User();
			user.setUsername(employee.getTel());
			user.setPassword(new BCryptPasswordEncoder().encode(defaultPassword));
			user.setOrg(org);
			user.setTel(employee.getTel());
			user.setCreateDate(new Date());
			if(!StringUtils.isEmpty(employeeDto.getRoleId())){
				Role role = new Role();
				role.setId(employeeDto.getRoleId());

				user.setRole(role);
			}
			user = (User) userRepository.save(user);
			employee.setUser(user);
		}else{
			throw new RuntimeException("手机号不能为空!");
		}

		if(StringUtils.isEmpty(employeeDto.getIdnum())){
			throw new RuntimeException("身份证号不能为空!");
		}else {
			Employee employee1 = employeeRepository.findByIdnum(employeeDto.getIdnum(),org.getId());
			if(employee1!=null){
				throw new RuntimeException("身份证号已被使用!");
			}
		}

		if(!StringUtils.isEmpty(employeeDto.getDepartmentId())){
			Department department = new Department();
			department.setId(employeeDto.getDepartmentId());

			employee.setDepartment(department);
		}

		if(!StringUtils.isEmpty(employeeDto.getPositionId())){
			Position position = new Position();
			position.setId(employeeDto.getPositionId());

			employee.setPosition(position);
		}
		employee.setSex(IdCardUtil.getSex(employeeDto.getIdnum()));
		employee.setAge(IdCardUtil.getAge(employeeDto.getIdnum()));
		employeeRepository.save(employee);
	}

	@Override
	public void updateEmployee(EmployeeDto employeeDto,Org org) {
		Employee employee = (Employee) employeeRepository.findById(employeeDto.getId()).get();
		if(!StringUtils.isEmpty(employeeDto.getPhoto()) && !employeeDto.getPhoto().equals(employee.getPhoto())){
				File photo = new File(employee.getRealPath());
				photo.delete();
		}

		//手机号不空，创建用户，手机号作为用户名
		if(!StringUtils.isEmpty(employeeDto.getTel())){
			//根据手机号查找人员
			Employee employee1 = employeeRepository.findByTel(employeeDto.getTel());
			if(employee1!=null && !employee1.getId().equals(employee.getId())){
				throw new RuntimeException("手机号已被使用!");
			}

			User user = employee.getUser();
			if(!StringUtils.isEmpty(employeeDto.getRoleId())){
				Role userRole = user.getRole();
				if(userRole==null || !userRole.getId().equals(employeeDto.getRoleId())) {
					Role role = new Role();
					role.setId(employeeDto.getRoleId());

					user.setRole(role);
					user = (User) userRepository.save(user);
				}
			}
		}else{
			throw new RuntimeException("手机号不能为空!");
		}

		if(StringUtils.isEmpty(employeeDto.getIdnum())){
			throw new RuntimeException("身份证号不能为空!");
		}else {
			Employee employee1 = employeeRepository.findByIdnum(employeeDto.getIdnum(),org.getId());
			if(employee1!=null && !employee1.getId().equals(employeeDto.getId())){
				throw new RuntimeException("身份证号已被使用!");
			}
		}
		employee.setName(employeeDto.getName());
		employee.setTel(employeeDto.getTel());
		if(employeeDto.getPhoto()!=null) {
			employee.setPhoto(employeeDto.getPhoto());
		}
		if(!StringUtils.isEmpty(employeeDto.getDepartmentId())){
			Department department  = employee.getDepartment();
			if(department!=null && !department.getId().equals(employeeDto.getDepartmentId().trim())){
				Department newDepartment = new Department();
				newDepartment.setId(employeeDto.getDepartmentId());

				employee.setDepartment(newDepartment);
			}
		}
		if(!StringUtils.isEmpty(employeeDto.getPositionId())){
			Position position  = employee.getPosition();
			if(position!=null && !position.getId().equals(employeeDto.getPositionId().trim())){
				Position newPosition = new Position();
				newPosition.setId(employeeDto.getPositionId());

				employee.setPosition(newPosition);
			}
		}
		employee.setRealPath(employeeDto.getRealPath());
		employee.setIdnum(employeeDto.getIdnum());
		employee.setNote(employeeDto.getNote());
		employee.setSex(IdCardUtil.getSex(employeeDto.getIdnum()));
		employee.setAge(IdCardUtil.getAge(employeeDto.getIdnum()));
		employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(String id) {
		Employee employee = (Employee) employeeRepository.findById(id).get();
		employeeRepository.updateOrg2Null(id);
		String userId = employee.getUser().getId();
		employeeRepository.updateUser2NullByUserId(userId);
		userRepository.deleteById(userId);
	}

	@Override
	public Employee findByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}

	@Override
	public List<Employee> findAllEmployees(Org org) {
		if(org==null){
			return employeeRepository.findAll();
		}
		return employeeRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<Employee,Org> orgJoin = root.join("org");
				return criteriaBuilder.equal(orgJoin.get("id"),org.getId());
			}
		});
	}
}

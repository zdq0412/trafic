package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.EmployeeDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.Role;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.EmployeeRepository;
import com.jxqixin.trafic.repository.UserRepository;
import com.jxqixin.trafic.service.IEmployeeService;
import com.jxqixin.trafic.util.IdCardUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Date;

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
	public Page findEmployees(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return employeeRepository.findAll(new NameSpecification(nameDto),pageable);
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
			Employee employee1 = employeeRepository.findByTel(employeeDto.getTel());
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
			Employee employee1 = employeeRepository.findByIdnum(employeeDto.getIdnum());
			if(employee1!=null){
				throw new RuntimeException("身份证号已被使用!");
			}
		}
		employee.setSex(IdCardUtil.getSex(employeeDto.getIdnum()));
		employee.setAge(IdCardUtil.getAge(employeeDto.getIdnum()));
		employeeRepository.save(employee);
	}

	@Override
	public void updateEmployee(EmployeeDto employeeDto) {
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
			Employee employee1 = employeeRepository.findByIdnum(employeeDto.getIdnum());
			if(employee1!=null && !employee1.getId().equals(employeeDto.getId())){
				throw new RuntimeException("身份证号已被使用!");
			}
		}
		employee.setName(employeeDto.getName());
		employee.setTel(employeeDto.getTel());
		if(employeeDto.getPhoto()!=null) {
			employee.setPhoto(employeeDto.getPhoto());
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
		employeeRepository.updateOrg2Null(id);
	}
}

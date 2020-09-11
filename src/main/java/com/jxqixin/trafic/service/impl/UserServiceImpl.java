package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.EmployeeRepository;
import com.jxqixin.trafic.repository.UserRepository;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl /*extends CommonServiceImpl<User>*/ implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	/*@Override
	public CommonRepository getCommonRepository() {
		return userRepository;
	}*/
	@Override
	public User login(String username, String password) {
		return userRepository.findByUsernameAndPassword(username,password);
	}
	@Override
	@Cacheable(unless = "#result eq null",key = "#root.methodName + '-' + #username")
	public User queryUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Cacheable(unless = "#result.size()==0",key = "#root.methodName + '-' + #username")
	public List<Object[]> queryFunctionsByUsername(String username) {
		return userRepository.queryFunctionsByUsername(username);
	}
	/**
	 * 批量删除用户
	 * @param ids
	 */
	@Override
	@CacheEvict(value = "userCache",allEntries = true)
	public void deleteBatch(String[] ids) {
		if(ids==null || ids.length<=0){
			throw new RuntimeException("没有要删除的用户!");
		}

		for (int i = 0;i<ids.length;i++){
			User user = (User) userRepository.findById(ids[i]).get();
			if(!user.isAllowedDelete()){
				throw new RuntimeException("含有不允许删除的用户:" + user.getUsername());
			}
			userRepository.deleteById(ids[i]);
		}
	}
	/**
	 * 根据id删除
	 * @param id
	 */
	@Override
	@CacheEvict(value = "userCache",allEntries = true)
	public void deleteById(String id) {
		User user = (User) userRepository.findById(id).get();
		if(!user.isAllowedDelete()){
			throw new RuntimeException("该用户不允许删除:" + user.getUsername());
		}
		if(user.getRealpath()!=null){
			FileUtil.deleteFile(user.getRealpath());
		}
		employeeRepository.updateUser2NullByUserId(id);
		userRepository.deleteById(id);
	}
	/**
	 * 根据角色id查找用户数
	 * @param id
	 * @return
	 */
	@Override
	@Cacheable(key = "#id")
	public Integer findCountByRoleId(String id) {
		return userRepository.findCountByRoleId(id);
	}

	@Override
	@Cacheable(unless = "#result eq null",key = "#root.methodName + '-' + #p0.username + '' + #p0.page + '' + #p0.limit + (#p1 eq null?null:#p1.name)")
	public Page findUsers(UserDto userDto,Org org) {
		Pageable pageable = PageRequest.of(userDto.getPage(),userDto.getLimit());
		return userRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(org!=null){
					Join<User,Org> orgJoin = root.join("org");
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				if(!StringUtils.isEmpty(userDto.getUsername())){
					list.add(criteriaBuilder.like(root.get("username"),"%" + userDto.getUsername().trim() + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}

	@Override
	@Cacheable(unless = "#result eq null")
	public User queryUserByUsernameAndOrgId(String username, String orgId) {
		if(!StringUtils.isEmpty(orgId)){
			return userRepository.findByUsernameAndOrgId(username,orgId);
		}else{
			return userRepository.findByUsername(username);
		}
	}
	@Override
	public User queryActiveUserByUsername(String username) {
		return userRepository.findActiveUserByUsername(username);
	}

	@Override
	@Cacheable(unless = "#result eq null",key = "#root.methodName + '-' + #p0")
	public User findByUsernameWhereOrgIsNull(String currentUsername) {
		return userRepository.findByUsernameWhereOrgIsNull(currentUsername);
	}

	@Override
	@CacheEvict(value = "userCache",allEntries = true)
	public void updateObj(User obj) {
		userRepository.save(obj);
	}

	@Override
	@CacheEvict(value = "userCache",allEntries = true)
	public User addObj(User obj) {
		return (User) userRepository.save(obj);
	}

	@Override
	@Cacheable(unless = "#result eq null",key = "#root.methodName + #p0")
	public User queryObjById(Serializable id) {
		return (User) userRepository.findById(id).get();
	}

	@Override
	@CacheEvict(value = {"userCache","functionCache"},allEntries = true)
	public void deleteObj(Serializable id) {
		userRepository.deleteById(id);
	}

	@Override
	@Cacheable
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	@Cacheable
	public List<User> findAll() {
		return userRepository.findAll();
	}
}

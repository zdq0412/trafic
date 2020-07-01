package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OrgRepository;
import com.jxqixin.trafic.repository.UserRepository;
import com.jxqixin.trafic.service.IUserService;
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

@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl<User> implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrgRepository orgRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return userRepository;
	}
	@Override
	public User login(String username, String password) {
		return userRepository.findByUsernameAndPassword(username,password);
	}
	@Override
	public User queryUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<Object[]> queryFunctionsByUsername(String username) {
		return userRepository.queryFunctionsByUsername(username);
	}
	/**
	 * 分页查询用户信息
	 * @param userDto
	 * @return
	 */
	@Override
	public Page<User> findByPage(UserDto userDto) {
		Pageable pageable = PageRequest.of(userDto.getPage(),userDto.getLimit(), Sort.Direction.DESC,"createDate");
		return userRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				String username = userDto.getUsername();
				String realName = userDto.getRealName();
				String tel = userDto.getTel();
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(username)){
					list.add(criteriaBuilder.like(root.get("username"),"%" + username + "%"));
				}
				if(!StringUtils.isEmpty(realName)){
					list.add(criteriaBuilder.like(root.get("realName"),"%" + realName + "%"));
				}
				if(!StringUtils.isEmpty(tel)){
					list.add(criteriaBuilder.like(root.get("tel"),"%" + tel + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}
	/**
	 * 批量删除用户
	 * @param ids
	 */
	@Override
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
	public void deleteById(String id) {
		User user = (User) userRepository.findById(id).get();
		if(!user.isAllowedDelete()){
			throw new RuntimeException("该用户不允许删除:" + user.getUsername());
		}
		userRepository.deleteById(id);
	}
	@Override
	public Page<User> findByPageWithoutAdmin(UserDto userDto) {
		Pageable pageable = PageRequest.of(userDto.getPage(),userDto.getLimit());
		return userRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.notEqual(root.get("username"),"admin");
			}
		}, pageable);
	}
	/**
	 * 根据角色id查找用户数
	 * @param id
	 * @return
	 */
	@Override
	public Integer findCountByRoleId(String id) {
		return userRepository.findCountByRoleId(id);
	}

	@Override
	public Page findUsers(NameDto nameDto,String currentUsername) {
		User user = userRepository.findByUsername(currentUsername);
		Org org = user.getOrg();
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return userRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(org!=null){
					Join<User,Org> orgJoin = root.join("org");
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				if(!StringUtils.isEmpty(nameDto.getName())){
					list.add(criteriaBuilder.like(root.get("username"),"%" + nameDto.getName().trim() + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
}

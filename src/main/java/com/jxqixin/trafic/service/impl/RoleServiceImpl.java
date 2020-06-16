package com.jxqixin.trafic.service.impl;

import com.twostep.resume.dto.RoleDto;
import com.twostep.resume.model.Power;
import com.twostep.resume.model.Role;
import com.twostep.resume.model.RolePower;
import com.twostep.resume.model.User;
import com.twostep.resume.repository.CommonRepository;
import com.twostep.resume.repository.RoleRepository;
import com.twostep.resume.repository.UserRepository;
import com.twostep.resume.service.IRolePowerService;
import com.twostep.resume.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends CommonServiceImpl<Role> implements IRoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IRolePowerService rolePowerService;
	@Autowired
	private IRoleService roleService;
	@Override
	public CommonRepository getCommonRepository() {
		return roleRepository;
	}
	/**
	 * 根据条件查找角色信息
	 * @param roleDto
	 * @return
	 */
	@Override
	public Page<Role> findByPage(RoleDto roleDto) {
		Pageable pageable = PageRequest.of(roleDto.getPage(),roleDto.getLimit(), Sort.Direction.DESC,"createDate");
		return roleRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				String roleName = roleDto.getRoleName();
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(roleName)){
					list.add(criteriaBuilder.like(root.get("roleName"),"%" + roleName +"%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}

	/**
	 * 批量删除角色
	 * @param ids
	 */
	@Override
	public void deleteBatch(String[] ids) {
		if(ids==null || ids.length<=0){
			throw new RuntimeException("没有要删除的简历!");
		}

		for (int i = 0;i<ids.length;i++){
			deleteById(ids[i]);
		}
	}

	/**
	 * 根据id删除
	 * @param roleName
	 */
	@Override
	public void deleteById(String roleName) {
		Role role = (Role) roleRepository.findById(roleName).get();
		if(!role.getAllowDelete()){
			throw new RuntimeException("该角色不允许删除:" + role.getRoleName());
		}

		List<User> list = userRepository.queryByRoleId(roleName);
		if(!CollectionUtils.isEmpty(list)){
			throw new RuntimeException("该角色下存在用户，不允许删除:" + role.getRoleName());
		}

		roleRepository.deleteById(roleName);
	}
	/***
	 * 根据角色名称查找角色
	 * @param rolename
	 * @return
	 */
	@Override
	public Role queryRoleByRolename(String rolename) {
		return roleRepository.findByRoleName(rolename);
	}
	/**
	 * 为角色赋权限
	 * @param roleName
	 * @param powerUrls
	 */
	@Override
	public void asignPowers(String roleName, String[] powerUrls) {
		Role role = roleService.queryRoleByRolename(roleName);
		rolePowerService.deleteByRoleId(role.getId());
		for(int i = 0;i<powerUrls.length;i++){
			rolePowerService.insert(role.getId(),powerUrls[i]);
		}
	}
}

package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.RoleDto;
import com.jxqixin.trafic.model.Role;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.RoleRepository;
import com.jxqixin.trafic.repository.UserRepository;
import com.jxqixin.trafic.service.IRoleFunctionsService;
import com.jxqixin.trafic.service.IRoleService;
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
	private IRoleFunctionsService roleFunctionsService;
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
		if(!role.isAllowedDelete()){
			throw new RuntimeException("该角色不允许删除:" + role.getName());
		}
		List<User> list = userRepository.queryByRoleId(roleName);
		if(!CollectionUtils.isEmpty(list)){
			throw new RuntimeException("该角色下存在用户，不允许删除:" + role.getName());
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
		return roleRepository.findByName(rolename);
	}
	/**
	 * 为角色赋权限
	 * @param roleName
	 * @param powerUrls
	 */
	@Override
	public void asignPowers(String roleName, String[] powerUrls) {
		Role role = roleService.queryRoleByRolename(roleName);
		roleFunctionsService.deleteByRoleId(role.getId());
		for(int i = 0;i<powerUrls.length;i++){
			roleFunctionsService.insert(role.getId(),powerUrls[i]);
		}
	}
}

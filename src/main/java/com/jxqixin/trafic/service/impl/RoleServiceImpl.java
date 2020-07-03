package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.RoleDto;
import com.jxqixin.trafic.model.Org;
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

import javax.persistence.criteria.*;
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
	@Override
	public CommonRepository getCommonRepository() {
		return roleRepository;
	}

	@Override
	public Role findByNameAndOrgId(String name, String orgId) {
		if(!StringUtils.isEmpty(orgId)){
			return roleRepository.findByNameAndOrgId(name,orgId);
		}else{
			return roleRepository.findByName(name);
		}
	}
	@Override
	public Page findRoles(NameDto nameDto,String orgId) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return roleRepository.findAll(new Specification(){

			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(!StringUtils.isEmpty(nameDto.getName())){
					list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName()+"%"));
				}

				if(!StringUtils.isEmpty(orgId)){
					Join<Role, Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),orgId));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}

	@Override
	public void deleteById(String id) {
		Role role = (Role)roleRepository.findById(id).get();
		if(!role.isAllowedDelete()){
			throw new RuntimeException("删除失败,该角色不允许删除!");
		}

		Integer userCount = userRepository.findCountByRoleId(id);
		if(userCount!=null & userCount>0){
			throw new RuntimeException("删除失败，该角色下存在用户!");
		}

		roleFunctionsService.deleteByRoleId(id);
		roleRepository.deleteById(id);
	}

	@Override
	public List<Role> findAllRoles(String orgId) {
		if(StringUtils.isEmpty(orgId)){
			return roleRepository.findAll();
		}
		return roleRepository.findAllByOrgId(orgId);
	}
}

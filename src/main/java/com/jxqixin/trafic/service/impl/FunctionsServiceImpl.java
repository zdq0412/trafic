package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.Menus;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.*;
import com.jxqixin.trafic.service.IFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FunctionsServiceImpl extends CommonServiceImpl<Functions> implements IFunctionsService{
	@Autowired
	private FunctionsRepository functionsRepository;
	@Autowired
	private DirectoryRepository directoryRepository;
	@Autowired
	private OrgCategoryRepository orgCategoryRepository;
	@Autowired
	private DirectoryFunctionsRepository directoryFunctionsRepository;
	@Autowired
	private RoleFunctionsRepository roleFunctionsRepository;
	@Autowired
	private OrgCategoryFunctionsRepository orgCategoryFunctionsRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return functionsRepository;
	}
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Value("${superRole}")
	private String superRole;
	/**
	 * 根据角色名称查找权限
	 * @param roleName
	 * @return
	 */
	@Override
	public List<Functions> queryByRoleName(String roleName) {
		return functionsRepository.queryByRoleName(roleName);
	}

	@Override
	public List<Menus> findMenus(String username) {
		List<Menus> list = new ArrayList<>();
		//查看当前模式下的目录
		List<Directory> directories = directoryRepository.findCurrentDirectorys();
		if (CollectionUtils.isEmpty(directories)) {
			return list;
		}
		//查询用户所在的企业类别
		OrgCategory orgCategory = orgCategoryRepository.findByUsername(username);
		//当前用户不在任何企业,或企业没有类别
		if (orgCategory == null) {
			//根据目录查找当前用户下的菜单
			directories.forEach(d -> {
				List<Functions> functions = functionsRepository.findByDirIdAndUsername(d.getId(),username);
				list.add(buildMenus(d,functions));
			});
		} else {
			directories.forEach(d -> {
				List<Functions> functions = functionsRepository.findFunctions(d.getId(),username,orgCategory.getId());
				list.add(buildMenus(d,functions));
			});
		}
		return list;
	}
	@Override
	public List<Functions> findFunctions(String currentUsername) {
		Role role = userRepository.findByUsername(currentUsername).getRole();
		List<Functions> list = null;
		if(role!=null){
			if(superRole.equals(role.getName())){
				list = functionsRepository.findAllFunctions();
			}else{
				list = functionsRepository.findByUsername(currentUsername);
			}
		}
		if(!CollectionUtils.isEmpty(list)) {
			list.forEach(functions -> findChildren(functions, currentUsername));
			return list;
		}else{
			return new ArrayList<>();
		}
	}
	@Override
	public List<String> findIdsByRoleId(String roleId) {
		return roleFunctionsRepository.findFunctionsIdsByRoleId(roleId);
	}

	@Override
	public void assign2Role(String[] functionIdArray, String roleId) {
		//根据角色ID删除角色和权限的关联
		roleFunctionsRepository.deleteByRoleId(roleId);

		if(functionIdArray!=null && functionIdArray.length>0) {
			//添加新关联
			List<RoleFunctions> list = new ArrayList<>();
			Role role = new Role();
			role.setId(roleId);
			for (int i = 0; i < functionIdArray.length; i++) {
				Functions functions = new Functions();
				functions.setId(functionIdArray[i]);

				RoleFunctions roleFunctions = new RoleFunctions();
				roleFunctions.setRole(role);
				roleFunctions.setFunctions(functions);

				list.add(roleFunctions);
			}

			roleFunctionsRepository.saveAll(list);
		}
	}

	@Override
	public List<String> findIdsByDirectoryId(String dirId) {
		return directoryFunctionsRepository.findIdsByDirectoryId(dirId);
	}

	@Override
	public List<Functions> findAllMenus() {
		return functionsRepository.findAllMenus();
	}

	@Override
	public List<Functions> findAllFunctions() {
		List<Functions> menus = findAllMenus();
		menus.forEach(functions -> findChildren(functions));
		return menus;
	}

	@Override
	public List<String> findIdsByOrgCategoryId(String orgCategoryId) {
		return orgCategoryFunctionsRepository.findFunctionIdsByOrgCategoryId(orgCategoryId);
	}

	@Override
	public void assign2OrgCategory(String[] functionIdArray, String orgCategoryId) {
		orgCategoryFunctionsRepository.deleteByOrgCategoryId(orgCategoryId);
		if(functionIdArray!=null && functionIdArray.length>0) {
			List<OrgCategoryFunctions> list = new ArrayList<>();
			OrgCategory orgCategory = new OrgCategory();
			orgCategory.setId(orgCategoryId);
			for (int i = 0; i < functionIdArray.length; i++) {
				Functions functions = new Functions();
				functions.setId(functionIdArray[i]);
				OrgCategoryFunctions orgCategoryFunctions = new OrgCategoryFunctions();
				orgCategoryFunctions.setOrgCategory(orgCategory);
				orgCategoryFunctions.setFunctions(functions);
				list.add(orgCategoryFunctions);
			}
			orgCategoryFunctionsRepository.saveAll(list);
		}
		//修改企业类别权限
		addOrFindOrgCategoryRole(functionIdArray,orgCategoryId);
	}

	@Override
	public List<Functions> findAdminRoleFunctions() {
		return functionsRepository.findAdminRoleFunctions();
	}

	/**
	 * 新增或查找类别管理员
	 * @param functionIdArray 权限ID数组
	 * @param orgCategoryId 企业类别ID
	 */
	private void addOrFindOrgCategoryRole(String[] functionIdArray, String orgCategoryId){
		OrgCategory orgCategory = (OrgCategory) orgCategoryRepository.findById(orgCategoryId).get();
		List<Role> roles = roleRepository.findByOrgCategoryId(orgCategoryId);
		Role role = null;
		if(CollectionUtils.isEmpty(roles)){
			role = new Role();
			role.setId(UUID.randomUUID().toString());
			role.setName(orgCategory.getName() + "管理员角色");
			role.setOrgCategory(orgCategory);
			role.setCreateDate(new Date());
			role.setStatus("0");
			role= (Role) roleRepository.save(role);
			modifyOrgCategoryRole(functionIdArray,role);
		}else{
			roles.forEach(r -> {
				modifyOrgCategoryRole(functionIdArray,r);
			});
		}
	}
	/**
	 * 修改类别管理员权限
	 * @param functionIdArray
	 * @param role
	 */
	private void modifyOrgCategoryRole(String[] functionIdArray, Role role) {
		roleFunctionsRepository.deleteByRoleId(role.getId());

		List<RoleFunctions> list = new ArrayList<>();
		if(functionIdArray!=null && functionIdArray.length>0){
			for(int i = 0;i<functionIdArray.length;i++){
				Functions functions = new Functions();
				functions.setId(functionIdArray[i]);

				RoleFunctions roleFunctions = new RoleFunctions();
				roleFunctions.setFunctions(functions);
				roleFunctions.setRole(role);

				list.add(roleFunctions);
			}

			roleFunctionsRepository.saveAll(list);
		}
	}
	/**
	 * 根据父功能和当前登录用户名称查找子功能
	 * @param parent 父功能
	 * @param currentUsername 当前登录用户名
	 */
	private void findChildren(Functions parent,String currentUsername){
		List<Functions> children = functionsRepository.findByParentIdAndUsername(parent.getId(),currentUsername);
		if(!CollectionUtils.isEmpty(children)){
			parent.setChildren(children);
			children.forEach(child ->{
				findChildren(child,currentUsername);
			});
		}else{
			return ;
		}
	}
	/**
	 * 根据父功能和当前登录用户名称查找子功能
	 * @param parent 父功能
	 */
	private void findChildren(Functions parent){
		List<Functions> children = functionsRepository.findByParentId(parent.getId());
		if(!CollectionUtils.isEmpty(children)){
			parent.setChildren(children);
			children.forEach(child ->{
				findChildren(child);
			});
		}else{
			return ;
		}
	}

	@Override
	public Page findMenusByPage(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		//目录名称或权限名称
		String name = nameDto.getName();
		//查询所有目录
		Page<Directory> dirPage = directoryRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(nameDto.getName())){
					list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName().trim() +"%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);

		if(dirPage.getTotalElements()>0){
			List<Directory> directories = dirPage.getContent();
			directories.forEach(directory ->{
				List<Functions> functionList = functionsRepository.findByDirId(directory.getId());
				directory.setChildren(functionList);
			});
		}
		return dirPage;
	}

	@Override
	public Functions findByName(String name) {
		return functionsRepository.findByName(name);
	}

	@Override
	public void addFunction(Functions functions) {
		Functions parent = functions.getParent();
		functions.setParent(null);
		functions = this.addObj(functions);
		//父菜单为目录
		if(StringUtils.isEmpty(parent.getType())){
			Directory d = new Directory();
			d.setId(parent.getId());

			DirectoryFunctions directoryFunctions = new DirectoryFunctions();
			directoryFunctions.setDirectory(d);
			directoryFunctions.setFunctions(functions);

			directoryFunctionsRepository.save(directoryFunctions);
		}
		//父菜单为菜单
		if("1".equals(parent.getType())){
			functions.setParent(parent);

			functionsRepository.save(functions);
		}
	}

	@Override
	public void deleteById(String id) {
		directoryFunctionsRepository.deleteByFunctionId(id);
		functionsRepository.deleteByParentId(id);
		functionsRepository.deleteById(id);
	}

	@Override
	public Page findFunctionsByPage(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return functionsRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<Functions,Functions> parentJoin = root.join("parent");
				return criteriaBuilder.and(criteriaBuilder.equal(parentJoin.get("id"),nameDto.getName()),criteriaBuilder.equal(root.get("type"),"0"));
			}
		}, pageable);
	}
	/**
	 * 构建页面显示菜单数据
	 */
	private Menus buildMenus(Directory d,List<Functions> functions){
		Menus parent = new Menus();
		parent.setIcon(d.getIcon());
		parent.setIndex(d.getIndex());
		parent.setTitle(d.getName());

		if(!CollectionUtils.isEmpty(functions)){
			List<Menus> subs = new ArrayList<>();
			functions.forEach(f ->{
				Menus subMenu = new Menus();
				subMenu.setTitle(f.getName());
				subMenu.setIndex(f.getIndex());
				subMenu.setIcon(f.getIcon());
				subs.add(subMenu);
			});
			parent.setSubs(subs);
		}
		return parent;
	}
}

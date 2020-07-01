package com.jxqixin.trafic.service.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.jxqixin.trafic.dto.Menus;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.DirectoryFunctions;
import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.repository.*;
import com.jxqixin.trafic.service.IFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
	private UserRepository userRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return functionsRepository;
	}
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

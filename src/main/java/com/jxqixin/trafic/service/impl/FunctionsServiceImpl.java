package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.Menus;
import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.repository.*;
import com.jxqixin.trafic.service.IFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
	private UserRepository userRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return functionsRepository;
	}

	/**
	 * 查找最顶层权限
	 * @return
	 */
	@Override
	public List<Functions> queryTopFunctions() {
		return functionsRepository.queryTopFunctions();
	}

	/**
	 * 根据父id查找子权限
	 * @param id
	 * @return
	 */
	@Override
	public List<Functions> findByParentId(String id) {
		return functionsRepository.findByParentId(id);
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

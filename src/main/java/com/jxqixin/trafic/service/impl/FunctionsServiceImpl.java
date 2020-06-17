package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.FunctionsRepository;
import com.jxqixin.trafic.service.IFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class FunctionsServiceImpl extends CommonServiceImpl<Functions> implements IFunctionsService{
	@Autowired
	private FunctionsRepository functionsRepository;
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
}

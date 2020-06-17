package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.model.RoleFunctions;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.RoleFunctionsRepository;
import com.jxqixin.trafic.service.IRoleFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class RoleFunctionsServiceImpl extends CommonServiceImpl<RoleFunctions> implements IRoleFunctionsService {
	@Autowired
	private RoleFunctionsRepository roleFunctionsRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return roleFunctionsRepository;
	}

	/**
	 * 根据角色id和权限url查找关联对象
	 *
	 * @param id
	 * @param powerUrl
	 * @return
	 */
	@Override
	public RoleFunctions findByRoleIdAndFunctionsUrl(String id, String powerUrl) {
		return roleFunctionsRepository.findByRoleIdAndFunctionsUrl(id,powerUrl);
	}

	@Override
	public void insert(String id, String powerUrl) {
		roleFunctionsRepository.insert(id,powerUrl);
	}

	@Override
	public void deleteByRoleId(String roleId) {
		roleFunctionsRepository.deleteByRoleId(roleId);
	}
}

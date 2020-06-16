package com.jxqixin.trafic.service.impl;
import com.twostep.resume.model.RolePower;
import com.twostep.resume.repository.CommonRepository;
import com.twostep.resume.repository.RolePowerRepository;
import com.twostep.resume.service.IRolePowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class RolePowerServiceImpl extends CommonServiceImpl<RolePower> implements IRolePowerService {
	@Autowired
	private RolePowerRepository rolePowerRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return rolePowerRepository;
	}

	/**
	 * 根据角色id和权限url查找关联对象
	 *
	 * @param id
	 * @param powerUrl
	 * @return
	 */
	@Override
	public RolePower findByRoleIdAndPowerUrl(String id, String powerUrl) {
		return rolePowerRepository.findByRoleIdAndPowerUrl(id,powerUrl);
	}

	@Override
	public void insert(String id, String powerUrl) {
		rolePowerRepository.insert(id,powerUrl);
	}

	@Override
	public void deleteByRoleId(String roleId) {
		rolePowerRepository.deleteByRoleId(roleId);
	}
}

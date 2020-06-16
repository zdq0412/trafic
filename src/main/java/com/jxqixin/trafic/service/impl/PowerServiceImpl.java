package com.jxqixin.trafic.service.impl;

import com.twostep.resume.model.Power;
import com.twostep.resume.repository.CommonRepository;
import com.twostep.resume.repository.PowerRepository;
import com.twostep.resume.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class PowerServiceImpl extends CommonServiceImpl<Power> implements IPowerService {
	@Autowired
	private PowerRepository powerRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return powerRepository;
	}

	/**
	 * 查找最顶层权限
	 * @return
	 */
	@Override
	public List<Power> queryTopPower() {
		return powerRepository.queryTopPower();
	}

	/**
	 * 根据父url查找子权限
	 * @param url
	 * @return
	 */
	@Override
	public List<Power> findByParentUrl(String url) {
		return powerRepository.findByParentUrl(url);
	}

	/**
	 * 根据角色名称查找权限
	 * @param roleName
	 * @return
	 */
	@Override
	public List<Power> queryByRoleName(String roleName) {
		return powerRepository.queryByRoleName(roleName);
	}
}

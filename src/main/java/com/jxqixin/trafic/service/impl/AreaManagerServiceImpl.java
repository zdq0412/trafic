package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.AreaManager;
import com.jxqixin.trafic.repository.AreaManagerRepository;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.service.IAreaManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Service("areaManagerService")
@Transactional
public class AreaManagerServiceImpl extends CommonServiceImpl<AreaManager> implements IAreaManagerService {
	@Autowired
	private AreaManagerRepository areaManagerRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return areaManagerRepository;
	}
	@Override
	public AreaManager login(String AreaManagername, String password) {
		return areaManagerRepository.findByUsernameAndPassword(AreaManagername,password);
	}
	@Override
	public AreaManager queryAreaManagerByUsername(String username) {
		return areaManagerRepository.findByUsername(username);
	}

	@Override
	public Page<AreaManager> findByPage(UserDto userDto) {
		return null;
	}

	/**
	 * 批量删除用户
	 * @param ids
	 */
	@Override
	public void deleteBatch(String[] ids) {
		if(ids==null || ids.length<=0){
			throw new RuntimeException("没有要删除的用户!");
		}
		for (int i = 0;i<ids.length;i++){
			AreaManager AreaManager = (AreaManager) areaManagerRepository.findById(ids[i]).get();
			areaManagerRepository.deleteById(ids[i]);
		}
	}
	/**
	 * 根据id删除
	 * @param id
	 */
	@Override
	public void deleteById(String id) {
		AreaManager AreaManager = (AreaManager) areaManagerRepository.findById(id).get();
		areaManagerRepository.deleteById(id);
	}
}

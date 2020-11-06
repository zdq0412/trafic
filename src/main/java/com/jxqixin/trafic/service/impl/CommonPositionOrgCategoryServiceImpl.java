package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.model.CommonPositionOrgCategory;
import com.jxqixin.trafic.repository.CommonPositionOrgCategoryRepository;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.service.ICommonPositionOrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Service
@Transactional
public class CommonPositionOrgCategoryServiceImpl extends CommonServiceImpl<CommonPositionOrgCategory> implements ICommonPositionOrgCategoryService {
	@Autowired
	private CommonPositionOrgCategoryRepository commonPositionOrgCategoryRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return commonPositionOrgCategoryRepository;
	}
}

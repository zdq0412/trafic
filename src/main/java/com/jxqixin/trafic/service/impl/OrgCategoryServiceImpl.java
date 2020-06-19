package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OrgCategoryRepository;
import com.jxqixin.trafic.service.IOrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class OrgCategoryServiceImpl extends CommonServiceImpl<OrgCategory> implements IOrgCategoryService {
	@Autowired
	private OrgCategoryRepository orgCategoryRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return orgCategoryRepository;
	}
}

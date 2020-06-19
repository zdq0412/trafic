package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OrgRepository;
import com.jxqixin.trafic.service.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrgServiceImpl extends CommonServiceImpl<Org> implements IOrgService {
	@Autowired
	private OrgRepository orgRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return orgRepository;
	}
}

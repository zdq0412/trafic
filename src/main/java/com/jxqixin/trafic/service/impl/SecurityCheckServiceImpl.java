package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SecurityCheckRepository;
import com.jxqixin.trafic.repository.SecurityCheckTemplateRepository;
import com.jxqixin.trafic.service.ISecurityCheckService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class SecurityCheckServiceImpl extends CommonServiceImpl<SecurityCheck> implements ISecurityCheckService {
	@Autowired
	private SecurityCheckRepository templateRepository;
	@Autowired
	private SecurityCheckTemplateRepository securityCheckTemplateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findSecurityChecks(NameDto nameDto,String type) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(pageable);
	}

	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}

	@Override
	public void importTemplate(String templateId, Org org, String currentUsername) {
		SecurityCheckTemplate template = (SecurityCheckTemplate) securityCheckTemplateRepository.findById(templateId).get();
		SecurityCheck securityCheck = new SecurityCheck();
		BeanUtils.copyProperties(template,securityCheck);
		if(org!=null){
			securityCheck.setOrg(org);
		}
		securityCheck.setUrl(null);
		securityCheck.setRealPath(null);
		securityCheck.setCreateDate(new Date());
		securityCheck.setCreator(currentUsername);
		templateRepository.save(securityCheck);
	}
}

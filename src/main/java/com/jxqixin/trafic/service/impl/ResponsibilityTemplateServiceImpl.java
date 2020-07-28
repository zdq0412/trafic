package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.ResponsibilityTemplate;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.ResponsibilityTemplateRepository;
import com.jxqixin.trafic.service.IResponsibilityTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ResponsibilityTemplateServiceImpl extends CommonServiceImpl<ResponsibilityTemplate> implements IResponsibilityTemplateService {
	@Autowired
	private ResponsibilityTemplateRepository templateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findResponsibilityTemplates(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(pageable);
	}

	@Override
	public ResponsibilityTemplate findByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}
}
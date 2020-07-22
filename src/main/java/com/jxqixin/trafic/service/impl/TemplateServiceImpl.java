package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Template;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryFunctionsRepository;
import com.jxqixin.trafic.repository.DirectoryRepository;
import com.jxqixin.trafic.repository.TemplateRepository;
import com.jxqixin.trafic.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TemplateServiceImpl extends CommonServiceImpl<Template> implements ITemplateService {
	@Autowired
	private TemplateRepository templateRepository;
	@Autowired
	private DirectoryRepository directoryRepository;
	@Autowired
	private DirectoryFunctionsRepository directoryFunctionsRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findTemplates(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return templateRepository.findAll(pageable);
	}

	@Override
	public Template findByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}
}

package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.RuleTemplate;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryFunctionsRepository;
import com.jxqixin.trafic.repository.DirectoryRepository;
import com.jxqixin.trafic.repository.RuleTemplateRepository;
import com.jxqixin.trafic.service.IRuleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RuleTemplateServiceImpl extends CommonServiceImpl<RuleTemplate> implements IRuleTemplateService {
	@Autowired
	private RuleTemplateRepository templateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findRuleTemplates(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(pageable);
	}

	@Override
	public RuleTemplate findByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}
}

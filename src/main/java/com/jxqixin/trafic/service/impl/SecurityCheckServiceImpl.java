package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.SecurityCheck;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SecurityCheckRepository;
import com.jxqixin.trafic.service.ISecurityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SecurityCheckServiceImpl extends CommonServiceImpl<SecurityCheck> implements ISecurityCheckService {
	@Autowired
	private SecurityCheckRepository templateRepository;
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
}

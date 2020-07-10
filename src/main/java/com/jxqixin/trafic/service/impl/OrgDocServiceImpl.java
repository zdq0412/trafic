package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgDoc;
import com.jxqixin.trafic.model.OrgImg;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OrgDocRepository;
import com.jxqixin.trafic.service.IOrgDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrgDocServiceImpl extends CommonServiceImpl<OrgDoc> implements IOrgDocService {
	@Autowired
	private OrgDocRepository orgDocRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return orgDocRepository;
	}

	@Override
	public void deleteById(String id) {
		orgDocRepository.deleteById(id);
	}

	@Override
	public Page findOrgDocs(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return orgDocRepository.findAll(new NameSpecification(nameDto),pageable);
	}

	@Override
	public List<OrgDoc> findAll(Org org) {
		if(org == null){
			return new ArrayList<>();
		}
		return orgDocRepository.findByOrgId(org.getId());
	}
}

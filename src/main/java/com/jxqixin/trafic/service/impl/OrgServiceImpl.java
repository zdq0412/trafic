package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OrgRepository;
import com.jxqixin.trafic.service.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrgServiceImpl extends CommonServiceImpl<Org> implements IOrgService {
	@Autowired
	private OrgRepository orgRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return orgRepository;
	}

	@Override
	public Page findOrgs(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return orgRepository.findAll(new NameSpecification(nameDto), pageable);
	}

	@Override
	public Org findByName(String name) {
		return orgRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		orgRepository.deleteById(id);
	}
	@Override
	public Org findByCode(String code) {
		return orgRepository.findByCode(code);
	}
}

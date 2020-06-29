package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryFunctionsRepository;
import com.jxqixin.trafic.repository.DirectoryRepository;
import com.jxqixin.trafic.repository.SchemaRepository;
import com.jxqixin.trafic.service.ISchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
@Service
@Transactional
public class SchemaServiceImpl extends CommonServiceImpl<Schema> implements ISchemaService {
	@Autowired
	private SchemaRepository schemaRepository;
	@Autowired
	private DirectoryRepository directoryRepository;
	@Autowired
	private DirectoryFunctionsRepository directoryFunctionsRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return schemaRepository;
	}
	@Override
	public Page findSchemas(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return schemaRepository.findAll(pageable);
	}

	@Override
	public Schema findByName(String name) {
		return schemaRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		directoryRepository.deleteSchemaBySchemaId(id);
		schemaRepository.deleteById(id);
	}
}

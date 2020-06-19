package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.SchemaRepository;
import com.jxqixin.trafic.service.ISchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Service
@Transactional
public class SchemaServiceImpl extends CommonServiceImpl<Schema> implements ISchemaService {
	@Autowired
	private SchemaRepository schemaRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return schemaRepository;
	}
}

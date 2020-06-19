package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.DirectoryFunctions;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryFunctionsRepository;
import com.jxqixin.trafic.service.IDirectoryFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DirectoryFunctionsServiceImpl extends CommonServiceImpl<DirectoryFunctions> implements IDirectoryFunctionsService {
	@Autowired
	private DirectoryFunctionsRepository directoryFunctionsRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return directoryFunctionsRepository;
	}
}

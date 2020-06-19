package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.mapper.DirectoryMapper;
import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryRepository;
import com.jxqixin.trafic.service.IDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DirectoryServiceImpl extends CommonServiceImpl<Directory> implements IDirectoryService {
	@Autowired
	private DirectoryRepository directoryRepository;
	@Resource
	private DirectoryMapper directoryMapper;
	@Override
	public CommonRepository getCommonRepository() {
		return directoryRepository;
	}

	@Override
	public List<Directory> findDirectories() {
		return directoryMapper.findDirectoies();
	}
}

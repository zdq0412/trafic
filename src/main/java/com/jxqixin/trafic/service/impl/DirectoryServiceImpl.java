package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.mapper.DirectoryMapper;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryFunctionsRepository;
import com.jxqixin.trafic.repository.DirectoryRepository;
import com.jxqixin.trafic.service.IDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DirectoryServiceImpl extends CommonServiceImpl<Directory> implements IDirectoryService {
	@Autowired
	private DirectoryRepository directoryRepository;
	@Resource
	private DirectoryMapper directoryMapper;
	@Autowired
	private DirectoryFunctionsRepository directoryFunctionsRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return directoryRepository;
	}

	@Override
	public List<Directory> findDirectories() {
		return directoryMapper.findDirectoies();
	}
	@Override
	public Page findDirectorys(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.ASC,"priority");
		return directoryRepository.findAll(new NameSpecification(nameDto),pageable);
	}

	@Override
	public Directory findByName(String name,String schemaId) {
		return directoryRepository.findByName(name,schemaId);
	}

	@Override
	public void deleteById(String id) {
		directoryFunctionsRepository.deleteByDirectoryId(id);
		directoryRepository.deleteById(id);
	}

	@Override
	public void assign2Directory(String[] functionIdArray, String dirId) {
		//根据角色ID删除角色和权限的关联
		directoryFunctionsRepository.deleteByDirectoryId(dirId);

		if(functionIdArray!=null && functionIdArray.length>0) {
			//添加新关联
			List<DirectoryFunctions> list = new ArrayList<>();
			Directory directory = new Directory();
			directory.setId(dirId);
			for (int i = 0; i < functionIdArray.length; i++) {
				Functions functions = new Functions();
				functions.setId(functionIdArray[i]);

				DirectoryFunctions directoryFunctions = new DirectoryFunctions();
				directoryFunctions.setFunctions(functions);
				directoryFunctions.setDirectory(directory);

				list.add(directoryFunctions);
			}

			directoryFunctionsRepository.saveAll(list);
		}
	}

}

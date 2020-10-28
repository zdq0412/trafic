package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.DeviceArchiveDto;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.DeviceArchive;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DeviceArchiveRepository;
import com.jxqixin.trafic.service.IDeviceArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class DeviceArchiveServiceImpl extends CommonServiceImpl<DeviceArchive> implements IDeviceArchiveService {
	@Autowired
	private DeviceArchiveRepository deviceArchiveRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return deviceArchiveRepository;
	}
	@Override
	public Page findDeviceArchives(DeviceArchiveDto deviceArchiveDto) {
		Pageable pageable = PageRequest.of(deviceArchiveDto.getPage(),deviceArchiveDto.getLimit(), Sort.Direction.DESC,"createDate");
		return deviceArchiveRepository.findAll(( root,  criteriaQuery,  criteriaBuilder)-> {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(deviceArchiveDto.getDeviceId())){
					Join<Device, DeviceArchive> employeeJoin = root.join("device",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),deviceArchiveDto.getDeviceId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		deviceArchiveRepository.deleteById(id);
	}
}

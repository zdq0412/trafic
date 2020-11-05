package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.constant.DeviceArchiveType;
import com.jxqixin.trafic.constant.EmpArchiveType;
import com.jxqixin.trafic.dto.DeviceArchiveDto;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.DeviceArchive;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DeviceArchiveRepository;
import com.jxqixin.trafic.service.IDeviceArchiveService;
import com.jxqixin.trafic.service.IDeviceService;
import com.jxqixin.trafic.util.StringUtil;
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
	@Autowired
	private IDeviceService deviceService;
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
		DeviceArchive deviceArchive = (DeviceArchive) deviceArchiveRepository.findById(id).get();
		deviceArchive.setDeleted(true);
		deviceArchiveRepository.save(deviceArchive);

		setArchiveCode(deviceArchive);
	}

	@Override
	public void addDeviceArchive(DeviceArchive deviceArchive) {
		addObj(deviceArchive);

		setArchiveCode(deviceArchive);
	}

	/**
	 * 设置档案码
	 * @param deviceArchive
	 */
	private void setArchiveCode(DeviceArchive deviceArchive){
		Long count = deviceArchiveRepository.count((root, criteriaQuery, criteriaBuilder) -> {
			Join<DeviceArchive, Device> deviceJoin = root.join("device",JoinType.INNER);
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("deleted"),false),criteriaBuilder.equal(deviceJoin.get("id"),deviceArchive.getDevice().getId()));
		});
		if(count == null)count = 0l;
		Device device = deviceService.queryObjById(deviceArchive.getDevice().getId());
		device.setArchiveCode(StringUtil.handleDeviceArchiveCode(DeviceArchiveType.ARCHIVE,device.getArchiveCode(),count.intValue()));
		deviceService.updateObj(device);
	}
}

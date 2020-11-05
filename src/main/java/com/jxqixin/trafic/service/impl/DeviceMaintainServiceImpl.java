package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.constant.DeviceArchiveType;
import com.jxqixin.trafic.dto.DeviceMaintainDto;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.DeviceArchive;
import com.jxqixin.trafic.model.DeviceMaintain;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DeviceMaintainRepository;
import com.jxqixin.trafic.service.IDeviceMaintainService;
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
public class DeviceMaintainServiceImpl extends CommonServiceImpl<DeviceMaintain> implements IDeviceMaintainService {
	@Autowired
	private DeviceMaintainRepository deviceMaintainRepository;
	@Autowired
	private IDeviceService deviceService;
	@Override
	public CommonRepository getCommonRepository() {
		return deviceMaintainRepository;
	}
	@Override
	public Page findDeviceMaintains(DeviceMaintainDto deviceMaintainDto) {
		Pageable pageable = PageRequest.of(deviceMaintainDto.getPage(),deviceMaintainDto.getLimit(), Sort.Direction.DESC,"createDate");
		return deviceMaintainRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(deviceMaintainDto.getDeviceId())){
					Join<Device, DeviceMaintain> employeeJoin = root.join("device",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),deviceMaintainDto.getDeviceId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}


	@Override
	public void deleteById(String id) {
		DeviceMaintain deviceMaintain = (DeviceMaintain) deviceMaintainRepository.findById(id).get();
		deviceMaintain.setDeleted(true);
		deviceMaintainRepository.save(deviceMaintain);
		setArchiveCode(deviceMaintain);
	}
	@Override
	public void addDeviceMaintain(DeviceMaintain deviceMaintain) {
		addObj(deviceMaintain);
		setArchiveCode(deviceMaintain);
	}
	/**
	 * 设置档案码
	 * @param deviceMaintain
	 */
	private void setArchiveCode(DeviceMaintain deviceMaintain){
		Long count = deviceMaintainRepository.count((root, criteriaQuery, criteriaBuilder) -> {
			Join<DeviceMaintain, Device> deviceJoin = root.join("device",JoinType.INNER);
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("deleted"),false),criteriaBuilder.equal(deviceJoin.get("id"),deviceMaintain.getDevice().getId()));
		});
		if(count == null)count = 0l;
		Device device = deviceService.queryObjById(deviceMaintain.getDevice().getId());
		device.setArchiveCode(StringUtil.handleDeviceArchiveCode(DeviceArchiveType.MAINTAIN,device.getArchiveCode(),count.intValue()));
		deviceService.updateObj(device);
	}
}

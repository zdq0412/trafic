package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.DeviceDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryFunctionsRepository;
import com.jxqixin.trafic.repository.DirectoryRepository;
import com.jxqixin.trafic.repository.DeviceRepository;
import com.jxqixin.trafic.service.IDeviceService;
import com.jxqixin.trafic.util.FileUtil;
import org.springframework.beans.BeanUtils;
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
public class DeviceServiceImpl extends CommonServiceImpl<Device> implements IDeviceService {
	@Autowired
	private DeviceRepository deviceRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return deviceRepository;
	}
	@Override
	public Page findDevices(DeviceDto deviceDto, Org org) {
		Pageable pageable = PageRequest.of(deviceDto.getPage(),deviceDto.getLimit(), Sort.Direction.DESC,"createDate");
		return deviceRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<Device,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				if(!StringUtils.isEmpty(deviceDto.getCategory_id())){
					Join<Device, Category> categoryJoin = root.join("category",JoinType.INNER);
					list.add(criteriaBuilder.equal(categoryJoin.get("id"),deviceDto.getCategory_id()));
				}

				if(!StringUtils.isEmpty(deviceDto.getName())){
					list.add(criteriaBuilder.like(root.get("name"),"%" + deviceDto.getName() + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}
	@Override
	public void deleteById(String id) {
		deviceRepository.deleteById(id);
	}

	@Override
	public void deleteDevice(String id) {
		Device device = (Device) deviceRepository.findById(id).get();
		if(!StringUtils.isEmpty(device.getRealPath())){
			FileUtil.deleteFile(device.getRealPath());
		}

		deviceRepository.deleteById(id);
	}
}

package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.DeviceCheckDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.DeviceCheck;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DeviceCheckRepository;
import com.jxqixin.trafic.service.IDeviceCheckService;
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
public class DeviceCheckServiceImpl extends CommonServiceImpl<DeviceCheck> implements IDeviceCheckService {
	@Autowired
	private DeviceCheckRepository deviceCheckRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return deviceCheckRepository;
	}
	@Override
	public Page findDeviceChecks(DeviceCheckDto deviceCheckDto) {
		Pageable pageable = PageRequest.of(deviceCheckDto.getPage(),deviceCheckDto.getLimit(), Sort.Direction.DESC,"createDate");
		return deviceCheckRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(deviceCheckDto.getOrgId())){
					Join<DeviceCheck, Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),deviceCheckDto.getOrgId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		deviceCheckRepository.deleteById(id);
	}
}

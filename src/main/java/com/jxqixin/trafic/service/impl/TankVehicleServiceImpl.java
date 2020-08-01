package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.TankVehicleRepository;
import com.jxqixin.trafic.repository.TankVehicleTemplateRepository;
import com.jxqixin.trafic.service.ITankVehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class TankVehicleServiceImpl extends CommonServiceImpl<TankVehicle> implements ITankVehicleService {
	@Autowired
	private TankVehicleRepository templateRepository;
	@Autowired
	private TankVehicleTemplateRepository tankVehicleTemplateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findTankVehicles(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(pageable);
	}
	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}

	@Override
	public void importTemplate(String templateId, Org org, String currentUsername) {
		TankVehicleTemplate template = (TankVehicleTemplate) tankVehicleTemplateRepository.findById(templateId).get();
		TankVehicle tankVehicle = new TankVehicle();
		BeanUtils.copyProperties(template,tankVehicle);
		if(org!=null){
			tankVehicle.setOrg(org);
		}
		tankVehicle.setUrl(null);
		tankVehicle.setRealPath(null);
		tankVehicle.setCreateDate(new Date());
		tankVehicle.setCreator(currentUsername);
		templateRepository.save(tankVehicle);
	}
}

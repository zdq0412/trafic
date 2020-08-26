package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.TankVehicleDto;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public Page findTankVehicles(TankVehicleDto tankVehicleDto,Org org) {
		Pageable pageable = PageRequest.of(tankVehicleDto.getPage(),tankVehicleDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<TankVehicle,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
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

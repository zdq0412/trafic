package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.HazardSourcesList;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.HazardSourcesListRepository;
import com.jxqixin.trafic.service.IHazardSourcesListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@Service
@Transactional
public class HazardSourcesListServiceImpl extends CommonServiceImpl<HazardSourcesList> implements IHazardSourcesListService {
	@Autowired
	private HazardSourcesListRepository hazardSourcesListRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return hazardSourcesListRepository;
	}

	@Override
	public void deleteById(String id) {
		hazardSourcesListRepository.deleteById(id);
	}

	@Override
	public Page findHazardSourcesLists(NameDto nameDto, Org org) {
		if(org==null){
			return Page.empty();
		}
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return hazardSourcesListRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<HazardSourcesList,Org> orgJoin = root.join("org",JoinType.INNER);
				return criteriaBuilder.equal(orgJoin.get("id"),org.getId());
			}
		}, pageable);
	}
}

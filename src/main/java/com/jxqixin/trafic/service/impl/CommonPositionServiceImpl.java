package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.CommonPositionDto;
import com.jxqixin.trafic.model.CommonPosition;
import com.jxqixin.trafic.model.CommonPositionOrgCategory;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.repository.CommonPositionOrgCategoryRepository;
import com.jxqixin.trafic.repository.CommonPositionRepository;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.service.ICommonPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommonPositionServiceImpl extends CommonServiceImpl<CommonPosition> implements ICommonPositionService {
	@Autowired
	private CommonPositionRepository commonPositionRepository;
	@Autowired
	private CommonPositionOrgCategoryRepository commonPositionOrgCategoryRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return commonPositionRepository;
	}
	@Override
	public Page findCommonPositions(CommonPositionDto commonPositionDto) {
		Pageable pageable = PageRequest.of(commonPositionDto.getPage(),commonPositionDto.getLimit(), Sort.Direction.DESC,"createDate");
		return commonPositionRepository.findAll((root,  criteriaQuery, criteriaBuilder)-> {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isEmpty(commonPositionDto.getName())){
					list.add(criteriaBuilder.like(root.get("name"),"%" + commonPositionDto.getName() +"%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}, pageable);
	}
	@Override
	public void deleteById(String id) {
		commonPositionOrgCategoryRepository.deleteByCommonPositionId(id);
		commonPositionRepository.deleteById(id);
	}
	@Override
	public List<CommonPosition> findAllCommonPositions() {
		return commonPositionRepository.findAll();
	}

	public List<CommonPosition> findAll(){
		return commonPositionRepository.findAll();
	}

	@Override
	public List<String> findIdsByOrgCategoryId(String orgCategoryId) {
		return commonPositionOrgCategoryRepository.findIdsByOrgCategoryId(orgCategoryId);
	}
	@Override
	public void assign2OrgCategory(String[] commonPositionIdArray, String orgCategoryId) {
		commonPositionOrgCategoryRepository.deleteByOrgCategoryId(orgCategoryId);
		if(commonPositionIdArray!=null && commonPositionIdArray.length>0) {
			List<CommonPositionOrgCategory> list = new ArrayList<>();
			OrgCategory orgCategory = new OrgCategory();
			orgCategory.setId(orgCategoryId);
			for (int i = 0; i < commonPositionIdArray.length; i++) {
				CommonPosition commonPosition = new CommonPosition();
				commonPosition.setId(commonPositionIdArray[i]);
				CommonPositionOrgCategory commonPositionOrgCategory = new CommonPositionOrgCategory();
				commonPositionOrgCategory.setOrgCategory(orgCategory);
				commonPositionOrgCategory.setCommonPosition(commonPosition);
				list.add(commonPositionOrgCategory);
			}
			commonPositionOrgCategoryRepository.saveAll(list);
		}
	}

	@Override
	public List<CommonPosition> findByOrgCategoryId(String orgCategoryId) {
		return commonPositionOrgCategoryRepository.findCommonPositionByOrgCategoryId(orgCategoryId);
	}
}

package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.HazardSourcesListDto;
import com.jxqixin.trafic.model.HazardSourcesList;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.RiskLevel;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.HazardSourcesListRepository;
import com.jxqixin.trafic.repository.RiskLevelRepository;
import com.jxqixin.trafic.service.IHazardSourcesListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
@Service
@Transactional
public class HazardSourcesListServiceImpl extends CommonServiceImpl<HazardSourcesList> implements IHazardSourcesListService {
	@Autowired
	private HazardSourcesListRepository hazardSourcesListRepository;
	@Autowired
	private RiskLevelRepository riskLevelRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return hazardSourcesListRepository;
	}
	@Override
	public void deleteById(String id) {
		hazardSourcesListRepository.deleteById(id);
	}
	@Override
	public Page findHazardSourcesLists(HazardSourcesListDto hazardSourcesListDto, Org org) {
		if(org==null){
			return Page.empty();
		}
		Pageable pageable = PageRequest.of(hazardSourcesListDto.getPage(),hazardSourcesListDto.getLimit(), Sort.Direction.DESC,"createDate");
		return hazardSourcesListRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<HazardSourcesList,Org> orgJoin = root.join("org",JoinType.INNER);
				return criteriaBuilder.equal(orgJoin.get("id"),org.getId());
			}
		}, pageable);
	}
	@Override
	public void addHazardSourcesList(HazardSourcesList hazardSourcesList) {
		saveHazardSourcesList(hazardSourcesList);
	}

	@Override
	public void updateHazardSourcesList(HazardSourcesList hazardSourcesList) {
		saveHazardSourcesList(hazardSourcesList);
	}

	/**
	 * 新增或更新风险源清单
	 * @param hazardSourcesList
	 */
	private void saveHazardSourcesList(HazardSourcesList hazardSourcesList){
		hazardSourcesList.setCriterion(hazardSourcesList.getConsequence()*hazardSourcesList.getHappen());
		RiskLevel riskLevel = riskLevelRepository.findByCriterion(hazardSourcesList.getCriterion());
		if(riskLevel==null){
			throw new RuntimeException("无该风险等级!请检查风险发生可能性和严重程度值是否正确!");
		}
		hazardSourcesList.setFourColor(riskLevel.getColor());
		hazardSourcesList.setRiskLevel(riskLevel.getName());

		hazardSourcesListRepository.save(hazardSourcesList);
	}
}

package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.DangerGoodsCheckDetailRecordDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CategoryRepository;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckDetailRecordRepository;
import com.jxqixin.trafic.service.ICategoryService;
import com.jxqixin.trafic.service.IDangerGoodsCheckDetailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DangerGoodsCheckDetailRecordServiceImpl extends CommonServiceImpl<DangerGoodsCheckDetailRecord> implements IDangerGoodsCheckDetailRecordService {
	@Autowired
	private DangerGoodsCheckDetailRecordRepository dangerGoodsCheckDetailRecordRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return dangerGoodsCheckDetailRecordRepository;
	}
	@Autowired
	private CategoryRepository categoryRepository;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public List<DangerGoodsCheckDetailRecord> findDangerGoodsCheckDetailRecordsNoPage(DangerGoodsCheckDetailRecordDto recordDto, Org org) {
		return dangerGoodsCheckDetailRecordRepository.findAll(new CustomSpecification(recordDto,org), Sort.by(Sort.Direction.DESC, "checkDate"));
	}

	@Override
	public Page findDangerGoodsCheckDetailRecords(DangerGoodsCheckDetailRecordDto recordDto,Org org) {
		Pageable pageable = PageRequest.of(recordDto.getPage(),recordDto.getLimit(), Sort.Direction.DESC,"checkDate");
		return dangerGoodsCheckDetailRecordRepository.findAll(new CustomSpecification(recordDto,org), pageable);
	}

	@Override
	public void deleteById(String id) {
		dangerGoodsCheckDetailRecordRepository.deleteById(id);
	}
	@Override
	public Object[] analysis(String checkDateFrom, String checkDateTo,Org org) {
		Object[] objArr = null;
		if(org!=null) {
			String orgId = org.getId();
			try {
				if (StringUtils.isEmpty(checkDateFrom) && StringUtils.isEmpty(checkDateTo)) {
					objArr = dangerGoodsCheckDetailRecordRepository.analysisAll(orgId);
				}else if (!StringUtils.isEmpty(checkDateFrom) && StringUtils.isEmpty(checkDateTo)) {
					objArr = dangerGoodsCheckDetailRecordRepository.analysisFrom(format.parse(checkDateFrom),orgId);
				}else if (StringUtils.isEmpty(checkDateFrom) && !StringUtils.isEmpty(checkDateTo)) {
					objArr = dangerGoodsCheckDetailRecordRepository.analysisTo(format.parse(checkDateTo),orgId);
				}else {
					objArr = dangerGoodsCheckDetailRecordRepository.analysisBetween(format.parse(checkDateFrom), format.parse(checkDateTo), orgId);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(objArr == null || objArr.length<=0){
			//查询隐患严重程度
			List<Category> categories = categoryRepository.findByType("隐患排查严重程度");
			if(!CollectionUtils.isEmpty(categories)){
				objArr = new Object[categories.size()];

				for(int i = 0;i<categories.size();i++){
					Category category = categories.get(i);
					Object[] childArr = new Object[5];
					childArr[0] = category.getName();
					childArr[1] = 0;
					childArr[2] = 0;
					childArr[3] = 0;
					childArr[4] = 0;

					objArr[i] = childArr;
				}
			}
		}
		return objArr;
	}
	@Override
	public Object[] statistics(String checkDateFrom, String checkDateTo,Org org) {
		Object[] objArr = null;
		if(org!=null) {
			String orgId = org.getId();
			try {
				if (StringUtils.isEmpty(checkDateFrom) && StringUtils.isEmpty(checkDateTo)) {
					objArr = dangerGoodsCheckDetailRecordRepository.statisticsAll(orgId);
				}else if (!StringUtils.isEmpty(checkDateFrom) && StringUtils.isEmpty(checkDateTo)) {
					objArr = dangerGoodsCheckDetailRecordRepository.statisticsFrom(format.parse(checkDateFrom),orgId);
				}else if (StringUtils.isEmpty(checkDateFrom) && !StringUtils.isEmpty(checkDateTo)) {
					objArr = dangerGoodsCheckDetailRecordRepository.statisticsTo(format.parse(checkDateTo),orgId);
				}else {
					objArr = dangerGoodsCheckDetailRecordRepository.statisticsBetween(format.parse(checkDateFrom), format.parse(checkDateTo), orgId);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(objArr == null || objArr.length<=0){
			//查询隐患严重程度
			List<Category> categories = categoryRepository.findByType("隐患排查原因类别");
			if(!CollectionUtils.isEmpty(categories)){
				objArr = new Object[categories.size()];

				for(int i = 0;i<categories.size();i++){
					Category category = categories.get(i);
					Object[] childArr = new Object[5];
					childArr[0] = category.getName();
					childArr[1] = 0;
					childArr[2] = 0;
					childArr[3] = 0;
					childArr[4] = 0;

					objArr[i] = childArr;
				}
			}
		}
		return objArr;
	}

	class CustomSpecification implements Specification{
		private DangerGoodsCheckDetailRecordDto recordDto;
		private Org org;
		CustomSpecification(DangerGoodsCheckDetailRecordDto recordDto,Org org){
			this.recordDto = recordDto;
			this.org = org;
		}
		@Override
		public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
			List<Predicate> list = new ArrayList<>();

			if(!StringUtils.isEmpty(recordDto.getCheckDateFrom())){
				try {
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("checkDate"),format.parse(recordDto.getCheckDateFrom())));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new RuntimeException("起始日期格式不正确:" + recordDto.getCheckDateFrom() +",日期格式应为:yyyy-MM-dd");
				}
			}
			if(!StringUtils.isEmpty(recordDto.getCheckDateTo())){
				try {
					list.add(criteriaBuilder.lessThanOrEqualTo(root.get("checkDate"),format.parse(recordDto.getCheckDateTo())));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new RuntimeException("截止日期格式不正确:" + recordDto.getCheckDateTo() +",日期格式应为:yyyy-MM-dd");
				}
			}

			if(org!=null){
				Join<DangerGoodsCheckDetailRecord,Org> orgJoin = root.join("org",JoinType.INNER);
				list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
			}
			Predicate[] predicates = new Predicate[list.size()];
			return criteriaBuilder.and(list.toArray(predicates));
		}
	}
}





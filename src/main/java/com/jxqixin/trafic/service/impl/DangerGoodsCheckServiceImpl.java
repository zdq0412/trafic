package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.DangerGoodsCheckDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.*;
import com.jxqixin.trafic.service.IDangerGoodsCheckService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DangerGoodsCheckServiceImpl extends CommonServiceImpl<DangerGoodsCheck> implements IDangerGoodsCheckService {
	@Autowired
	private DangerGoodsCheckRepository templateRepository;
	@Autowired
	private DangerGoodsCheckDetailRepository dangerGoodsCheckDetailRepository;
	@Autowired
	private DangerGoodsCheckTemplateRepository dangerGoodsCheckTemplateRepository;
	@Autowired
	private DangerGoodsCheckDetailRecordRepository dangerGoodsCheckDetailRecordRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findDangerGoodsChecks(DangerGoodsCheckDto dangerGoodsCheckDto,Org org) {
		Pageable pageable = PageRequest.of(dangerGoodsCheckDto.getPage(),dangerGoodsCheckDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<DangerGoodsCheck,Org> orgJoin = root.join("org",JoinType.INNER);
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
	public void updateDetails(String id, List<DangerGoodsCheckDetailRecord> detailList) {
		dangerGoodsCheckDetailRepository.deleteById(id);
		dangerGoodsCheckDetailRepository.saveAll(detailList);
	}

	@Override
	public void importTemplate(String templateId, Org org, String currentUsername) {
		DangerGoodsCheckTemplate template = (DangerGoodsCheckTemplate) dangerGoodsCheckTemplateRepository.findById(templateId).get();
		DangerGoodsCheck dangerGoodsCheck = new DangerGoodsCheck();
		dangerGoodsCheck.setName(template.getName());
		dangerGoodsCheck.setNote(template.getNote());
		dangerGoodsCheck.setCreator(currentUsername);
		if(org!=null){
			dangerGoodsCheck.setOrg(org);
		}
		dangerGoodsCheck = (DangerGoodsCheck) templateRepository.save(dangerGoodsCheck);

		List<DangerGoodsCheckDetail> detailList = dangerGoodsCheckDetailRepository.findByDangerGoodsCheckTemplateId(templateId);

		if(!CollectionUtils.isEmpty(detailList)){
			List<DangerGoodsCheckDetailRecord> recordList = new ArrayList<>();
			for (DangerGoodsCheckDetail detail:detailList) {
				DangerGoodsCheckDetailRecord record = new DangerGoodsCheckDetailRecord();
				BeanUtils.copyProperties(detail,record);

				//record.setDangerGoodsCheck(dangerGoodsCheck);

				recordList.add(record);
			}

			dangerGoodsCheckDetailRecordRepository.saveAll(recordList);
		}
	}
}

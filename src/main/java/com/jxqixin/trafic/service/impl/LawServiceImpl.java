package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.LawRepository;
import com.jxqixin.trafic.repository.NoticeRepository;
import com.jxqixin.trafic.service.ILawService;
import com.jxqixin.trafic.util.StringUtil;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LawServiceImpl extends CommonServiceImpl<Law> implements ILawService {
	@Autowired
	private LawRepository lawRepository;
	@Autowired
	private NoticeRepository noticeRepository;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy");
	@Override
	public CommonRepository getCommonRepository() {
		return lawRepository;
	}
	@Override
	public Page findLaws(NameDto nameDto,Org org) {
		Pageable pageable = PageRequest.of(nameDto.getPage(), nameDto.getLimit(), Sort.Direction.DESC,"publishDate");
		if(org==null) {
			return lawRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<>();
					if(!StringUtils.isEmpty(nameDto.getName())){
						list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName() +"%"));
					}
					list.add(criteriaBuilder.isNull(root.get("org")));
					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}else{
			return lawRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

					List<Predicate> list = new ArrayList<>();
					//企业字段不为空的
					if(!StringUtils.isEmpty(nameDto.getName())){
						list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName() +"%"));
					}
					Join<Law, Org> orgJoin = root.join("org",JoinType.LEFT);
					list.add(criteriaBuilder.or(criteriaBuilder.equal(orgJoin.get("id"),org.getId()),criteriaBuilder.isNull(root.get("org"))));
					//企业类别
					if(org.getOrgCategory()!=null) {
						Join<Law, OrgCategory> orgCategoryJoin = root.join("orgCategory", JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("orgCategory")), criteriaBuilder.equal(orgCategoryJoin.get("id"), org.getOrgCategory().getId())));
					}
					//省市区
					if(org.getProvince()!=null) {
						Join<Law, Category> provinceJoin = root.join("province", JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("province")),criteriaBuilder.equal(provinceJoin.get("id"),org.getProvince().getId())));
					}
					if(org.getCity()!=null) {
						Join<Law, Category> cityJoin = root.join("city", JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("city")),criteriaBuilder.equal(cityJoin.get("id"),org.getCity().getId())));
					}
					if(org.getRegion()!=null){
						Join<Law,Category> regionJoin = root.join("region",JoinType.LEFT);
						list.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("region")),criteriaBuilder.equal(regionJoin.get("id"),org.getRegion().getId())));
					}

					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}
	}
	@Override
	public void deleteById(String id) {
		noticeRepository.updateLawId2Null(id);
		lawRepository.deleteById(id);
	}
	@Override
	public void addLaw(Law law, Org org) {
		String maxNum = "";
		//查找最大发文字号
		if(org == null){
			maxNum = lawRepository.findMaxNumWhereOrgIdIsNull();
		}else{
			maxNum = lawRepository.findMaxNumByOrgId(org.getId());
			law.setProvince(org.getProvince());
			law.setCity(org.getCity());
			law.setRegion(org.getRegion());
			law.setOrgCategory(org.getOrgCategory());
			law.setOrg(org);
		}
		String newNum = StringUtil.generateNewNum(org==null?null:org.getShortName(),maxNum);
		law.setNum(newNum);
		lawRepository.save(law);
	}
	@Override
	public void publishLaw(String id) {
		Law law = (Law) lawRepository.findById(id).get();
		Notice notice = new Notice();
		BeanUtils.copyProperties(law,notice);
		notice.setLaw(law);
		notice.setPublishDate(new Date());

		noticeRepository.save(notice);
	}
}

package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.LawRepository;
import com.jxqixin.trafic.repository.NoticeRepository;
import com.jxqixin.trafic.service.ILawService;
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

					Join<Law, Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("province"),orgJoin.get("province")),
							criteriaBuilder.equal(root.get("city"),orgJoin.get("city")),
							criteriaBuilder.equal(root.get("region"),orgJoin.get("region"))))	;

					OrgCategory orgCategory = org.getOrgCategory();
					if(orgCategory!=null){
						Join<Law,OrgCategory> lawOrgCategoryJoin = root.join("orgCategory",JoinType.INNER);
						Join<Org,OrgCategory> orgOrgCategoryJoin = orgJoin.join("orgCategory",JoinType.INNER);
						list.add(criteriaBuilder.equal(orgOrgCategoryJoin.get("id"), lawOrgCategoryJoin.get("id")));
					}

					//过滤本企业发布或超级管理员发布的法律法规文件
					list.add(criteriaBuilder.or(criteriaBuilder.equal(orgJoin.get("id"),org.getId()),criteriaBuilder.isNull(root.get("org"))));
					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}
	}
	@Override
	public void deleteById(String id) {
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
		String newNum = generateNewNum(org==null?null:org.getShortName(),maxNum);
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

	/**
	 * 根据企业简称生成新的发文字号
	 * @param num 企业简称
	 * @return
	 */
	private String generateNewNum(String num,String maxNum) {
		Date now = new Date();
		String currentYear = format.format(now);
		if(StringUtils.isEmpty(maxNum)){
			if(StringUtils.isEmpty(num)){
				return currentYear + "0001";
			}else{
				return num + currentYear + "0001";
			}
		}else{
			//截取后倒数第八位到倒数第四位
			String year = maxNum.substring(maxNum.length()-8,maxNum.length()-4);
			if(currentYear.compareTo(year)>0){
				//截取后四位加一
				String last4 = maxNum.substring(maxNum.length()-4);
				int intNum = Integer.parseInt(last4) + 1;
				String strNum = String.valueOf(intNum);
				switch (strNum.length()){
					case 1:{
						last4 = "000" + strNum;
						break;
					}
					case 2:{
						last4 = "00" + strNum;
						break;
					}
					case 3:{
						last4 = "0" + strNum;
						break;
					}case 4:{
						last4 = strNum;
						break;
					}
				}
				return num==null?currentYear+last4:num+currentYear+last4;
			}else{
				return num==null?currentYear+"0001":num+currentYear+"0001";
			}
		}
	}

	public static void main(String[] args) {
		String year = "20200001";
		System.out.println(year.substring(year.length()-4));
	}
}

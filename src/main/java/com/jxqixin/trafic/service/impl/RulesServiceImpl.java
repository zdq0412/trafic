package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Notice;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.RuleTemplate;
import com.jxqixin.trafic.model.Rules;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.NoticeRepository;
import com.jxqixin.trafic.repository.RuleTemplateRepository;
import com.jxqixin.trafic.repository.RulesRepository;
import com.jxqixin.trafic.service.IRulesService;
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
public class RulesServiceImpl extends CommonServiceImpl<Rules> implements IRulesService {
	@Autowired
	private RulesRepository rulesRepository;
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private RuleTemplateRepository templateRepository;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy");
	@Override
	public CommonRepository getCommonRepository() {
		return rulesRepository;
	}
	@Override
	public Page findRules(NameDto nameDto,Org org) {
		Pageable pageable = PageRequest.of(nameDto.getPage(), nameDto.getLimit(), Sort.Direction.DESC,"publishDate");
		if(org==null) {
			return rulesRepository.findAll(new Specification() {
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
			return rulesRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<>();
					//企业字段不为空的
					if(!StringUtils.isEmpty(nameDto.getName())){
						list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName() +"%"));
					}
					Join<Rules, Org> orgJoin = root.join("org",JoinType.INNER);
					//过滤本企业发布或超级管理员发布的法律法规文件
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}
	}
	@Override
	public void deleteById(String id) {
		rulesRepository.deleteById(id);
	}
	@Override
	public void addRule(Rules rules, Org org) {
		String maxNum = "";
		//查找最大发文字号
		if(org == null){
			maxNum = rulesRepository.findMaxNumWhereOrgIdIsNull();
		}else{
			maxNum = rulesRepository.findMaxNumByOrgId(org.getId());
			rules.setProvince(org.getProvince());
			rules.setCity(org.getCity());
			rules.setRegion(org.getRegion());
			rules.setOrgCategory(org.getOrgCategory());
			rules.setOrg(org);
		}
		String newNum = StringUtil.generateNewNum(org==null?null:org.getShortName(),maxNum);
		rules.setNum(newNum);
		rulesRepository.save(rules);
	}

	@Override
	public void publishRules(String id) {
		Rules rules = (Rules) rulesRepository.findById(id).get();
		Notice notice = new Notice();
		BeanUtils.copyProperties(rules,notice);
		notice.setRules(rules);
		noticeRepository.save(notice);
	}

	@Override
	public Page findTemplates(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"publishDate");
		return rulesRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.and(criteriaBuilder.isNull(root.get("org")));
			}
		}, pageable);
	}

	@Override
	public void importTemplate(String templateId, Org org) {
		RuleTemplate template = (RuleTemplate) templateRepository.findById(templateId).get();
		Rules rules = new Rules();
		rules.setName(template.getName());
		rules.setNote(template.getNote());
		rules.setContent(template.getContent());
		rules.setPublishDate(new Date());
		rules.setTimeliness("有效");
		rules.setNum(StringUtil.generateNewNum(org==null?"":org.getShortName(),rulesRepository.findMaxNumByOrgId(org==null?null:org.getId())));
		if(org!=null){
			rules.setOrg(org);
			rules.setProvince(org.getProvince());
			rules.setCity(org.getCity());
			rules.setRegion(org.getRegion());
			rules.setOrgCategory(org.getOrgCategory());
		}

		rulesRepository.save(rules);
	}

	/**
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
			if(currentYear.compareTo(year)==0){
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
	}*/
}

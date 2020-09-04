package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Notice;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.NoticeRepository;
import com.jxqixin.trafic.service.INoticeService;
import com.jxqixin.trafic.util.StringUtil;
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
public class NoticeServiceImpl extends CommonServiceImpl<Notice> implements INoticeService {
	@Autowired
	private NoticeRepository noticeRepository;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy");
	@Override
	public CommonRepository getCommonRepository() {
		return noticeRepository;
	}
	@Override
	public Page findNotices(NameDto nameDto,Org org) {
		Pageable pageable = PageRequest.of(nameDto.getPage(), nameDto.getLimit(), Sort.Direction.DESC,"publishDate");
		if(org==null) {
			return noticeRepository.findAll(new Specification() {
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
			return noticeRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<>();
					//企业字段不为空的
					if(!StringUtils.isEmpty(nameDto.getName())){
						list.add(criteriaBuilder.like(root.get("name"),"%" + nameDto.getName() +"%"));
					}

					Join<Notice, Org> orgJoin = root.join("org",JoinType.INNER);

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
		noticeRepository.deleteById(id);
	}
	@Override
	public void addNotice(Notice notice, Org org) {
		String num = notice.getNum();
		String maxNum = "";
		//查找最大发文字号
		if(org == null){
			maxNum = noticeRepository.findMaxNumWhereOrgIdIsNull();
		}else{
			maxNum = noticeRepository.findMaxNumByOrgId(org.getId());
			notice.setProvince(org.getProvince());
			notice.setCity(org.getCity());
			notice.setRegion(org.getRegion());
			notice.setOrgCategory(org.getOrgCategory());
			notice.setOrg(org);
		}
		String newNum = StringUtil.generateNewNum(org.getShortName(),maxNum);
		notice.setNum(newNum);

		noticeRepository.save(notice);
	}

	@Override
	public List<Notice> findByLawId(String lawId) {
		return noticeRepository.findByLawId(lawId);
	}

	@Override
	public List<Notice> findByRulesId(String rulesId) {
		return noticeRepository.findByRulesId(rulesId);
	}
}

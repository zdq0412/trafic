package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Meeting;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.MeetingRepository;
import com.jxqixin.trafic.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Service
@Transactional
public class MeetingServiceImpl extends CommonServiceImpl<Meeting> implements IMeetingService {
	@Autowired
	private MeetingRepository meetingRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return meetingRepository;
	}
	@Override
	public Page findMeetings(NameDto nameDto,String type) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return meetingRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("type"),type);
			}
		},pageable);
	}

	@Override
	public void deleteById(String id) {
		meetingRepository.deleteById(id);
	}
}

package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.MeetingDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.MeetingRepository;
import com.jxqixin.trafic.repository.MeetingTemplateRepository;
import com.jxqixin.trafic.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MeetingServiceImpl extends CommonServiceImpl<Meeting> implements IMeetingService {
	@Autowired
	private MeetingRepository meetingRepository;
	@Autowired
	private MeetingTemplateRepository meetingTemplateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return meetingRepository;
	}
	@Override
	public Page findMeetings(MeetingDto meetingDto, Org org) {
		Pageable pageable = PageRequest.of(meetingDto.getPage(),meetingDto.getLimit(), Sort.Direction.DESC,"createDate");
		return meetingRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<Meeting,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}

	@Override
	public void deleteById(String id) {
		meetingRepository.deleteById(id);
	}

	@Override
	public Meeting importTemplate(String templateId, Org org,String username) {
		MeetingTemplate template = (MeetingTemplate) meetingTemplateRepository.findById(templateId).get();
		Meeting meeting = new Meeting();
		if(org!=null){
			meeting.setOrg(org);
		}
		meeting.setAttendants(template.getAttendants());
		meeting.setContent(template.getContent());
		meeting.setMeetingName(template.getMeetingName());
		meeting.setMeetingPlace(template.getMeetingPlace());
		meeting.setRecorder(template.getRecorder());
		meeting.setPresident(template.getPresident());
		meeting.setName(template.getName());
		meeting.setFinalDecision(template.getFinalDecision());
		meeting.setCreateDate(new Date());
		meeting.setCreator(username);
		meeting = (Meeting) meetingRepository.save(meeting);
		return meeting;
	}
}

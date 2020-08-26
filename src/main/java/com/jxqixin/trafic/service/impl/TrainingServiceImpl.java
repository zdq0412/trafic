package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.TrainingDto;
import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import com.jxqixin.trafic.model.Training;
import com.jxqixin.trafic.model.TrainingTemplate;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.TrainingRepository;
import com.jxqixin.trafic.repository.TrainingTemplateRepository;
import com.jxqixin.trafic.service.ITrainingService;
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
public class TrainingServiceImpl extends CommonServiceImpl<Training> implements ITrainingService {
	@Autowired
	private TrainingRepository trainingRepository;
	@Autowired
	private TrainingTemplateRepository trainingTemplateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return trainingRepository;
	}
	@Override
	public Page findTrainings(TrainingDto trainingDto,Org org) {
		Pageable pageable = PageRequest.of(trainingDto.getPage(),trainingDto.getLimit(), Sort.Direction.DESC,"createDate");
		return trainingRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				if(org!=null){
					Join<Training,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		},pageable);
	}

	@Override
	public void deleteById(String id) {
		trainingRepository.deleteById(id);
	}

	@Override
	public void importTemplate(String templateId, Org org,String username) {
		TrainingTemplate template = (TrainingTemplate) trainingTemplateRepository.findById(templateId).get();
		Training training = new Training();
		if(org!=null){
			training.setOrg(org);
		}
		training.setAttendance(template.getAttendance());
		training.setAttendants(template.getAttendants());
		training.setContent(template.getContent());
		training.setTrainingName(template.getTrainingName());
		training.setTrainingPlace(template.getTrainingPlace());
		training.setRecorder(template.getRecorder());
		training.setPresident(template.getPresident());
		training.setName(template.getName());
		training.setCreateDate(new Date());
		training.setCreator(username);
		trainingRepository.save(training);
	}
}

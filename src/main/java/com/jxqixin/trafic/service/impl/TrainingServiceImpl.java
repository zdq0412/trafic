package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.NameDto;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;

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
	public Page findTrainings(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return trainingRepository.findAll(pageable);
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

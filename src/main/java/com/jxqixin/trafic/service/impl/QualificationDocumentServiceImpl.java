package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.QualificationDocumentDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.QualificationDocument;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.QualificationDocumentRepository;
import com.jxqixin.trafic.service.IQualificationDocumentService;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QualificationDocumentServiceImpl extends CommonServiceImpl<QualificationDocument> implements IQualificationDocumentService {
	@Autowired
	private QualificationDocumentRepository qualificationDocumentRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return qualificationDocumentRepository;
	}
	@Override
	public Page findQualificationDocuments(QualificationDocumentDto qualificationDocumentDto) {
		Pageable pageable = PageRequest.of(qualificationDocumentDto.getPage(),qualificationDocumentDto.getLimit(), Sort.Direction.DESC,"createDate");
		return qualificationDocumentRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(qualificationDocumentDto.getEmpId())){
					Join<QualificationDocumentDto, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),qualificationDocumentDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		QualificationDocument qualificationDocument = (QualificationDocument) qualificationDocumentRepository.findById(id).get();
		qualificationDocument.setDeleted(true);
		qualificationDocumentRepository.save(qualificationDocument);
	}
}

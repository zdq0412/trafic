package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.IDCardDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.IDCard;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.IDCardRepository;
import com.jxqixin.trafic.service.IIDCardService;
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
public class IDCardServiceImpl extends CommonServiceImpl<IDCard> implements IIDCardService {
	@Autowired
	private IDCardRepository idcardRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return idcardRepository;
	}
	@Override
	public Page findIDCards(IDCardDto idCardDto) {
		Pageable pageable = PageRequest.of(idCardDto.getPage(),idCardDto.getLimit(), Sort.Direction.DESC,"createDate");
		return idcardRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();

				list.add(criteriaBuilder.equal(root.get("deleted"),false));

				if(!StringUtils.isEmpty(idCardDto.getEmpId())){
					Join<IDCard, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),idCardDto.getEmpId()));
				}

				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}

	@Override
	public void deleteById(String id) {
		IDCard idCard = (IDCard) idcardRepository.findById(id).get();
		idCard.setDeleted(true);

		idcardRepository.save(idCard);
	}
}

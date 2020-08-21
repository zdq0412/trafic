package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.dto.GPSAccountDto;
import com.jxqixin.trafic.model.GPSAccount;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.GPSAccountRepository;
import com.jxqixin.trafic.service.IGPSAccountService;
import org.aspectj.weaver.ast.Or;
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
import java.util.List;

@Service
@Transactional
public class GPSAccountServiceImpl extends CommonServiceImpl<GPSAccount> implements IGPSAccountService {
	@Autowired
	private GPSAccountRepository gpsAccountRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return gpsAccountRepository;
	}

	@Override
	public Page findGPSAccounts(GPSAccountDto gpsAccountDto, Org org) {
		Pageable pageable = PageRequest.of(gpsAccountDto.getPage(),gpsAccountDto.getLimit(), Sort.Direction.DESC,"createDate");
		return gpsAccountRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(org!=null){
					Join<GPSAccount, Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}

	@Override
	public void deleteById(String id) {
		gpsAccountRepository.deleteById(id);
	}
}

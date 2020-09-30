package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.RemindDto;
import com.jxqixin.trafic.mapper.RemindMapper;
import com.jxqixin.trafic.model.AccidentRecord;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.Remind;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.RemindRepository;
import com.jxqixin.trafic.service.IRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class RemindServiceImpl extends CommonServiceImpl<Remind> implements IRemindService {
	@Autowired
	private RemindRepository remindRepository;
	@Resource
	private RemindMapper remindMapper;
	@Override
	public CommonRepository getCommonRepository() {
		return remindRepository;
	}
	@Override
	public Page findReminds(RemindDto remindDto, Org org) {
		Pageable pageable = PageRequest.of(remindDto.getPage(),remindDto.getLimit(), Sort.Direction.DESC,"createDate");
		return remindRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if(org!=null){
					Join<AccidentRecord,Org> orgJoin = root.join("org",JoinType.INNER);
					list.add(criteriaBuilder.equal(orgJoin.get("id"),org.getId()));
				}

				list.add(criteriaBuilder.equal(root.get("deleted"),true));
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		Remind remind = (Remind) remindRepository.findById(id).get();
		remind.setDeleted(true);
		remindRepository.save(remind);
	}
}

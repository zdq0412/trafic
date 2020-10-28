package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.model.Pics;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.PicsRepository;
import com.jxqixin.trafic.service.IPicsService;
import com.jxqixin.trafic.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PicsServiceImpl extends CommonServiceImpl<Pics> implements IPicsService {
	@Autowired
	private PicsRepository picsRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return picsRepository;
	}
	@Override
	public void deleteById(String id) {
		Pics pics = (Pics) picsRepository.findById(id).get();
		if(pics!=null && !StringUtils.isEmpty(pics.getRealPath())){
			FileUtil.deleteFile(pics.getRealPath());
		}
		picsRepository.deleteById(id);
	}
	@Override
	public List<Pics> findPics(String type, String pid) {
		return picsRepository.findAll((root,criteriaQuery,criteriaBuilder)->{
			List<Predicate> list = new ArrayList();
			list.add(criteriaBuilder.equal(root.get("type"),type));
			list.add(criteriaBuilder.equal(root.get("pid"),pid));
			Predicate[] predicates = new Predicate[list.size()];
			return criteriaBuilder.and(list.toArray(predicates));
		});
	}
	@Override
	public void deleteAll(String type, String pid) {
		List<Pics> list = this.findPics(type,pid);
		if(!CollectionUtils.isEmpty(list)){
			list.forEach(pics -> {
				deleteById(pics.getId());
			});
		}
	}
}

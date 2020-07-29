package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.DangerGoodsCheckDetail;
import com.jxqixin.trafic.model.DangerGoodsCheckTemplate;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckDetailRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckTemplateRepository;
import com.jxqixin.trafic.service.IDangerGoodsCheckTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DangerGoodsCheckTemplateServiceImpl extends CommonServiceImpl<DangerGoodsCheckTemplate> implements IDangerGoodsCheckTemplateService {
	@Autowired
	private DangerGoodsCheckTemplateRepository templateRepository;
	@Autowired
	private DangerGoodsCheckDetailRepository dangerGoodsCheckDetailRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}
	@Override
	public Page findDangerGoodsCheckTemplates(NameDto nameDto,String type) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit(), Sort.Direction.DESC,"createDate");
		return templateRepository.findAll(pageable);
	}

	@Override
	public DangerGoodsCheckTemplate findByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		templateRepository.deleteById(id);
	}

	@Override
	public void updateDetails(String id, List<DangerGoodsCheckDetail> detailList) {
		dangerGoodsCheckDetailRepository.deleteByTemplateId(id);
		dangerGoodsCheckDetailRepository.saveAll(detailList);
	}
}

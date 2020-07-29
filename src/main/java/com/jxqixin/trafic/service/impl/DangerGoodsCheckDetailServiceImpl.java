package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.model.DangerGoodsCheckDetail;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckDetailRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckTemplateRepository;
import com.jxqixin.trafic.service.IDangerGoodsCheckDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DangerGoodsCheckDetailServiceImpl extends CommonServiceImpl<DangerGoodsCheckDetail> implements IDangerGoodsCheckDetailService {
	@Autowired
	private DangerGoodsCheckDetailRepository templateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}

	@Override
	public List<DangerGoodsCheckDetail> findByDangerGoodsCheckTemplateId(String dangerGoodsCheckTemplateId) {
		return templateRepository.findByDangerGoodsCheckTemplateId(dangerGoodsCheckTemplateId);
	}
}

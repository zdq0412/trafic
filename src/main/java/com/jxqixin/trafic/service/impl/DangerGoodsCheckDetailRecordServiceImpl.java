package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DangerGoodsCheckDetailRecordRepository;
import com.jxqixin.trafic.service.IDangerGoodsCheckDetailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DangerGoodsCheckDetailRecordServiceImpl extends CommonServiceImpl<DangerGoodsCheckDetailRecord> implements IDangerGoodsCheckDetailRecordService {
	@Autowired
	private DangerGoodsCheckDetailRecordRepository templateRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return templateRepository;
	}

	@Override
	public List<DangerGoodsCheckDetailRecord> findByDangerGoodsCheckId(String dangerGoodsCheckId) {
		return templateRepository.findByDangerGoodsCheckId(dangerGoodsCheckId);
	}
}

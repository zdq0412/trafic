package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Position;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.PositionRepository;
import com.jxqixin.trafic.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class PositionServiceImpl extends CommonServiceImpl<Position> implements IPositionService {
	@Autowired
	private PositionRepository positionRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return positionRepository;
	}

	@Override
	public Page findPositions(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return positionRepository.findAll(new NameSpecification(nameDto),pageable);
	}

	@Override
	public void deleteById(String id) {

	}
}

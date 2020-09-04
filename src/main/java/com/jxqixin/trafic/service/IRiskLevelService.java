package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.RiskLevelDto;
import com.jxqixin.trafic.model.RiskLevel;
import org.springframework.data.domain.Page;

public interface IRiskLevelService extends ICommonService<RiskLevel> {
    /**
     * 根据ID删除危险源
     * @param id
     */
    void deleteById(String id);
    /**
     * 分页查找
     * @param riskLevelDto
     * @return
     */
    Page findRiskLevels(RiskLevelDto riskLevelDto);
}

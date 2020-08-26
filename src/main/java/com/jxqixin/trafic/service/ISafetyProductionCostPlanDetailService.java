package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.SafetyProductionCostPlanDetailDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import org.springframework.data.domain.Page;

public interface ISafetyProductionCostPlanDetailService extends ICommonService<SafetyProductionCostPlanDetail> {
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 分页查询安全生产费用详情
     * @param safetyProductionCostPlanDetailDto
     * @return
     */
    Page findSafetyProductionCostPlanDetails(SafetyProductionCostPlanDetailDto safetyProductionCostPlanDetailDto, Org org);
}

package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.EmergencyPlanDrillDto;
import com.jxqixin.trafic.model.EmergencyPlanDrill;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IEmergencyPlanDrillService extends ICommonService<EmergencyPlanDrill> {
    /**
     * 分页查询信息
     * @param emergencyPlanDrillDto
     * @return
     */
    Page findEmergencyPlanDrills(EmergencyPlanDrillDto emergencyPlanDrillDto,Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

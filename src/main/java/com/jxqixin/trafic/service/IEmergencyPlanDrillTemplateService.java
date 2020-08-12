package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.EmergencyPlanDrillTemplateDto;
import com.jxqixin.trafic.model.EmergencyPlanDrillTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IEmergencyPlanDrillTemplateService extends ICommonService<EmergencyPlanDrillTemplate> {
    /**
     * 分页查询信息
     * @param emergencyPlanDrillTemplateDto
     * @return
     */
    Page findEmergencyPlanDrillTemplates(EmergencyPlanDrillTemplateDto emergencyPlanDrillTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

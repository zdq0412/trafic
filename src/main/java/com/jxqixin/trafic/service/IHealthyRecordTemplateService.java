package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.HealthyRecordTemplateDto;
import com.jxqixin.trafic.model.HealthyRecordTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IHealthyRecordTemplateService extends ICommonService<HealthyRecordTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findHealthyRecordTemplates(HealthyRecordTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

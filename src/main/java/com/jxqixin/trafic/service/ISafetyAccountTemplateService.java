package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.SafetyAccountTemplateDto;
import com.jxqixin.trafic.model.SafetyAccountTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface ISafetyAccountTemplateService extends ICommonService<SafetyAccountTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findSafetyAccountTemplates(SafetyAccountTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.SecurityMonthTemplateDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SecurityMonthTemplate;
import org.springframework.data.domain.Page;

public interface ISecurityMonthTemplateService extends ICommonService<SecurityMonthTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findSecurityMonthTemplates(SecurityMonthTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

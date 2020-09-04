package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.SecurityActivityTemplateDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SecurityActivityTemplate;
import org.springframework.data.domain.Page;

public interface ISecurityActivityTemplateService extends ICommonService<SecurityActivityTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findSecurityActivityTemplates(SecurityActivityTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

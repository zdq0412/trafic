package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.SecurityBuildTemplateDto;
import com.jxqixin.trafic.model.SecurityBuildTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface ISecurityBuildTemplateService extends ICommonService<SecurityBuildTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findSecurityBuildTemplates(SecurityBuildTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

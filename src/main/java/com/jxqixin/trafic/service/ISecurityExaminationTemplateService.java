package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.SecurityExaminationTemplateDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SecurityExaminationTemplate;
import org.springframework.data.domain.Page;

public interface ISecurityExaminationTemplateService extends ICommonService<SecurityExaminationTemplate> {
    /**
     * 分页查询信息
     * @param securityExaminationTemplateDto
     * @return
     */
    Page findSecurityExaminationTemplates(SecurityExaminationTemplateDto securityExaminationTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

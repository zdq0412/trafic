package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.StandardizationDto;
import com.jxqixin.trafic.dto.StandardizationTemplateDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.StandardizationTemplate;
import org.springframework.data.domain.Page;

public interface IStandardizationTemplateService extends ICommonService<StandardizationTemplate> {
    /**
     * 分页查询信息
     * @param standardizationTemplateDto
     * @return
     */
    Page findStandardizationTemplates(StandardizationTemplateDto standardizationTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.EmpArchivesTemplateDto;
import com.jxqixin.trafic.model.EmpArchivesTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IEmpArchivesTemplateService extends ICommonService<EmpArchivesTemplate> {
    /**
     * 分页查询信息
     * @param empArchivesTemplateDto
     * @return
     */
    Page findEmpArchivesTemplates(EmpArchivesTemplateDto empArchivesTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

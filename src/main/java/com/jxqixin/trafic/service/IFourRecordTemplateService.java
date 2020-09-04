package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.FourRecordTemplateDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.FourRecordTemplate;
import org.springframework.data.domain.Page;

public interface IFourRecordTemplateService extends ICommonService<FourRecordTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findFourRecordTemplates(FourRecordTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

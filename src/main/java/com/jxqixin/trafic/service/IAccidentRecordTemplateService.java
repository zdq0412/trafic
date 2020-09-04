package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.AccidentRecordTemplateDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.AccidentRecordTemplate;
import org.springframework.data.domain.Page;

public interface IAccidentRecordTemplateService extends ICommonService<AccidentRecordTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findAccidentRecordTemplates(AccidentRecordTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

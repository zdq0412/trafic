package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.GpsAccountTemplateDto;
import com.jxqixin.trafic.model.GpsAccountTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IGpsAccountTemplateService extends ICommonService<GpsAccountTemplate> {
    /**
     * 分页查询信息
     * @param healthyRecordTemplateDto
     * @return
     */
    Page findGpsAccountTemplates(GpsAccountTemplateDto healthyRecordTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.DeviceArchivesTemplateDto;
import com.jxqixin.trafic.model.DeviceArchivesTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IDeviceArchivesTemplateService extends ICommonService<DeviceArchivesTemplate> {
    /**
     * 分页查询信息
     * @param deviceArchivesTemplateDto
     * @return
     */
    Page findDeviceArchivesTemplates(DeviceArchivesTemplateDto deviceArchivesTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

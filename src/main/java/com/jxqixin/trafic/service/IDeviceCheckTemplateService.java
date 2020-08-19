package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.DeviceCheckTemplateDto;
import com.jxqixin.trafic.model.DeviceCheckTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IDeviceCheckTemplateService extends ICommonService<DeviceCheckTemplate> {
    /**
     * 分页查询信息
     * @param deviceCheckTemplateDto
     * @return
     */
    Page findDeviceCheckTemplates(DeviceCheckTemplateDto deviceCheckTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

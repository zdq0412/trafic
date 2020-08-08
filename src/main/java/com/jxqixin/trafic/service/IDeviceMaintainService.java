package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.DeviceMaintainDto;
import com.jxqixin.trafic.model.DeviceMaintain;
import org.springframework.data.domain.Page;

public interface IDeviceMaintainService extends ICommonService<DeviceMaintain> {
    /**
     * 分页查询信息
     * @param deviceMaintainDto
     * @return
     */
    Page findDeviceMaintains(DeviceMaintainDto deviceMaintainDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

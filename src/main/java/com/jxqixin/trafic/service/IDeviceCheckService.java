package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.DeviceCheckDto;
import com.jxqixin.trafic.model.DeviceCheck;
import org.springframework.data.domain.Page;

public interface IDeviceCheckService extends ICommonService<DeviceCheck> {
    /**
     * 分页查询信息
     * @param contractDto
     * @return
     */
    Page findDeviceChecks(DeviceCheckDto contractDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

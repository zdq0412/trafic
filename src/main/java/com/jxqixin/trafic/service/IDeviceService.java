package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.DeviceDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IDeviceService extends ICommonService<Device> {
    /**
     * 分页查询
     * @param deviceDto
     * @return
     */
    Page findDevices(DeviceDto deviceDto, Org org);
    /**
     * 根据ID删除
     */
    void deleteById(String id);
    /**
     * 删除设备
     * @param id
     */
    void deleteDevice(String id);
}

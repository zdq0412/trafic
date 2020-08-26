package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.DeviceCheckDto;
import com.jxqixin.trafic.model.DeviceCheck;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IDeviceCheckService extends ICommonService<DeviceCheck> {
    /**
     * 分页查询信息
     * @return
     */
    Page findDeviceChecks(DeviceCheckDto deviceCheckDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.DeviceArchiveDto;
import com.jxqixin.trafic.model.DeviceArchive;
import org.springframework.data.domain.Page;
public interface IDeviceArchiveService extends ICommonService<DeviceArchive> {
    /**
     * 分页查询信息
     * @param deviceArchiveDto
     * @return
     */
    Page findDeviceArchives(DeviceArchiveDto deviceArchiveDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

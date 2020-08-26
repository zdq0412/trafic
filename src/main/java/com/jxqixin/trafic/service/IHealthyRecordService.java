package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.HealthyRecordDto;
import com.jxqixin.trafic.model.HealthyRecord;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IHealthyRecordService extends ICommonService<HealthyRecord> {
    /**
     * 分页查询信息
     * @param healthyRecordDto
     * @return
     */
    Page findHealthyRecords(HealthyRecordDto healthyRecordDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

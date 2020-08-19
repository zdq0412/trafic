package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.PreplanDrillRecordDto;
import com.jxqixin.trafic.model.PreplanDrillRecord;
import org.springframework.data.domain.Page;
public interface IPreplanDrillRecordService extends ICommonService<PreplanDrillRecord> {
    /**
     * 分页查询
     * @param preplanDrillRecordDto
     * @return
     */
    Page findPreplanDrillRecords(PreplanDrillRecordDto preplanDrillRecordDto);
    /**
     * 根据ID删除
     */
    void deleteById(String id);
    /**
     * 根据应急预案备案ID查找应急预案演练数量
     * @param emergencyPlanBakId
     * @return
     */
    Long queryCountByEmergencyPlanBakId(String emergencyPlanBakId);
}

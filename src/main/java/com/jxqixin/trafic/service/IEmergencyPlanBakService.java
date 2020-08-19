package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.EmergencyPlanBakDto;
import com.jxqixin.trafic.model.EmergencyPlanBak;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IEmergencyPlanBakService extends ICommonService<EmergencyPlanBak> {
    /**
     * 分页查询
     * @param preplanDrillRecordDto
     * @return
     */
    Page findEmergencyPlanBaks(EmergencyPlanBakDto preplanDrillRecordDto, Org org);
    /**
     * 根据ID删除
     */
    void deleteById(String id);
}

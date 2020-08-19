package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.PreplanDrillRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
public interface PreplanDrillRecordRepository<ID extends Serializable> extends CommonRepository<PreplanDrillRecord,ID> {
    /**
     * 根据应急预案备案删除应急预案演练记录
     * @param emergencyPlanBakId
     */
    @Modifying
    @Query(nativeQuery = true,value = "delete from m038_preplan_drill_record where emergency_plan_bak_id=?1")
    void deleteByEmergencyPlanBakId(String emergencyPlanBakId);
    @Query(nativeQuery = true,value="select count(1) from m038_preplan_drill_record where emergency_plan_bak_id=?1")
    Long queryCountByEmergencyPlanBakId(String emergencyPlanBakId);
}

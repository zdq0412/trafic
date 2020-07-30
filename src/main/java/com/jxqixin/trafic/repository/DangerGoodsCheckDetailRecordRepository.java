package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface DangerGoodsCheckDetailRecordRepository<ID extends Serializable> extends CommonRepository<DangerGoodsCheckDetailRecord,ID> {
    @Query("select detail from DangerGoodsCheckDetailRecord detail where detail.dangerGoodsCheck.id=?1")
    List<DangerGoodsCheckDetailRecord> findByDangerGoodsCheckId(String dangerGoodsCheckId);
    @Modifying
    @Query("delete from DangerGoodsCheckDetailRecord d where d.dangerGoodsCheck.id=?1")
    void deleteByTemplateId(String id);
}

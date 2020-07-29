package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.DangerGoodsCheckDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface DangerGoodsCheckDetailRepository<ID extends Serializable> extends CommonRepository<DangerGoodsCheckDetail,ID> {
    @Query("select detail from DangerGoodsCheckDetail detail where detail.dangerGoodsCheckTemplate.id=?1")
    List<DangerGoodsCheckDetail> findByDangerGoodsCheckTemplateId(String dangerGoodsCheckTemplateId);
    @Modifying
    @Query("delete from DangerGoodsCheckDetail d where d.dangerGoodsCheckTemplate.id=?1")
    void deleteByTemplateId(String id);
}

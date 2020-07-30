package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;

import java.util.List;

public interface IDangerGoodsCheckDetailRecordService extends ICommonService<DangerGoodsCheckDetailRecord> {
    /**
     *  根据危险货物运输企业安全生产隐患排查整改台账模板id查找详情
     * @param dangerGoodsCheckId
     * @return
     */
    List<DangerGoodsCheckDetailRecord> findByDangerGoodsCheckId(String dangerGoodsCheckId);
}

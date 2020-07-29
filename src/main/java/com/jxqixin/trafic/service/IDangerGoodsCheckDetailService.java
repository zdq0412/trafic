package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.DangerGoodsCheckDetail;

import java.util.List;

public interface IDangerGoodsCheckDetailService extends ICommonService<DangerGoodsCheckDetail> {
    /**
     *  根据危险货物运输企业安全生产隐患排查整改台账模板id查找详情
     * @param dangerGoodsCheckTemplateId
     * @return
     */
    List<DangerGoodsCheckDetail> findByDangerGoodsCheckTemplateId(String dangerGoodsCheckTemplateId);
}

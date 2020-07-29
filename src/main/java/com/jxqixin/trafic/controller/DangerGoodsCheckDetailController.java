package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.model.DangerGoodsCheckDetail;
import com.jxqixin.trafic.service.IDangerGoodsCheckDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 危险货物运输企业安全生产隐患排查整改台账模板详情控制器
 */
@RestController
public class DangerGoodsCheckDetailController extends CommonController{
    @Autowired
    private IDangerGoodsCheckDetailService dangerGoodsCheckDetailService;
    /**
     * 根据危险货物运输企业安全生产隐患排查整改台账模板ID查找详情
     * @param id 危险货物运输企业安全生产隐患排查整改台账模板id
     * @return
     */
    @GetMapping("dangerGoodsCheckDetail/dangerGoodsCheckDetail")
    public List<DangerGoodsCheckDetail> dangerGoodsCheckDetail(String id){
        List<DangerGoodsCheckDetail> list = dangerGoodsCheckDetailService.findByDangerGoodsCheckTemplateId(id);
        return list;
    }
}

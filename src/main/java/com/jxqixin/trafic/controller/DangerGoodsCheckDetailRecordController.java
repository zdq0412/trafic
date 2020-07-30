package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import com.jxqixin.trafic.service.IDangerGoodsCheckDetailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 危险货物运输企业安全生产隐患排查整改台账模板详情控制器
 */
@RestController
public class DangerGoodsCheckDetailRecordController extends CommonController{
    @Autowired
    private IDangerGoodsCheckDetailRecordService dangerGoodsCheckDetailRecordService;
    /**
     * 根据危险货物运输企业安全生产隐患排查整改台账ID查找详情
     * @param id 危险货物运输企业安全生产隐患排查整改台账id
     * @return
     */
    @GetMapping("dangerGoodsCheckDetailRecord/dangerGoodsCheckDetailRecord")
    public List<DangerGoodsCheckDetailRecord> dangerGoodsCheckDetailRecord(String id){
        List<DangerGoodsCheckDetailRecord> list = dangerGoodsCheckDetailRecordService.findByDangerGoodsCheckId(id);
        return list;
    }
}

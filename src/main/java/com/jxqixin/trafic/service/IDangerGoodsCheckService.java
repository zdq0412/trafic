package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.DangerGoodsCheck;
import com.jxqixin.trafic.model.DangerGoodsCheckDetail;
import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDangerGoodsCheckService extends ICommonService<DangerGoodsCheck> {
    /**
     * 分页查询模板信息
     * @param nameDto
     * @return
     */
    Page findDangerGoodsChecks(NameDto nameDto);
    /**
     * 根据ID删除模板，同时删除模板下的所有目录
     */
    void deleteById(String id);
    /**
     * 删除template下的详情，添加新的详情
     * @param id  template id
     * @param detailList 详情
     */
    void updateDetails(String id, List<DangerGoodsCheckDetailRecord> detailList);
}

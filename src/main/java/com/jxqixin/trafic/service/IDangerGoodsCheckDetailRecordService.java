package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.DangerGoodsCheckDetailRecordDto;
import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDangerGoodsCheckDetailRecordService extends ICommonService<DangerGoodsCheckDetailRecord> {
    /**
     * 根据条件查询隐患整改记录
     * @param recordDto
     * @return
     */
    List<DangerGoodsCheckDetailRecord> findDangerGoodsCheckDetailRecordsNoPage(DangerGoodsCheckDetailRecordDto recordDto, Org org);
    /**
     * 分页查询隐患整改记录
     * @param record
     * @return
     */
    Page findDangerGoodsCheckDetailRecords(DangerGoodsCheckDetailRecordDto record,Org org);
    /**
     * 根据ID删除隐患整改记录
     * @param id
     */
    void deleteById(String id);
    /**
     * 隐患排查和治理情况分析
     * @param checkDateFrom
     * @param checkDateTo
     * @return
     */
    Object[] analysis(String checkDateFrom, String checkDateTo,Org org);
    /**
     * 隐患类型统计
     * @param checkDateFrom
     * @param checkDateTo
     * @return
     */
    Object[] statistics(String checkDateFrom, String checkDateTo, Org org);
}

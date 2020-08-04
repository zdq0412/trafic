package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.JobHistoryDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JobHistory;
import org.springframework.data.domain.Page;

public interface IJobHistoryService extends ICommonService<JobHistory> {
    /**
     * 分页查询信息
     * @param jobHistoryDto
     * @return
     */
    Page findJobHistorys(JobHistoryDto jobHistoryDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

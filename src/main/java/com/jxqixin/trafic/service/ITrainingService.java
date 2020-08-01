package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Training;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface ITrainingService extends ICommonService<Training> {
    /**
     * 分页查询
     * @param nameDto
     * @return
     */
    Page findTrainings(NameDto nameDto);
    /**
     * 根据ID删除
     */
    void deleteById(String id);
    /**
     * 引入模板
     * @param templateId
     * @param org
     */
    void importTemplate(String templateId, Org org, String username);
}

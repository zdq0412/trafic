package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.TrainingExamineDto;
import com.jxqixin.trafic.model.TrainingExamine;
import org.springframework.data.domain.Page;

public interface ITrainingExamineService extends ICommonService<TrainingExamine> {
    /**
     * 分页查询信息
     * @param trainingExamineDto
     * @return
     */
    Page findTrainingExamines(TrainingExamineDto trainingExamineDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

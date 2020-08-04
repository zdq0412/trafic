package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.InductionTrainingDto;
import com.jxqixin.trafic.model.InductionTraining;
import org.springframework.data.domain.Page;

public interface IInductionTrainingService extends ICommonService<InductionTraining> {
    /**
     * 分页查询信息
     * @param inductionTrainingDto
     * @return
     */
    Page findInductionTrainings(InductionTrainingDto inductionTrainingDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

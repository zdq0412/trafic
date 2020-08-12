package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.GoalExaminationDto;
import com.jxqixin.trafic.model.GoalExamination;
import org.springframework.data.domain.Page;

public interface IGoalExaminationService extends ICommonService<GoalExamination> {
    /**
     * 分页查询信息
     * @param goalExaminationDto
     * @return
     */
    Page findGoalExaminations(GoalExaminationDto goalExaminationDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

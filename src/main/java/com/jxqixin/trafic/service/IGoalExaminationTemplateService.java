package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.GoalExaminationTemplateDto;
import com.jxqixin.trafic.model.GoalExaminationTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IGoalExaminationTemplateService extends ICommonService<GoalExaminationTemplate> {
    /**
     * 分页查询信息
     * @param goalExaminationTemplateDto
     * @return
     */
    Page findGoalExaminationTemplates(GoalExaminationTemplateDto  goalExaminationTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

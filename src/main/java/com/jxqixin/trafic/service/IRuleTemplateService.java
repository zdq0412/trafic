package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.RuleTemplate;
import org.springframework.data.domain.Page;

public interface IRuleTemplateService extends ICommonService<RuleTemplate> {
    /**
     * 分页查询模板信息
     * @param nameDto
     * @return
     */
    Page findRuleTemplates(NameDto nameDto, Org org);
    /**
     * 根据模板名称查找
     * @param name
     * @return
     */
    RuleTemplate findByName(String name);
    /**
     * 根据ID删除模板，同时删除模板下的所有目录
     */
    void deleteById(String id);
}

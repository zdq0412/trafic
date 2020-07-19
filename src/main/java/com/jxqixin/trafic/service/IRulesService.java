package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.Rules;
import org.springframework.data.domain.Page;

public interface IRulesService extends ICommonService<Rules> {
    /**
     * 分页查询安全规章制度信息
     * @param nameDto
     * @return
     */
    Page findRules(NameDto nameDto, Org org);
    /**
     * 根据ID删除安全规章制度，同时删除安全规章制度下的所有目录
     */
    void deleteById(String id);
    /**
     * 新增安全规章制度文件
     * @param rule 安全规章制度文件对象
     * @param org 企业对象
     */
    void addRule(Rules rule, Org org);
}

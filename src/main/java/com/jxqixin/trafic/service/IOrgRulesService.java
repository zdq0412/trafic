package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgRules;
import org.springframework.data.domain.Page;

public interface IOrgRulesService extends ICommonService<OrgRules> {
    /**
     * 分页查找安全规章制度
     * @param nameDto
     * @param org
     * @return
     */
    Page findOrgRules(NameDto nameDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 发布通知
     * @param id
     */
    void publishRules(String id);
}

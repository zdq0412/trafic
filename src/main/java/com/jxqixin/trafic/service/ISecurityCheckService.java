package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.SecurityCheckDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SecurityCheck;
import org.springframework.data.domain.Page;

public interface ISecurityCheckService extends ICommonService<SecurityCheck> {
    /**
     * 分页查询模板信息
     * @return
     */
    Page findSecurityChecks(SecurityCheckDto securityCheckDto, Org org);
    /**
     * 根据ID删除模板，同时删除模板下的所有目录
     */
    void deleteById(String id);
    /**
     * 引入模板
     * @param templateId
     * @param org
     * @param currentUsername
     */
    SecurityCheck importTemplate(String templateId, Org org, String currentUsername);
}

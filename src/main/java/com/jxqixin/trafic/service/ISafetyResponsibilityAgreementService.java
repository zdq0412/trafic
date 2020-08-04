package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.SafetyResponsibilityAgreementDto;
import com.jxqixin.trafic.model.SafetyResponsibilityAgreement;
import org.springframework.data.domain.Page;

public interface ISafetyResponsibilityAgreementService extends ICommonService<SafetyResponsibilityAgreement> {
    /**
     * 分页查询信息
     * @param safetyResponsibilityAgreementDto
     * @return
     */
    Page findSafetyResponsibilityAgreements(SafetyResponsibilityAgreementDto safetyResponsibilityAgreementDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

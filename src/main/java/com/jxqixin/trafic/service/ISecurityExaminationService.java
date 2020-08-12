package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.SecurityExaminationDto;
import com.jxqixin.trafic.model.SecurityExamination;
import org.springframework.data.domain.Page;

public interface ISecurityExaminationService extends ICommonService<SecurityExamination> {
    /**
     * 分页查询信息
     * @param contractDto
     * @return
     */
    Page findSecurityExaminations(SecurityExaminationDto contractDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

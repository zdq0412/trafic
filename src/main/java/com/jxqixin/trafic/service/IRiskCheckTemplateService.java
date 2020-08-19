package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.RiskCheckTemplateDto;
import com.jxqixin.trafic.model.RiskCheckTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;
public interface IRiskCheckTemplateService extends ICommonService<RiskCheckTemplate> {
    /**
     * 分页查询信息
     * @param deviceCheckTemplateDto
     * @return
     */
    Page findRiskCheckTemplates(RiskCheckTemplateDto deviceCheckTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

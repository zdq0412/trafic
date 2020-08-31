package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.JobManagementAccountTemplateDto;
import com.jxqixin.trafic.model.JobManagementAccountTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;
public interface IJobManagementAccountTemplateService extends ICommonService<JobManagementAccountTemplate> {
    /**
     * 分页查询信息
     * @param deviceCheckTemplateDto
     * @return
     */
    Page findJobManagementAccountTemplates(JobManagementAccountTemplateDto deviceCheckTemplateDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

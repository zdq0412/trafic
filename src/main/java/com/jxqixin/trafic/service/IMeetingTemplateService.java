package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.MeetingTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IMeetingTemplateService extends ICommonService<MeetingTemplate> {
    /**
     * 分页查询模板信息
     * @param nameDto
     * @return
     */
    Page findMeetingTemplates(NameDto nameDto, String type, Org org);
    /**
     * 根据模板名称查找
     * @param name
     * @return
     */
    MeetingTemplate findByName(String name);
    /**
     * 根据ID删除模板，同时删除模板下的所有目录
     */
    void deleteById(String id);
}

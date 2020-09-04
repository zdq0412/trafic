package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.MeetingDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Meeting;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.User;
import org.springframework.data.domain.Page;

public interface IMeetingService extends ICommonService<Meeting> {
    /**
     * 分页查询
     * @return
     */
    Page findMeetings(MeetingDto meetingDto, Org org);
    /**
     * 根据ID删除
     */
    void deleteById(String id);
    /**
     * 引入模板
     * @param templateId
     * @param org
     */
    Meeting importTemplate(String templateId, Org org, String username);
}

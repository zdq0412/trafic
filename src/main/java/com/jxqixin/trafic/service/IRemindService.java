package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.RemindDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.Remind;
import org.springframework.data.domain.Page;

public interface IRemindService extends ICommonService<Remind> {
    /**
     * 分页查询信息
     * @param remindDto
     * @return
     */
    Page findReminds(RemindDto remindDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

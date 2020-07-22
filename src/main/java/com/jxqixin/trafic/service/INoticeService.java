package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Notice;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface INoticeService extends ICommonService<Notice> {
    /**
     * 分页查询企业发文通知信息
     * @param nameDto
     * @return
     */
    Page findNotices(NameDto nameDto, Org org);
    /**
     * 根据ID删除企业发文通知，同时删除企业发文通知下的所有目录
     */
    void deleteById(String id);
    /**
     * 新增企业发文通知文件
     * @param notice 企业发文通知文件对象
     * @param org 企业对象
     */
    void addNotice(Notice notice, Org org);
    /**
     * 根据法律法规ID查找
     * @param lawId
     * @return
     */
    List<Notice> findByLawId(String lawId);

    /**
     * 根据安全规章制度ID查找
     * @param rulesId
     * @return
     */
    List<Notice> findByRulesId(String rulesId);
}

package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.SecurityActivityDto;
import com.jxqixin.trafic.model.SecurityActivity;
import org.springframework.data.domain.Page;

public interface ISecurityActivityService extends ICommonService<SecurityActivity> {
    /**
     * 分页查询信息
     * @param securityActivityDto
     * @return
     */
    Page findSecurityActivitys(SecurityActivityDto securityActivityDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

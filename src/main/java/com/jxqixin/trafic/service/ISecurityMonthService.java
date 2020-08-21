package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.SecurityMonthDto;
import com.jxqixin.trafic.model.SecurityMonth;
import org.springframework.data.domain.Page;

public interface ISecurityMonthService extends ICommonService<SecurityMonth> {
    /**
     * 分页查询信息
     * @param securityMonthDto
     * @return
     */
    Page findSecurityMonths(SecurityMonthDto securityMonthDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

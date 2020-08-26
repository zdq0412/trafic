package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.StandardizationDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.Standardization;
import org.springframework.data.domain.Page;
public interface IStandardizationService extends ICommonService<Standardization> {
    /**
     * 分页查询信息
     * @return
     */
    Page findStandardizations(StandardizationDto standardizationDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}

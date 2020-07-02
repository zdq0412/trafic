package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IOrgService extends ICommonService<Org> {
    /**
     * 分页查找企业信息
     * @param nameDto
     * @return
     */
    Page findOrgs(NameDto nameDto);
    /**
     * 根据企业名称查找
     * @param name
     * @return
     */
    Org findByName(String name);
    /**
     * 根据ID删除企业
     * @param id
     */
    void deleteById(String id);
}

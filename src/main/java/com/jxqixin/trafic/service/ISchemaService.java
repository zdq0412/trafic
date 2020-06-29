package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Schema;
import org.springframework.data.domain.Page;

public interface ISchemaService extends ICommonService<Schema> {
    /**
     * 分页查询模式信息
     * @param nameDto
     * @return
     */
    Page findSchemas(NameDto nameDto);
    /**
     * 根据模式名称查找
     * @param name
     * @return
     */
    Schema findByName(String name);
    /**
     * 根据ID删除模式，同时删除模式下的所有目录
     */
    void deleteById(String id);
}

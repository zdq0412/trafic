package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.DangerGoodsCheckTemplate;
import org.springframework.data.domain.Page;

public interface IDangerGoodsCheckTemplateService extends ICommonService<DangerGoodsCheckTemplate> {
    /**
     * 分页查询模板信息
     * @param nameDto
     * @return
     */
    Page findDangerGoodsCheckTemplates(NameDto nameDto, String type);
    /**
     * 根据模板名称查找
     * @param name
     * @return
     */
    DangerGoodsCheckTemplate findByName(String name);
    /**
     * 根据ID删除模板，同时删除模板下的所有目录
     */
    void deleteById(String id);
}

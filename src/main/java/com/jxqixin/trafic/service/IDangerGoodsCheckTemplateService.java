package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.DangerGoodsCheckTemplateDto;
import com.jxqixin.trafic.model.DangerGoodsCheckDetail;
import com.jxqixin.trafic.model.DangerGoodsCheckTemplate;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDangerGoodsCheckTemplateService extends ICommonService<DangerGoodsCheckTemplate> {
    /**
     * 分页查询模板信息
     * @param dangerGoodsCheckTemplateDto
     * @return
     */
    Page findDangerGoodsCheckTemplates(DangerGoodsCheckTemplateDto dangerGoodsCheckTemplateDto, Org org);
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
    /**
     * 删除template下的详情，添加新的详情
     * @param id  template id
     * @param detailList 详情
     */
    void updateDetails(String id, List<DangerGoodsCheckDetail> detailList);
}

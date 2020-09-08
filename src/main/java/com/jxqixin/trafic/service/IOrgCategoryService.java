package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.OrgCategoryDto;
import com.jxqixin.trafic.model.OrgCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrgCategoryService extends ICommonService<OrgCategory> {
    /**
     * 根据企业类别名称查找
     * @param name
     * @return
     */
    OrgCategory findByName(String name);
    /**
     * 根据ID删除企业类别
     * @param id
     */
    void deleteById(String id);
    /**
     * 分页查找企业类别
     * @param nameDto
     * @return
     */
    Page findOrgCategorys(NameDto nameDto);

    /**
     * 修改状态
     * @param orgCategoryDto
     */
    void orgCategoryStatus(OrgCategoryDto orgCategoryDto);

    /**
     * 查找所有可用企业类别
     * @return
     */
    List<OrgCategory> queryAllOrgCategory();
}

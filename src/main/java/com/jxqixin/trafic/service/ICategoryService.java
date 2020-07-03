package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService extends ICommonService<Category> {
    /**
     * 分页查询类别信息
     * @param nameDto
     * @return
     */
    Page findCategorys(NameDto nameDto);
    /**
     * 根据类别名称查找
     * @param name
     * @return
     */
    Category findByName(String name);
    /**
     * 根据ID删除类别，同时删除类别下的所有目录
     */
    void deleteById(String id);
    /**
     * 根据类型查找类别
     * @param type
     * @return
     */
    List<Category> findAll(String type);
}

package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.CategoryDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

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
    /**
     * 导入类别
     * @param list
     */
    void importCategory(List<Category> list);

    /**
     * 修改类别状态：pause 停用  play 启用
     * @param categoryDto
     */
    void categoryStatus(CategoryDto categoryDto);
}

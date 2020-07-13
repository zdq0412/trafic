package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Department;
import org.springframework.data.domain.Page;

public interface IDepartmentService extends ICommonService<Department> {
    /**
     * 分页查询部门
     * @param nameDto
     * @return
     */
    Page findDepartments(NameDto nameDto);
    /**
     * 根据部门名称查找部门
     * @param name
     * @return
     */
    Department findByName(String name);
    /**
     * 根据id删除部门
     * @param id
     */
    void deleteById(String id);
}

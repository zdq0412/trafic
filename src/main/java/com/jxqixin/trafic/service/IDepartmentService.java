package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDepartmentService extends ICommonService<Department> {
    /**
     * 分页查询部门
     * @param nameDto
     * @return
     */
    Page findDepartments(NameDto nameDto,Org org);
    /**
     * 根据部门名称查找部门
     * @param name
     * @return
     */
    Department findByName(String name,Org org);
    /**
     * 根据id删除部门
     * @param id
     */
    void deleteById(String id);

    /**
     * 查找部门树
     * @return
     */
    List<Department> findTree(Org org);

    /**
     * 根据父部门ID查找至根ID
     * @param pid
     * @return
     */
    List<String> findParent(String pid);
}

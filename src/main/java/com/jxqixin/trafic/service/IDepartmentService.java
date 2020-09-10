package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.DepartmentDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDepartmentService extends ICommonService<Department> {
    /**
     * 分页查询部门
     * @param departmentDto
     * @return
     */
    Page findDepartments(DepartmentDto departmentDto, Org org);

    /**
     * 查找当前部门的父部门
     * @param id
     * @return
     */
    public List<String> findParentDepartments(String id);
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
    /**
     * 根据父部门ID查找子部门数
     * @param parentId
     * @return
     */
    Long queryCountByParentId(String parentId);
}

package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.EmployeeDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeService extends ICommonService<Employee> {
    /**
     * 根据员工ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 分页查询员工信息
     * @param nameDto
     * @return
     */
    Page findEmployees(NameDto nameDto,Org org);
    /**
     * 添加人员，同时添加对应用户
     * @param employeeDto
     */
    void addEmployee(EmployeeDto employeeDto, Org org);
    /**
     * 更新人员
     * @param employeeDto
     */
    void updateEmployee(EmployeeDto employeeDto,Org org);

    /**
     * 根据ID删除人员
     * @param id
     */
    void deleteEmployee(String id);
    /**
     * 根据当前登录用户名查找员工信息
     * @param username
     * @return
     */
    Employee findByUsername(String username);

    /**
     * 查询企业内所有员工
     * @param org
     * @return
     */
    List<Employee> findAllEmployees(Org org);
    /**
     * 查找管理层员工
     * @param org
     * @return
     */
    List<Employee> findManagementLayers(Org org);
}

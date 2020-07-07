package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Employee;
import org.springframework.data.domain.Page;
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
    Page findEmployees(NameDto nameDto);
}

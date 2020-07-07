package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 企业员工控制器
 */
@RestController
public class EmployeeController extends CommonController{
    @Autowired
    private IEmployeeService employeeService;
    /**
     * 查询所有企业员工
     * @return
     */
    @GetMapping("/employee/employees")
    public JsonResult<List<Employee>> queryAllEmployee(){
        List<Employee> list = employeeService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询企业员工
     * @param nameDto
     * @return
     */
    @GetMapping("/employee/employeesByPage")
    public ModelMap queryEmployees(NameDto nameDto){
        Page page = employeeService.findEmployees(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增企业员工
     * @param employee
     * @return
     */
    @PostMapping("/employee/employee")
    public JsonResult addEmployee(Employee employee){
        employeeService.addObj(employee);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑企业员工
     * @param employee
     * @return
     */
    @PutMapping("/employee/employee")
    public JsonResult updateEmployee(Employee employee){
        Employee savedEmployee = employeeService.queryObjById(employee.getId());
        savedEmployee.setName(employee.getName());
        savedEmployee.setNote(employee.getNote());
        employeeService.updateObj(savedEmployee);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除企业员工
     * @param id
     * @return
     */
    @DeleteMapping("/employee/employee/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        employeeService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

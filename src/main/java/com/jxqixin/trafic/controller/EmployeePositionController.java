package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.service.IEmployeePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
/**
 * 人员职位关联表
 */
@RestController
public class EmployeePositionController extends CommonController{
    @Autowired
    private IEmployeePositionService employeePositionService;
}

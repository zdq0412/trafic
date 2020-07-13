package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.DepartmentDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 部门控制器
 */
@RestController
public class DepartmentController extends CommonController{
    @Autowired
    private IDepartmentService departmentService;
    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/department/departments")
    public JsonResult<List<Department>> queryAllDepartment(){
        List<Department> list = departmentService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询部门
     * @param nameDto
     * @return
     */
    @GetMapping("/department/departmentsByPage")
    public ModelMap queryDepartments(NameDto nameDto){
        Page page = departmentService.findDepartments(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增部门
     * @param department
     * @return
     */
    @PostMapping("/department/department")
    public JsonResult addDepartment(Department department){
        JsonResult jsonResult = findByName(department.getName());
        if(jsonResult.getResult().getResultCode()==200){
            department.setId(UUID.randomUUID().toString());
            departmentService.addObj(department);
        }
        return jsonResult;
    }
    /**
     * 编辑部门
     * @param departmentDto
     * @return
     */
    @PutMapping("/department/department")
    public JsonResult updateDepartment(DepartmentDto departmentDto){
        Department s = departmentService.findByName(departmentDto.getName());

        if(s!=null && !s.getId().equals(departmentDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        Department savedDepartment = departmentService.queryObjById(departmentDto.getId());
        savedDepartment.setName(departmentDto.getName());
        departmentService.updateObj(savedDepartment);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据部门名称查找
     * @param name
     * @return
     */
    @GetMapping("/department/department/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        Department department = departmentService.findByName(name);
        if(department==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除部门
     * @param id
     * @return
     */
    @DeleteMapping("/department/department/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            departmentService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}

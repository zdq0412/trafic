package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.DepartmentDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IDepartmentService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private IUserService userService;
    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/department/departments")
    public JsonResult<List<Department>> queryAllDepartment(HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        List<Department> list = departmentService.findTree(user.getOrg());
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询部门
     * @param nameDto
     * @return
     */
    @GetMapping("/department/departmentsByPage")
    public ModelMap queryDepartments(NameDto nameDto,HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Page page = departmentService.findDepartments(nameDto,user.getOrg());
        return pageModelMap(page);
    }
    /**
     * 新增部门
     * @param departmentDto
     * @return
     */
    @PostMapping("/department/department")
    public JsonResult addDepartment(DepartmentDto departmentDto,HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        JsonResult jsonResult = findByName(departmentDto.getName(),request);
        if(jsonResult.getResult().getResultCode()==200){
            Department department = new Department();
            BeanUtils.copyProperties(departmentDto,department);
            if(!StringUtils.isEmpty(departmentDto.getPid())){
                Department parent = new Department();
                parent.setId(departmentDto.getPid());

                department.setParent(parent);
            }
            department.setOrg(user.getOrg());
            departmentService.addObj(department);
        }
        return jsonResult;
    }

    /**
     * 根据pid查找至根ID
     * @param id
     * @return
     */
    @GetMapping("/department/findParent")
    public List<String> findParent(String id){
        return departmentService.findParent(id);
    }
    /**
     * 编辑部门
     * @param departmentDto
     * @return
     */
    @PutMapping("/department/department")
    public JsonResult updateDepartment(DepartmentDto departmentDto,HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Department s = departmentService.findByName(departmentDto.getName(),user.getOrg());

        if(s!=null && !s.getId().equals(departmentDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        Department savedDepartment = departmentService.queryObjById(departmentDto.getId());
        savedDepartment.setName(departmentDto.getName());
        savedDepartment.setTel(departmentDto.getTel());
        savedDepartment.setBusiness(departmentDto.getBusiness());
        if(!StringUtils.isEmpty(departmentDto.getPid())){
            if(savedDepartment.getParent()!=null && !departmentDto.getPid().equals(savedDepartment.getParent().getId())){
                Department parent = new Department();
                parent.setId(departmentDto.getPid());

                savedDepartment.setParent(parent);
            }
        }
        departmentService.updateObj(savedDepartment);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据部门名称查找
     * @param name
     * @return
     */
    @GetMapping("/department/department/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name,HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Department department = departmentService.findByName(name,user.getOrg());
        if(department==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            Result result = Result.FAIL;
            result.setMessage("部门名称已存在!");
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

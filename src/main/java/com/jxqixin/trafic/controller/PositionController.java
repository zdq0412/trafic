package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DeptPositionDto;
import com.jxqixin.trafic.dto.PositionDto;
import com.jxqixin.trafic.model.Department;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Position;
import com.jxqixin.trafic.service.IEmployeePositionService;
import com.jxqixin.trafic.service.IPositionService;
import com.jxqixin.trafic.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * 职位控制器
 */
@RestController
public class PositionController extends CommonController{
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IEmployeePositionService employeePositionService;
    /**
     * 分页查询职位
     * @param positionDto
     * @return
     */
    @GetMapping("/position/positionsByPage")
    public ModelMap queryPositions(PositionDto positionDto){
        Page page = positionService.findPositions(positionDto);
        return pageModelMap(page);
    }
    /**
     *根据部门ID查找职位
     * @param departmentId
     * @return
     */
    @GetMapping("/position/positions")
    public List<Position> queryPositions(String departmentId){
       return positionService.findByDepartmentId(departmentId);
    }
    /**
     * 新增职位
     * @param positionDto
     * @return
     */
    @PostMapping("/position/position")
    public JsonResult addPosition(PositionDto positionDto){
        Position position = new Position();
        BeanUtils.copyProperties(positionDto,position);
        if(!StringUtils.isEmpty(positionDto.getDepartmentId())){
            Department department = new Department();
            department.setId(positionDto.getDepartmentId());

            position.setDepartment(department);
        }
        positionService.addObj(position);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑职位
     * @param positionDto
     * @return
     */
    @PostMapping("/position/updatePosition")
    public JsonResult updatePosition(PositionDto positionDto){
        Position savedPosition = positionService.queryObjById(positionDto.getId());
        if(!StringUtils.isEmpty(positionDto.getDepartmentId())){
            Department department = savedPosition.getDepartment();
            if(!department.getId().equals(positionDto.getDepartmentId())){
                Department dept = new Department();
                dept.setId(positionDto.getDepartmentId());

                savedPosition.setDepartment(dept);
            }
        }
        savedPosition.setName(positionDto.getName());
        savedPosition.setNote(positionDto.getNote());
        savedPosition.setManagementLayer(positionDto.isManagementLayer());
        positionService.updateObj(savedPosition);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除职位
     * @param id
     * @return
     */
    @DeleteMapping("/position/position/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            positionService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 根据人员ID查找
     * @param employeeId
     * @return
     */
    @GetMapping("/positions/employeePositions")
    public ModelMap findEmployeePositions(String employeeId){
        List<Position> positions = positionService.findAll();
        List<DeptPositionDto> deptPositionDtos = new ArrayList<>();
        Set<Department> departments = new HashSet<>();
        ModelMap modelMap = new ModelMap();
        if(!CollectionUtils.isEmpty(positions)){
            positions.forEach(position ->departments.add(position.getDepartment()));

            if(!CollectionUtils.isEmpty(departments)){
                departments.forEach(department -> {
                    DeptPositionDto deptPositionDto = new DeptPositionDto();
                    deptPositionDto.setId(department.getId());
                    deptPositionDto.setName(department.getName());
                    deptPositionDto.setType("dept");
                    List<DeptPositionDto> children = new ArrayList<>();
                    positions.forEach(position -> {
                        Department dept = position.getDepartment();
                        if(dept!=null && department.getId().equals(dept.getId())){
                            DeptPositionDto dpdto = new DeptPositionDto();
                            dpdto.setId(position.getId());
                            dpdto.setName(position.getName());
                            dpdto.setType("position");
                            children.add(dpdto);
                        }
                    });
                    deptPositionDto.setChildren(children);
                    deptPositionDtos.add(deptPositionDto);
                });
            }
            List<String> positionsIdList = employeePositionService.findIdsByEmployeeId(employeeId);
            modelMap.addAttribute("positions",deptPositionDtos);
            modelMap.addAttribute("positionIds",positionsIdList);
        }else{
            modelMap.addAttribute("positions",deptPositionDtos);
            modelMap.addAttribute("positionIds",new ArrayList<>());
        }
        return modelMap;
    }

    /**
     * 修改人员职位
     * @param positionsId
     * @param employeeId
     * @return
     */
    @PutMapping("/position/employeePositions")
    public JsonResult assignFunctions2OrgCategory(String positionsId,String employeeId){
        String[] positionIdArray = StringUtil.handleIds(positionsId);
        positionService.assign2Employee(positionIdArray,employeeId);
        return new JsonResult(Result.SUCCESS);
    }
}

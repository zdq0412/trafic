package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.RoleDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.model.Role;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IRoleService;
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
 * 角色控制器
 */
@RestController
public class RoleController extends CommonController{
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    /**
     * 查询所有角色
     * @return
     */
    @GetMapping("/role/roles")
    public JsonResult<List<Role>> queryAllRole(HttpServletRequest request){
        String username = getCurrentUsername(request);
        User user = userService.queryUserByUsername(username);
        List<Role> list = roleService.findAllRoles(user.getOrg()==null?null:user.getOrg().getId());
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询角色
     * @param nameDto
     * @return
     */
    @GetMapping("/role/rolesByPage")
    public ModelMap queryRoles(NameDto nameDto,HttpServletRequest request){
        String username = getCurrentUsername(request);
        User user = userService.queryUserByUsername(username);
        Page page = roleService.findRoles(nameDto,user.getOrg()==null?null:user.getOrg().getId());
        return pageModelMap(page);
    }
    /**
     * 新增角色
     * @param roleDto
     * @return
     */
    @PostMapping("/role/role")
    public JsonResult addRole(RoleDto roleDto, HttpServletRequest request){
        JsonResult jsonResult = findByName(roleDto.getName(),request);
        Role role = new Role();
        BeanUtils.copyProperties(roleDto,role);
        if(jsonResult.getResult().getResultCode()==200){
            role.setId(UUID.randomUUID().toString());
            role.setCreateDate(new Date());

            if(!StringUtils.isEmpty(roleDto.getOrgCategoryId())){
                OrgCategory orgCategory = new OrgCategory();
                orgCategory.setId(roleDto.getOrgCategoryId());

                role.setOrgCategory(orgCategory);
            }
            role.setAllowedDelete(true);
            role.setOrg(userService.queryUserByUsername(getCurrentUsername(request)).getOrg());
            roleService.addObj(role);
        }
        return jsonResult;
    }
    /**
     * 编辑角色
     * @param roleDto
     * @return
     */
    @PutMapping("/role/role")
    public JsonResult updateRole(RoleDto roleDto, HttpServletRequest request){
        String  username = getCurrentUsername(request);
        User user = userService.queryUserByUsername(username);
        Role s = roleService.findByNameAndOrgId(roleDto.getName(),user.getOrg()==null?null:user.getOrg().getId());

        if(s!=null && !s.getId().equals(roleDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        Role savedRole = roleService.queryObjById(roleDto.getId());
        savedRole.setName(roleDto.getName());
        savedRole.setNote(roleDto.getNote());
        if(!StringUtils.isEmpty(roleDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(roleDto.getOrgCategoryId());
            savedRole.setOrgCategory(orgCategory);
        }
        roleService.updateObj(savedRole);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据角色名称查找
     * @param name
     * @return
     */
    @GetMapping("/role/role/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name,HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Role role = roleService.findByNameAndOrgId(name,user.getOrg()==null?null:user.getOrg().getId());
        if(role==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/role/role/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            roleService.deleteById(id);
        }catch (RuntimeException re){
            Result result = Result.FAIL;
            result.setMessage(re.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}

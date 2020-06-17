package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.dto.RoleDto;
import com.jxqixin.trafic.model.Role;
import com.jxqixin.trafic.service.IRoleService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.Date;
import java.util.List;
/**
 * Created by Administrator on 2019/10/6.
 */
@RestController
@RequestMapping("role")
public class RoleController extends CommonController{
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    /**
     * 根据条件查找角色信息
     * @param roleDto
     * @return
     */
    @GetMapping("queryRoles")
    public ModelMap queryRoles(RoleDto roleDto) {
        Page<Role> page = roleService.findByPage(roleDto);
        return pageModelMap(page);
    }

    /**
     * 查询所有角色
     * @return
     */
    @RequestMapping("queryAll")
    public List<Role> queryAll(){
        return roleService.findAll();
    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("deleteByIds")
    public ModelMap deleteByIds(String ids){
        if(StringUtils.isEmpty(ids)){
            return failureModelMap("没有要删除的角色!");
        }
        ids = StringUtils.delete(ids,"]");
        ids = StringUtils.delete(ids,"[");
        ids = StringUtils.delete(ids,"\"");
        if(StringUtils.isEmpty(ids)){
            return failureModelMap("没有要删除的角色!");
        }
        try {
            roleService.deleteBatch(ids.split(","));
        }catch (RuntimeException e){
            failureModelMap(e.getMessage());
        }
        return successModelMap("删除成功!");
    }
    /**
     * 根据id删除简历
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public ModelMap deleteById(@PathVariable String id) {
        try {
            //根据id查找角色是否被使用
           Integer count =  userService.findCountByRoleId(id);
           if(count!=null &&count>0){
               return failureModelMap("该角色已被使用，不允许删除!");
           }
            roleService.deleteById(id);
        }catch (RuntimeException e){
            return failureModelMap(e.getMessage());
        }
        return successModelMap("删除成功!");
    }

    /**
     * 验证角色名是否可用
     * @param rolename
     * @return
     */
    @GetMapping("checkRolename/{rolename}")
    public ModelMap checkRolename(@PathVariable String rolename){
        Role role = roleService.queryRoleByRolename(rolename);
        if(role!=null){
            return failureModelMap("");
        }else{
            return successModelMap("");
        }
    }
    /**
     * 添加角色
     * @param role
     * @return
     */
    @GetMapping("addRole")
    public ModelMap addRole(Role role, Principal principal){
        Role u = roleService.queryRoleByRolename(role.getName());
        if(u!=null){
            return failureModelMap("角色添加失败: "+ role.getName()+"已被使用!");
        }
        role.setCreateDate(new Date());
        roleService.addObj(role);
        return successModelMap("新增角色成功!");
    }
    /**
     * 修改角色
     * @param role
     * @return
     */
    @GetMapping("modifyRole")
    public ModelMap modifyRole(Role role){
        Role u = roleService.queryRoleByRolename(role.getName());
        u.setNote(role.getNote());
        roleService.addObj(u);
        return successModelMap("修改角色成功!");
    }

    /**
     * 根据角色名查找
     * @param rolename
     * @return
     */
    @RequestMapping("queryByRolename/{rolename}")
    public Role queryByRolename(@PathVariable String rolename){
        return roleService.queryRoleByRolename(rolename);
    }

    /**
     * 为角色赋权限
     * @param roleName
     * @param powerUrls
     * @return
     */
    @RequestMapping("asignPowers")
    public ModelMap asignPowers(String roleName,String powerUrls){
        if(StringUtils.isEmpty(roleName) || StringUtils.isEmpty(powerUrls)){
            return failureModelMap("角色或权限为空!");
        }
        powerUrls = powerUrls.replace("[","");
        powerUrls = powerUrls.replace("]","");
        powerUrls = powerUrls.replace("\"","");
        roleService.asignPowers(roleName,powerUrls.split(","));
        return successModelMap("");
    }
}

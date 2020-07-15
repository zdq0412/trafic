package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.Role;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
/**
 * 用户管理
 */
@RestController
public class UserController extends CommonController{
    @Autowired
    private IUserService userService;
    @Value("${defaultPassword}")
    private String defaultPassword;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param username
     * @return
     */
    @PostMapping("/user/modifyPassword")
    public JsonResult modifyPassword(String oldPassword, String newPassword, String username){
        User user = userService.queryUserByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            Result result = Result.FAIL;
            result.setMessage("原密码错误!");
            return new JsonResult(result);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateObj(user);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 密码重置
     * @param username
     * @return
     */
    @GetMapping("/user/resetPassword")
    public JsonResult resetPassword(String username){
        User user = userService.queryUserByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(defaultPassword));
        userService.updateObj(user);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 禁用或启用用户
     * @param id
     * @return
     */
    @GetMapping("/user/enableOrDisableUser")
    public JsonResult enableOrDisableUser(String id,String status){
        User user = userService.queryObjById(id);
       switch (status){
           //启用
           case "1":{
               user.setStatus(User.OK);
               break;
           }
           //禁用
           case "0":{
               user.setStatus(User.DISABLED);
               break;
           }
       }

       userService.updateObj(user);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 分页查询用户
     * @param nameDto
     * @return
     */
    @GetMapping("/user/usersByPage")
    public ModelMap queryUsers(NameDto nameDto, HttpServletRequest request){
        String token = request.getHeader("token");
        String currentUsername =(String) redisUtil.get(token);
        Page page = userService.findUsers(nameDto,currentUsername);
        return pageModelMap(page);
    }
    /**
     * 新增用户
     * @param userDto
     * @return
     */
    @PostMapping("/user/user")
    public JsonResult addUser(UserDto userDto,HttpServletRequest request){
        JsonResult jsonResult = findByName(userDto.getUsername(),request);
        if(jsonResult.getResult().getResultCode()==200){
            User user = new User();
            BeanUtils.copyProperties(userDto,user);

            if(!StringUtils.isEmpty(userDto.getOrgId())){
                Org org = new Org();
                org.setId(userDto.getOrgId());
                user.setOrg(org);
            }
            if(!StringUtils.isEmpty(userDto.getRoleId())){
                Role role = new Role();
                role.setId(userDto.getRoleId());
                user.setRole(role);
            }

            user.setId(UUID.randomUUID().toString());
            user.setCreateDate(new Date());
            user.setAllowedDelete(true);
            user.setPassword(new BCryptPasswordEncoder().encode(defaultPassword));
            userService.addObj(user);
        }
        return jsonResult;
    }
    /**
     * 编辑用户
     * @param userDto
     * @return
     */
    @PutMapping("/user/user")
    public JsonResult updateUser(UserDto userDto){
        User s = userService.queryUserByUsername(userDto.getUsername());
        if(s!=null && !s.getId().equals(userDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        User savedUser = userService.queryObjById(userDto.getId());
        if(!StringUtils.isEmpty(userDto.getOrgId())){
            Org org = new Org();
            org.setId(userDto.getOrgId());
            savedUser.setOrg(org);
        }
        if(!StringUtils.isEmpty(userDto.getRoleId())){
            Role role = new Role();
            role.setId(userDto.getRoleId());
            savedUser.setRole(role);
        }
        savedUser.setUsername(userDto.getUsername());
        savedUser.setRealname(userDto.getRealname());
        savedUser.setTel(userDto.getTel());
        userService.updateObj(savedUser);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据用户名称查找
     * @param username
     * @return
     */
    @GetMapping("/user/user/{username}")
    public JsonResult findByName(@PathVariable(name="username") String username,HttpServletRequest request){
        User currentUser = userService.queryUserByUsername(getCurrentUsername(request));
        User user = userService.queryUserByUsernameAndOrgId(username,currentUser.getOrg()==null?null:currentUser.getOrg().getId());
        if(user==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/user/user/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            userService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
            return new JsonResult(Result.FAIL);
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
     * @param user
     * @return
     */
    @PostMapping("/user/user")
    public JsonResult addUser(User user){
        JsonResult jsonResult = findByName(user.getUsername());
        if(jsonResult.getResult().getResultCode()==200){
            user.setId(UUID.randomUUID().toString());
            user.setCreateDate(new Date());
            userService.addObj(user);
        }
        return jsonResult;
    }
    /**
     * 编辑用户
     * @param user
     * @return
     */
    @PutMapping("/user/user")
    public JsonResult updateUser(User user){
        User s = userService.queryUserByUsername(user.getUsername());
        if(s!=null && !s.getId().equals(user.getId())){
            return new JsonResult(Result.FAIL);
        }
        User savedUser = userService.queryObjById(user.getId());
        user.setCreateDate(savedUser.getCreateDate());
        user.setPassword(savedUser.getPassword());
        user.setCreator(savedUser.getCreator());
        user.setStatus(savedUser.getStatus());
        userService.updateObj(user);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据用户名称查找
     * @param username
     * @return
     */
    @GetMapping("/user/user/{username}")
    public JsonResult findByName(@PathVariable(name="username") String username){
        User user = userService.queryUserByUsername(username);
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
        userService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

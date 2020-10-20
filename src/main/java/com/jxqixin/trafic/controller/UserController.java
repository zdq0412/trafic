package com.jxqixin.trafic.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IAreaManagerService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
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
    private IAreaManagerService areaManagerService;
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
     * 登录验证码
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping("/verifyCode")
    public void  verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        // 定义图形验证码的长、宽、验证码位数、线性数量
        //还有更多功能自行研究，这里只做简单实现
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(125, 50,4,5);
        //保存到session
        session.setAttribute("code", lineCaptcha.getCode());
        //响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //将生成的验证码图片通过流的方式返回给前端
        ImageIO.write(lineCaptcha.getImage(), "JPEG", outputStream);
    }
    /**
     * 查询用户是否具有企业信息
     * @param request
     * @return
     */
    @GetMapping("/user/haveOrg")
    public JsonResult haveOrg(HttpServletRequest request){
        return new JsonResult(Result.SUCCESS,getOrg(request));
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
        String newPass = defaultPassword;
        if(!StringUtils.isEmpty(user.getTel())){
            newPass = user.getTel().substring(user.getTel().length()-6);
        }
        user.setPassword(passwordEncoder.encode(newPass));
        userService.updateObj(user);
        return new JsonResult(Result.SUCCESS,newPass);
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
     * @param userDto
     * @return
     */
    @GetMapping("/user/usersByPage")
    public ModelMap queryUsers(UserDto userDto, HttpServletRequest request){
        Page page = userService.findUsers(userDto,getOrg(request));
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
            }else{
                Org org = getOrg(request);
                if(org!=null){
                    user.setOrg(getOrg(request));
                }else{
                    user.setOrg(null);
                }
            }
            if(!StringUtils.isEmpty(userDto.getRoleId())){
                Role role = new Role();
                role.setId(userDto.getRoleId());
                user.setRole(role);
            }

            user.setId(UUID.randomUUID().toString());
            user.setCreateDate(new Date());
            user.setAllowedDelete(true);
            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getTel().substring(userDto.getTel().length()-6)));
            userService.addObj(user);
        }
        return jsonResult;
    }
    /**
     * 编辑用户
     * @param userDto
     * @return
     */
    @PostMapping("/user/updateUser")
    public JsonResult updateUser(UserDto userDto,HttpServletRequest request){
        User s = userService.queryUserByUsername(userDto.getUsername());
        if(s!=null && !s.getId().equals(userDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        User savedUser = userService.queryObjById(userDto.getId());
        if(!StringUtils.isEmpty(userDto.getOrgId())){
            Org org = new Org();
            org.setId(userDto.getOrgId());
            savedUser.setOrg(org);
        }else{
            Org org = getOrg(request);
            if(org!=null){
                savedUser.setOrg(getOrg(request));
            }else{
                savedUser.setOrg(null);
            }
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
     * 根据用户名称查找,用户登录成功后查找用户信息
     * @param username
     * @return
     */
    @GetMapping("/user/userOrAreaManager/{username}")
    public JsonResult findByUsername(@PathVariable(name="username") String username,HttpServletRequest request){
        Org org = getOrg(request);
        User user = userService.queryUserByUsernameAndOrgId(username,org==null?null:org.getId());
        if(user!=null){
            user.setPassword(null);
            return new JsonResult(Result.SUCCESS,user);
        }else{
            AreaManager areaManager = areaManagerService.findByUsername(username);
            areaManager.setPassword(null);
            return new JsonResult(Result.SUCCESS,areaManager);
        }
    }
    /**
     * 上传头像
     * @return
     */
    @PostMapping("/user/photo")
    public JsonResult uploadPhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        AreaManager areaManager = null;
        if(user==null){
            areaManager = areaManagerService.findByUsername(getCurrentUsername(request));
        }
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = "photo/" + user!=null?user.getUsername():areaManager.getUsername();
            File savedFile = upload(dir,file);
            if(savedFile!=null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(user!=null){
                if(!StringUtils.isEmpty(user.getRealpath())){
                    FileUtil.deleteFile(user.getRealpath());
                }
                user.setRealpath(savedFile.getAbsolutePath());
                user.setPhoto(urlMapping);
                userService.updateObj(user);
            }else{
                if(!StringUtils.isEmpty(areaManager.getRealpath())){
                    FileUtil.deleteFile(areaManager.getRealpath());
                }
                areaManager.setRealpath(savedFile.getAbsolutePath());
                areaManager.setPhoto(urlMapping);
                areaManagerService.updateObj(areaManager);
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result,urlMapping);
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

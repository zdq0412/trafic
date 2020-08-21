package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityActivityDto;
import com.jxqixin.trafic.model.SecurityActivity;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.ISecurityActivityService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 各种安全生产活动控制器
 */
@RestController
public class SecurityActivityController extends CommonController{
    @Autowired
    private ISecurityActivityService securityActivityService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param securityActivityDto
     * @return
     */
    @GetMapping("/securityActivity/securityActivitysByPage")
    public ModelMap querySecurityActivitys(SecurityActivityDto securityActivityDto){
        Page page = securityActivityService.findSecurityActivitys(securityActivityDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityActivityDto
     * @return
     */
    @PostMapping("/securityActivity/securityActivity")
    public JsonResult addSecurityActivity(SecurityActivityDto securityActivityDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        SecurityActivity savedSecurityActivity = new SecurityActivity();
        BeanUtils.copyProperties(securityActivityDto,savedSecurityActivity);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "securityActivity";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityActivity.setUrl(urlMapping);
            savedSecurityActivity.setRealPath(savedFile.getAbsolutePath());
            savedSecurityActivity.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedSecurityActivity.setCreateDate(new Date());
        savedSecurityActivity.setOrg(getOrg(request));
        savedSecurityActivity.setCreator(getCurrentUsername(request));
        securityActivityService.addObj(savedSecurityActivity);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityActivityDto
     * @return
     */
    @PostMapping("/securityActivity/updateSecurityActivity")
    public JsonResult updateSecurityActivity(SecurityActivityDto securityActivityDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        SecurityActivity savedSecurityActivity = securityActivityService.queryObjById(securityActivityDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "securityActivity";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedSecurityActivity.getRealPath())){
                FileUtil.deleteFile(savedSecurityActivity.getRealPath());
            }
            savedSecurityActivity.setUrl(urlMapping);
            savedSecurityActivity.setRealPath(savedFile.getAbsolutePath());
            savedSecurityActivity.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedSecurityActivity.setName(securityActivityDto.getName());
        savedSecurityActivity.setNote(securityActivityDto.getNote());
        securityActivityService.updateObj(savedSecurityActivity);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityActivityDto
     * @return
     */
    @PostMapping("/securityActivity/updateSecurityActivityNoFile")
    public JsonResult updateSecurityActivityNoFile(SecurityActivityDto securityActivityDto, HttpServletRequest request){
        SecurityActivity savedSecurityActivity = securityActivityService.queryObjById(securityActivityDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        savedSecurityActivity.setName(securityActivityDto.getName());
        savedSecurityActivity.setNote(securityActivityDto.getNote());
        securityActivityService.updateObj(savedSecurityActivity);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityActivity/securityActivity/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityActivityService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

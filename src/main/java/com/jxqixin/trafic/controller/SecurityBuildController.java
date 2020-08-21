package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityBuildDto;
import com.jxqixin.trafic.model.SecurityBuild;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.ISecurityBuildService;
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
 * 安全文化建设控制器
 */
@RestController
public class SecurityBuildController extends CommonController{
    @Autowired
    private ISecurityBuildService securityBuildService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param securityBuildDto
     * @return
     */
    @GetMapping("/securityBuild/securityBuildsByPage")
    public ModelMap querySecurityBuilds(SecurityBuildDto securityBuildDto){
        Page page = securityBuildService.findSecurityBuilds(securityBuildDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityBuildDto
     * @return
     */
    @PostMapping("/securityBuild/securityBuild")
    public JsonResult addSecurityBuild(SecurityBuildDto securityBuildDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        SecurityBuild savedSecurityBuild = new SecurityBuild();
        BeanUtils.copyProperties(securityBuildDto,savedSecurityBuild);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "securityBuild";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityBuild.setUrl(urlMapping);
            savedSecurityBuild.setRealPath(savedFile.getAbsolutePath());
            savedSecurityBuild.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedSecurityBuild.setCreateDate(new Date());
        savedSecurityBuild.setOrg(getOrg(request));
        savedSecurityBuild.setCreator(getCurrentUsername(request));
        securityBuildService.addObj(savedSecurityBuild);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityBuildDto
     * @return
     */
    @PostMapping("/securityBuild/updateSecurityBuild")
    public JsonResult updateSecurityBuild(SecurityBuildDto securityBuildDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        SecurityBuild savedSecurityBuild = securityBuildService.queryObjById(securityBuildDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "securityBuild";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedSecurityBuild.getRealPath())){
                FileUtil.deleteFile(savedSecurityBuild.getRealPath());
            }
            savedSecurityBuild.setUrl(urlMapping);
            savedSecurityBuild.setRealPath(savedFile.getAbsolutePath());
            savedSecurityBuild.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedSecurityBuild.setName(securityBuildDto.getName());
        savedSecurityBuild.setNote(securityBuildDto.getNote());
        securityBuildService.updateObj(savedSecurityBuild);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityBuildDto
     * @return
     */
    @PostMapping("/securityBuild/updateSecurityBuildNoFile")
    public JsonResult updateSecurityBuildNoFile(SecurityBuildDto securityBuildDto, HttpServletRequest request){
        SecurityBuild savedSecurityBuild = securityBuildService.queryObjById(securityBuildDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        savedSecurityBuild.setName(securityBuildDto.getName());
        savedSecurityBuild.setNote(securityBuildDto.getNote());
        securityBuildService.updateObj(savedSecurityBuild);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityBuild/securityBuild/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityBuildService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

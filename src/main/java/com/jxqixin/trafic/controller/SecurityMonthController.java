package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityMonthDto;
import com.jxqixin.trafic.model.SecurityMonth;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.ISecurityMonthService;
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
 * 安全生产月控制器
 */
@RestController
public class SecurityMonthController extends CommonController{
    @Autowired
    private ISecurityMonthService securityMonthService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param securityMonthDto
     * @return
     */
    @GetMapping("/securityMonth/securityMonthsByPage")
    public ModelMap querySecurityMonths(SecurityMonthDto securityMonthDto,HttpServletRequest request){
        Page page = securityMonthService.findSecurityMonths(securityMonthDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityMonthDto
     * @return
     */
    @PostMapping("/securityMonth/securityMonth")
    public JsonResult addSecurityMonth(SecurityMonthDto securityMonthDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        SecurityMonth savedSecurityMonth = new SecurityMonth();
        BeanUtils.copyProperties(securityMonthDto,savedSecurityMonth);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "securityMonth";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityMonth.setUrl(urlMapping);
            savedSecurityMonth.setRealPath(savedFile.getAbsolutePath());
            savedSecurityMonth.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedSecurityMonth.setCreateDate(new Date());
        savedSecurityMonth.setOrg(getOrg(request));
        savedSecurityMonth.setCreator(getCurrentUsername(request));
        securityMonthService.addObj(savedSecurityMonth);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityMonthDto
     * @return
     */
    @PostMapping("/securityMonth/updateSecurityMonth")
    public JsonResult updateSecurityMonth(SecurityMonthDto securityMonthDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        SecurityMonth savedSecurityMonth = securityMonthService.queryObjById(securityMonthDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "securityMonth";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedSecurityMonth.getRealPath())){
                FileUtil.deleteFile(savedSecurityMonth.getRealPath());
            }
            savedSecurityMonth.setUrl(urlMapping);
            savedSecurityMonth.setRealPath(savedFile.getAbsolutePath());
            savedSecurityMonth.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedSecurityMonth.setName(securityMonthDto.getName());
        savedSecurityMonth.setNote(securityMonthDto.getNote());
        securityMonthService.updateObj(savedSecurityMonth);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityMonthDto
     * @return
     */
    @PostMapping("/securityMonth/updateSecurityMonthNoFile")
    public JsonResult updateSecurityMonthNoFile(SecurityMonthDto securityMonthDto, HttpServletRequest request){
        SecurityMonth savedSecurityMonth = securityMonthService.queryObjById(securityMonthDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        savedSecurityMonth.setName(securityMonthDto.getName());
        savedSecurityMonth.setNote(securityMonthDto.getNote());
        securityMonthService.updateObj(savedSecurityMonth);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityMonth/securityMonth/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityMonthService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

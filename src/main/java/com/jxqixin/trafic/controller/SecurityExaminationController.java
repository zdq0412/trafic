package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityExaminationDto;
import com.jxqixin.trafic.model.SecurityExamination;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.ISecurityExaminationService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 安全责任考核控制器
 */
@RestController
public class SecurityExaminationController extends CommonController{
    @Autowired
    private ISecurityExaminationService securityExaminationService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param securityExaminationDto
     * @return
     */
    @GetMapping("/securityExamination/securityExaminationsByPage")
    public ModelMap querySecurityExaminations(SecurityExaminationDto securityExaminationDto){
        Page page = securityExaminationService.findSecurityExaminations(securityExaminationDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityExaminationDto
     * @return
     */
    @PostMapping("/securityExamination/securityExamination")
    public JsonResult addSecurityExamination(SecurityExaminationDto securityExaminationDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        SecurityExamination savedSecurityExamination = new SecurityExamination();
        BeanUtils.copyProperties(securityExaminationDto,savedSecurityExamination);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityExamination.setUrl(urlMapping);
            savedSecurityExamination.setRealPath(savedFile.getAbsolutePath());
            savedSecurityExamination.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedSecurityExamination.setCreateDate(new Date());
        if(!StringUtils.isEmpty(securityExaminationDto.getBeginDate())){
            try {
                savedSecurityExamination.setBeginDate(format.parse(securityExaminationDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityExamination.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(securityExaminationDto.getEndDate())){
            try {
                savedSecurityExamination.setEndDate(format.parse(securityExaminationDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityExamination.setEndDate(null);
            }
        }
        savedSecurityExamination.setOrg(getOrg(request));
        savedSecurityExamination.setCreator(getCurrentUsername(request));
        securityExaminationService.addObj(savedSecurityExamination);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityExaminationDto
     * @return
     */
    @PostMapping("/securityExamination/updateSecurityExamination")
    public JsonResult updateSecurityExamination(SecurityExaminationDto securityExaminationDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        SecurityExamination savedSecurityExamination = securityExaminationService.queryObjById(securityExaminationDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedSecurityExamination.getRealPath())){
                FileUtil.deleteFile(savedSecurityExamination.getRealPath());
            }
            savedSecurityExamination.setUrl(urlMapping);
            savedSecurityExamination.setRealPath(savedFile.getAbsolutePath());
            savedSecurityExamination.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityExaminationDto.getBeginDate())){
            try {
                savedSecurityExamination.setBeginDate(format.parse(securityExaminationDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityExamination.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(securityExaminationDto.getEndDate())){
            try {
                savedSecurityExamination.setEndDate(format.parse(securityExaminationDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityExamination.setEndDate(null);
            }
        }
        savedSecurityExamination.setName(securityExaminationDto.getName());
        savedSecurityExamination.setNote(securityExaminationDto.getNote());
        securityExaminationService.updateObj(savedSecurityExamination);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityExaminationDto
     * @return
     */
    @PostMapping("/securityExamination/updateSecurityExaminationNoFile")
    public JsonResult updateSecurityExaminationNoFile(SecurityExaminationDto securityExaminationDto, HttpServletRequest request){
        SecurityExamination savedSecurityExamination = securityExaminationService.queryObjById(securityExaminationDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(securityExaminationDto.getBeginDate())){
            try {
                savedSecurityExamination.setBeginDate(format.parse(securityExaminationDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityExamination.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(securityExaminationDto.getEndDate())){
            try {
                savedSecurityExamination.setEndDate(format.parse(securityExaminationDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityExamination.setEndDate(null);
            }
        }
        savedSecurityExamination.setName(securityExaminationDto.getName());
        savedSecurityExamination.setNote(securityExaminationDto.getNote());
        securityExaminationService.updateObj(savedSecurityExamination);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityExamination/securityExamination/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityExaminationService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

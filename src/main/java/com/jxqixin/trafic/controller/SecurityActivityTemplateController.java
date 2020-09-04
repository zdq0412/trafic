package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityActivityTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.ISecurityActivityTemplateService;
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
 * 其他安全活动模板控制器
 */
@RestController
public class SecurityActivityTemplateController extends CommonController{
    @Autowired
    private ISecurityActivityTemplateService securityActivityTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param securityActivityTemplateDto
     * @return
     */
    @GetMapping("/securityActivityTemplate/securityActivityTemplatesByPage")
    public ModelMap querySecurityActivityTemplates(SecurityActivityTemplateDto securityActivityTemplateDto,HttpServletRequest request){
        Page page = securityActivityTemplateService.findSecurityActivityTemplates(securityActivityTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityActivityTemplateDto
     * @return
     */
    @PostMapping("/securityActivityTemplate/securityActivityTemplate")
    public JsonResult addSecurityActivityTemplate(SecurityActivityTemplateDto securityActivityTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityActivityTemplate savedSecurityActivityTemplate = new SecurityActivityTemplate();
        BeanUtils.copyProperties(securityActivityTemplateDto,savedSecurityActivityTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityActivityTemplate.setUrl(urlMapping);
            savedSecurityActivityTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityActivityTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityActivityTemplateDto.getProvinceId());

            savedSecurityActivityTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityActivityTemplateDto.getCityId());

            savedSecurityActivityTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityActivityTemplateDto.getRegionId());

            savedSecurityActivityTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityActivityTemplateDto.getOrgCategoryId());

            savedSecurityActivityTemplate.setOrgCategory(orgCategory);
        }
        savedSecurityActivityTemplate.setOrg(getOrg(request));
        savedSecurityActivityTemplate.setCreateDate(new Date());
        savedSecurityActivityTemplate.setCreator(getCurrentUsername(request));
        securityActivityTemplateService.addObj(savedSecurityActivityTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param securityActivityTemplateDto
     * @return
     */
    @PostMapping("/securityActivityTemplate/updateSecurityActivityTemplate")
    public JsonResult updateSecurityActivityTemplate(SecurityActivityTemplateDto securityActivityTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityActivityTemplate savedSecurityActivityTemplate = securityActivityTemplateService.queryObjById(securityActivityTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityActivityTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedSecurityActivityTemplate.getRealPath())){
                FileUtil.deleteFile(savedSecurityActivityTemplate.getRealPath());
            }
            savedSecurityActivityTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityActivityTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityActivityTemplateDto.getProvinceId());

            savedSecurityActivityTemplate.setProvince(province);
        }else{
            savedSecurityActivityTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityActivityTemplateDto.getCityId());

            savedSecurityActivityTemplate.setCity(city);
        }else{
            savedSecurityActivityTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityActivityTemplateDto.getRegionId());

            savedSecurityActivityTemplate.setRegion(region);
        }else{
            savedSecurityActivityTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityActivityTemplateDto.getOrgCategoryId());

            savedSecurityActivityTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityActivityTemplate.setOrgCategory(null);
        }
        savedSecurityActivityTemplate.setName(securityActivityTemplateDto.getName());
        savedSecurityActivityTemplate.setNote(securityActivityTemplateDto.getNote());
        securityActivityTemplateService.updateObj(savedSecurityActivityTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityActivityTemplateDto
     * @return
     */
    @PostMapping("/securityActivityTemplate/updateSecurityActivityTemplateNoFile")
    public JsonResult updateSecurityActivityTemplateNoFile(SecurityActivityTemplateDto securityActivityTemplateDto,HttpServletRequest request){
        SecurityActivityTemplate savedSecurityActivityTemplate = securityActivityTemplateService.queryObjById(securityActivityTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityActivityTemplateDto.getProvinceId());
            savedSecurityActivityTemplate.setProvince(province);
        }else{
            savedSecurityActivityTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityActivityTemplateDto.getCityId());

            savedSecurityActivityTemplate.setCity(city);
        }else{
            savedSecurityActivityTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityActivityTemplateDto.getRegionId());

            savedSecurityActivityTemplate.setRegion(region);
        }else{
            savedSecurityActivityTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityActivityTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityActivityTemplateDto.getOrgCategoryId());

            savedSecurityActivityTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityActivityTemplate.setOrgCategory(null);
        }
        savedSecurityActivityTemplate.setName(securityActivityTemplateDto.getName());
        savedSecurityActivityTemplate.setNote(securityActivityTemplateDto.getNote());
        securityActivityTemplateService.updateObj(savedSecurityActivityTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityActivityTemplate/securityActivityTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityActivityTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityMonthTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.ISecurityMonthTemplateService;
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
 * 安全生产月模板控制器
 */
@RestController
public class SecurityMonthTemplateController extends CommonController{
    @Autowired
    private ISecurityMonthTemplateService securityMonthTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param securityMonthTemplateDto
     * @return
     */
    @GetMapping("/securityMonthTemplate/securityMonthTemplatesByPage")
    public ModelMap querySecurityMonthTemplates(SecurityMonthTemplateDto securityMonthTemplateDto,HttpServletRequest request){
        Page page = securityMonthTemplateService.findSecurityMonthTemplates(securityMonthTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityMonthTemplateDto
     * @return
     */
    @PostMapping("/securityMonthTemplate/securityMonthTemplate")
    public JsonResult addSecurityMonthTemplate(SecurityMonthTemplateDto securityMonthTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityMonthTemplate savedSecurityMonthTemplate = new SecurityMonthTemplate();
        BeanUtils.copyProperties(securityMonthTemplateDto,savedSecurityMonthTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityMonthTemplate.setUrl(urlMapping);
            savedSecurityMonthTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityMonthTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityMonthTemplateDto.getProvinceId());

            savedSecurityMonthTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityMonthTemplateDto.getCityId());

            savedSecurityMonthTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityMonthTemplateDto.getRegionId());

            savedSecurityMonthTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityMonthTemplateDto.getOrgCategoryId());

            savedSecurityMonthTemplate.setOrgCategory(orgCategory);
        }
        savedSecurityMonthTemplate.setOrg(getOrg(request));
        savedSecurityMonthTemplate.setCreateDate(new Date());
        savedSecurityMonthTemplate.setCreator(getCurrentUsername(request));
        securityMonthTemplateService.addObj(savedSecurityMonthTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param securityMonthTemplateDto
     * @return
     */
    @PostMapping("/securityMonthTemplate/updateSecurityMonthTemplate")
    public JsonResult updateSecurityMonthTemplate(SecurityMonthTemplateDto securityMonthTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityMonthTemplate savedSecurityMonthTemplate = securityMonthTemplateService.queryObjById(securityMonthTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityMonthTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedSecurityMonthTemplate.getRealPath())){
                FileUtil.deleteFile(savedSecurityMonthTemplate.getRealPath());
            }
            savedSecurityMonthTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityMonthTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityMonthTemplateDto.getProvinceId());

            savedSecurityMonthTemplate.setProvince(province);
        }else{
            savedSecurityMonthTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityMonthTemplateDto.getCityId());

            savedSecurityMonthTemplate.setCity(city);
        }else{
            savedSecurityMonthTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityMonthTemplateDto.getRegionId());

            savedSecurityMonthTemplate.setRegion(region);
        }else{
            savedSecurityMonthTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityMonthTemplateDto.getOrgCategoryId());

            savedSecurityMonthTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityMonthTemplate.setOrgCategory(null);
        }
        savedSecurityMonthTemplate.setName(securityMonthTemplateDto.getName());
        savedSecurityMonthTemplate.setNote(securityMonthTemplateDto.getNote());
        securityMonthTemplateService.updateObj(savedSecurityMonthTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityMonthTemplateDto
     * @return
     */
    @PostMapping("/securityMonthTemplate/updateSecurityMonthTemplateNoFile")
    public JsonResult updateSecurityMonthTemplateNoFile(SecurityMonthTemplateDto securityMonthTemplateDto,HttpServletRequest request){
        SecurityMonthTemplate savedSecurityMonthTemplate = securityMonthTemplateService.queryObjById(securityMonthTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityMonthTemplateDto.getProvinceId());
            savedSecurityMonthTemplate.setProvince(province);
        }else{
            savedSecurityMonthTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityMonthTemplateDto.getCityId());

            savedSecurityMonthTemplate.setCity(city);
        }else{
            savedSecurityMonthTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityMonthTemplateDto.getRegionId());

            savedSecurityMonthTemplate.setRegion(region);
        }else{
            savedSecurityMonthTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityMonthTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityMonthTemplateDto.getOrgCategoryId());

            savedSecurityMonthTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityMonthTemplate.setOrgCategory(null);
        }
        savedSecurityMonthTemplate.setName(securityMonthTemplateDto.getName());
        savedSecurityMonthTemplate.setNote(securityMonthTemplateDto.getNote());
        securityMonthTemplateService.updateObj(savedSecurityMonthTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityMonthTemplate/securityMonthTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityMonthTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityExaminationTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.ISecurityExaminationTemplateService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
 * 安全责任考核模板表控制器
 */
@RestController
public class SecurityExaminationTemplateController extends CommonController{
    @Autowired
    private ISecurityExaminationTemplateService securityExaminationTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param securityExaminationTemplateDto
     * @return
     */
    @GetMapping("/securityExaminationTemplate/securityExaminationTemplatesByPage")
    public ModelMap querySecurityExaminationTemplates(SecurityExaminationTemplateDto securityExaminationTemplateDto,HttpServletRequest request){
        Page page = securityExaminationTemplateService.findSecurityExaminationTemplates(securityExaminationTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityExaminationTemplateDto
     * @return
     */
    @PostMapping("/securityExaminationTemplate/securityExaminationTemplate")
    public JsonResult addSecurityExaminationTemplate(SecurityExaminationTemplateDto securityExaminationTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityExaminationTemplate savedSecurityExaminationTemplate = new SecurityExaminationTemplate();
        BeanUtils.copyProperties(securityExaminationTemplateDto,savedSecurityExaminationTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityExaminationTemplate.setUrl(urlMapping);
            savedSecurityExaminationTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityExaminationTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityExaminationTemplateDto.getProvinceId());

            savedSecurityExaminationTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityExaminationTemplateDto.getCityId());

            savedSecurityExaminationTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityExaminationTemplateDto.getRegionId());

            savedSecurityExaminationTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityExaminationTemplateDto.getOrgCategoryId());

            savedSecurityExaminationTemplate.setOrgCategory(orgCategory);
        }
        savedSecurityExaminationTemplate.setCreateDate(new Date());
        savedSecurityExaminationTemplate.setCreator(getCurrentUsername(request));
        savedSecurityExaminationTemplate.setOrg(getOrg(request));
        securityExaminationTemplateService.addObj(savedSecurityExaminationTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param securityExaminationTemplateDto
     * @return
     */
    @PostMapping("/securityExaminationTemplate/updateSecurityExaminationTemplate")
    public JsonResult updateSecurityExaminationTemplate(SecurityExaminationTemplateDto securityExaminationTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityExaminationTemplate savedSecurityExaminationTemplate = securityExaminationTemplateService.queryObjById(securityExaminationTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityExaminationTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedSecurityExaminationTemplate.getRealPath())) {
                FileUtil.deleteFile(savedSecurityExaminationTemplate.getRealPath());
            }
            savedSecurityExaminationTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityExaminationTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityExaminationTemplateDto.getProvinceId());

            savedSecurityExaminationTemplate.setProvince(province);
        }else{
            savedSecurityExaminationTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityExaminationTemplateDto.getCityId());

            savedSecurityExaminationTemplate.setCity(city);
        }else{
            savedSecurityExaminationTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityExaminationTemplateDto.getRegionId());

            savedSecurityExaminationTemplate.setRegion(region);
        }else{
            savedSecurityExaminationTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityExaminationTemplateDto.getOrgCategoryId());

            savedSecurityExaminationTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityExaminationTemplate.setOrgCategory(null);
        }
        savedSecurityExaminationTemplate.setName(securityExaminationTemplateDto.getName());
        savedSecurityExaminationTemplate.setNote(securityExaminationTemplateDto.getNote());
        securityExaminationTemplateService.updateObj(savedSecurityExaminationTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityExaminationTemplateDto
     * @return
     */
    @PostMapping("/securityExaminationTemplate/updateSecurityExaminationTemplateNoFile")
    public JsonResult updateSecurityExaminationTemplateNoFile(SecurityExaminationTemplateDto securityExaminationTemplateDto,HttpServletRequest request){
        SecurityExaminationTemplate savedSecurityExaminationTemplate = securityExaminationTemplateService.queryObjById(securityExaminationTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityExaminationTemplateDto.getProvinceId());

            savedSecurityExaminationTemplate.setProvince(province);
        }else{
            savedSecurityExaminationTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityExaminationTemplateDto.getCityId());

            savedSecurityExaminationTemplate.setCity(city);
        }else{
            savedSecurityExaminationTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityExaminationTemplateDto.getRegionId());

            savedSecurityExaminationTemplate.setRegion(region);
        }else{
            savedSecurityExaminationTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityExaminationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityExaminationTemplateDto.getOrgCategoryId());

            savedSecurityExaminationTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityExaminationTemplate.setOrgCategory(null);
        }
        savedSecurityExaminationTemplate.setName(securityExaminationTemplateDto.getName());
        savedSecurityExaminationTemplate.setNote(securityExaminationTemplateDto.getNote());
        securityExaminationTemplateService.updateObj(savedSecurityExaminationTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityExaminationTemplate/securityExaminationTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityExaminationTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

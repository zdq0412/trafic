package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.HealthyRecordTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IHealthyRecordTemplateService;
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
 * 职业健康记录模板控制器
 */
@RestController
public class HealthyRecordTemplateController extends CommonController{
    @Autowired
    private IHealthyRecordTemplateService healthyRecordTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param healthyRecordTemplateDto
     * @return
     */
    @GetMapping("/healthyRecordTemplate/healthyRecordTemplatesByPage")
    public ModelMap queryHealthyRecordTemplates(HealthyRecordTemplateDto healthyRecordTemplateDto,HttpServletRequest request){
        Page page = healthyRecordTemplateService.findHealthyRecordTemplates(healthyRecordTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param healthyRecordTemplateDto
     * @return
     */
    @PostMapping("/healthyRecordTemplate/healthyRecordTemplate")
    public JsonResult addHealthyRecordTemplate(HealthyRecordTemplateDto healthyRecordTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        HealthyRecordTemplate savedHealthyRecordTemplate = new HealthyRecordTemplate();
        BeanUtils.copyProperties(healthyRecordTemplateDto,savedHealthyRecordTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedHealthyRecordTemplate.setUrl(urlMapping);
            savedHealthyRecordTemplate.setRealPath(savedFile.getAbsolutePath());
            savedHealthyRecordTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(healthyRecordTemplateDto.getProvinceId());

            savedHealthyRecordTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(healthyRecordTemplateDto.getCityId());

            savedHealthyRecordTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(healthyRecordTemplateDto.getRegionId());

            savedHealthyRecordTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(healthyRecordTemplateDto.getOrgCategoryId());

            savedHealthyRecordTemplate.setOrgCategory(orgCategory);
        }
        savedHealthyRecordTemplate.setCreateDate(new Date());
        savedHealthyRecordTemplate.setCreator(getCurrentUsername(request));
        savedHealthyRecordTemplate.setOrg(getOrg(request));
        healthyRecordTemplateService.addObj(savedHealthyRecordTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param healthyRecordTemplateDto
     * @return
     */
    @PostMapping("/healthyRecordTemplate/updateHealthyRecordTemplate")
    public JsonResult updateHealthyRecordTemplate(HealthyRecordTemplateDto healthyRecordTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        HealthyRecordTemplate savedHealthyRecordTemplate = healthyRecordTemplateService.queryObjById(healthyRecordTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedHealthyRecordTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedHealthyRecordTemplate.getRealPath())){
                FileUtil.deleteFile(savedHealthyRecordTemplate.getRealPath());
            }
            savedHealthyRecordTemplate.setRealPath(savedFile.getAbsolutePath());
            savedHealthyRecordTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(healthyRecordTemplateDto.getProvinceId());

            savedHealthyRecordTemplate.setProvince(province);
        }else{
            savedHealthyRecordTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(healthyRecordTemplateDto.getCityId());

            savedHealthyRecordTemplate.setCity(city);
        }else{
            savedHealthyRecordTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(healthyRecordTemplateDto.getRegionId());

            savedHealthyRecordTemplate.setRegion(region);
        }else{
            savedHealthyRecordTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(healthyRecordTemplateDto.getOrgCategoryId());

            savedHealthyRecordTemplate.setOrgCategory(orgCategory);
        }else {
            savedHealthyRecordTemplate.setOrgCategory(null);
        }
        savedHealthyRecordTemplate.setName(healthyRecordTemplateDto.getName());
        savedHealthyRecordTemplate.setNote(healthyRecordTemplateDto.getNote());
        healthyRecordTemplateService.updateObj(savedHealthyRecordTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param healthyRecordTemplateDto
     * @return
     */
    @PostMapping("/healthyRecordTemplate/updateHealthyRecordTemplateNoFile")
    public JsonResult updateHealthyRecordTemplateNoFile(HealthyRecordTemplateDto healthyRecordTemplateDto,HttpServletRequest request){
        HealthyRecordTemplate savedHealthyRecordTemplate = healthyRecordTemplateService.queryObjById(healthyRecordTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(healthyRecordTemplateDto.getProvinceId());
            savedHealthyRecordTemplate.setProvince(province);
        }else{
            savedHealthyRecordTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(healthyRecordTemplateDto.getCityId());

            savedHealthyRecordTemplate.setCity(city);
        }else{
            savedHealthyRecordTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(healthyRecordTemplateDto.getRegionId());

            savedHealthyRecordTemplate.setRegion(region);
        }else{
            savedHealthyRecordTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(healthyRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(healthyRecordTemplateDto.getOrgCategoryId());

            savedHealthyRecordTemplate.setOrgCategory(orgCategory);
        }else {
            savedHealthyRecordTemplate.setOrgCategory(null);
        }
        savedHealthyRecordTemplate.setName(healthyRecordTemplateDto.getName());
        savedHealthyRecordTemplate.setNote(healthyRecordTemplateDto.getNote());
        healthyRecordTemplateService.updateObj(savedHealthyRecordTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/healthyRecordTemplate/healthyRecordTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        healthyRecordTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

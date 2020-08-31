package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.JobManagementAccountTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IJobManagementAccountTemplateService;
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
import java.util.Date;

/**
 * 作业管理台账模板控制器
 */
@RestController
public class JobManagementAccountTemplateController extends CommonController{
    @Autowired
    private IJobManagementAccountTemplateService jobManagementAccountTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param jobManagementAccountTemplateDto
     * @return
     */
    @GetMapping("/jobManagementAccountTemplate/jobManagementAccountTemplatesByPage")
    public ModelMap queryJobManagementAccountTemplates(JobManagementAccountTemplateDto jobManagementAccountTemplateDto,HttpServletRequest request){
        Page page = jobManagementAccountTemplateService.findJobManagementAccountTemplates(jobManagementAccountTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param jobManagementAccountTemplateDto
     * @return
     */
    @PostMapping("/jobManagementAccountTemplate/jobManagementAccountTemplate")
    public JsonResult addJobManagementAccountTemplate(JobManagementAccountTemplateDto jobManagementAccountTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        JobManagementAccountTemplate savedJobManagementAccountTemplate = new JobManagementAccountTemplate();
        BeanUtils.copyProperties(jobManagementAccountTemplateDto,savedJobManagementAccountTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedJobManagementAccountTemplate.setUrl(urlMapping);
            savedJobManagementAccountTemplate.setRealPath(savedFile.getAbsolutePath());
            savedJobManagementAccountTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(jobManagementAccountTemplateDto.getProvinceId());

            savedJobManagementAccountTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getJobManagementAccountTypeId())){
            Category jobManagementAccountType = new Category();
            jobManagementAccountType.setId(jobManagementAccountTemplateDto.getJobManagementAccountTypeId());

            savedJobManagementAccountTemplate.setJobManagementAccountType(jobManagementAccountType);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(jobManagementAccountTemplateDto.getCityId());

            savedJobManagementAccountTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(jobManagementAccountTemplateDto.getRegionId());

            savedJobManagementAccountTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(jobManagementAccountTemplateDto.getOrgCategoryId());

            savedJobManagementAccountTemplate.setOrgCategory(orgCategory);
        }
        savedJobManagementAccountTemplate.setCreateDate(new Date());
        savedJobManagementAccountTemplate.setCreator(getCurrentUsername(request));
        jobManagementAccountTemplateService.addObj(savedJobManagementAccountTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param jobManagementAccountTemplateDto
     * @return
     */
    @PostMapping("/jobManagementAccountTemplate/updateJobManagementAccountTemplate")
    public JsonResult updateJobManagementAccountTemplate(JobManagementAccountTemplateDto jobManagementAccountTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        JobManagementAccountTemplate savedJobManagementAccountTemplate = jobManagementAccountTemplateService.queryObjById(jobManagementAccountTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedJobManagementAccountTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedJobManagementAccountTemplate.getRealPath())){
                FileUtil.deleteFile(savedJobManagementAccountTemplate.getRealPath());
            }
            savedJobManagementAccountTemplate.setRealPath(savedFile.getAbsolutePath());
            savedJobManagementAccountTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(jobManagementAccountTemplateDto.getProvinceId());

            savedJobManagementAccountTemplate.setProvince(province);
        }else{
            savedJobManagementAccountTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(jobManagementAccountTemplateDto.getCityId());

            savedJobManagementAccountTemplate.setCity(city);
        }else{
            savedJobManagementAccountTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(jobManagementAccountTemplateDto.getRegionId());

            savedJobManagementAccountTemplate.setRegion(region);
        }else{
            savedJobManagementAccountTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(jobManagementAccountTemplateDto.getOrgCategoryId());

            savedJobManagementAccountTemplate.setOrgCategory(orgCategory);
        }else {
            savedJobManagementAccountTemplate.setOrgCategory(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getJobManagementAccountTypeId())){
            Category jobManagementAccountType = new Category();
            jobManagementAccountType.setId(jobManagementAccountTemplateDto.getJobManagementAccountTypeId());
            savedJobManagementAccountTemplate.setJobManagementAccountType(jobManagementAccountType);
        }
        savedJobManagementAccountTemplate.setName(jobManagementAccountTemplateDto.getName());
        savedJobManagementAccountTemplate.setNote(jobManagementAccountTemplateDto.getNote());
        jobManagementAccountTemplateService.updateObj(savedJobManagementAccountTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param jobManagementAccountTemplateDto
     * @return
     */
    @PostMapping("/jobManagementAccountTemplate/updateJobManagementAccountTemplateNoFile")
    public JsonResult updateJobManagementAccountTemplateNoFile(JobManagementAccountTemplateDto jobManagementAccountTemplateDto,HttpServletRequest request){
        JobManagementAccountTemplate savedJobManagementAccountTemplate = jobManagementAccountTemplateService.queryObjById(jobManagementAccountTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(jobManagementAccountTemplateDto.getProvinceId());

            savedJobManagementAccountTemplate.setProvince(province);
        }else{
            savedJobManagementAccountTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(jobManagementAccountTemplateDto.getCityId());

            savedJobManagementAccountTemplate.setCity(city);
        }else{
            savedJobManagementAccountTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(jobManagementAccountTemplateDto.getRegionId());

            savedJobManagementAccountTemplate.setRegion(region);
        }else{
            savedJobManagementAccountTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(jobManagementAccountTemplateDto.getOrgCategoryId());

            savedJobManagementAccountTemplate.setOrgCategory(orgCategory);
        }else {
            savedJobManagementAccountTemplate.setOrgCategory(null);
        }
        if(!StringUtils.isEmpty(jobManagementAccountTemplateDto.getJobManagementAccountTypeId())){
            Category jobManagementAccountType = new Category();
            jobManagementAccountType.setId(jobManagementAccountTemplateDto.getJobManagementAccountTypeId());

            savedJobManagementAccountTemplate.setJobManagementAccountType(jobManagementAccountType);
        }
        savedJobManagementAccountTemplate.setName(jobManagementAccountTemplateDto.getName());
        savedJobManagementAccountTemplate.setNote(jobManagementAccountTemplateDto.getNote());
        jobManagementAccountTemplateService.updateObj(savedJobManagementAccountTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/jobManagementAccountTemplate/jobManagementAccountTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        jobManagementAccountTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

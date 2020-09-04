package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityBuildTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.ISecurityBuildTemplateService;
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
 * 安全文化建设模板控制器
 */
@RestController
public class SecurityBuildTemplateController extends CommonController{
    @Autowired
    private ISecurityBuildTemplateService securityBuildTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param securityBuildTemplateDto
     * @return
     */
    @GetMapping("/securityBuildTemplate/securityBuildTemplatesByPage")
    public ModelMap querySecurityBuildTemplates(SecurityBuildTemplateDto securityBuildTemplateDto,HttpServletRequest request){
        Page page = securityBuildTemplateService.findSecurityBuildTemplates(securityBuildTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param securityBuildTemplateDto
     * @return
     */
    @PostMapping("/securityBuildTemplate/securityBuildTemplate")
    public JsonResult addSecurityBuildTemplate(SecurityBuildTemplateDto securityBuildTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityBuildTemplate savedSecurityBuildTemplate = new SecurityBuildTemplate();
        BeanUtils.copyProperties(securityBuildTemplateDto,savedSecurityBuildTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityBuildTemplate.setUrl(urlMapping);
            savedSecurityBuildTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityBuildTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityBuildTemplateDto.getProvinceId());

            savedSecurityBuildTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityBuildTemplateDto.getCityId());

            savedSecurityBuildTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityBuildTemplateDto.getRegionId());

            savedSecurityBuildTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityBuildTemplateDto.getOrgCategoryId());

            savedSecurityBuildTemplate.setOrgCategory(orgCategory);
        }
        savedSecurityBuildTemplate.setOrg(getOrg(request));
        savedSecurityBuildTemplate.setCreateDate(new Date());
        savedSecurityBuildTemplate.setCreator(getCurrentUsername(request));
        securityBuildTemplateService.addObj(savedSecurityBuildTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param securityBuildTemplateDto
     * @return
     */
    @PostMapping("/securityBuildTemplate/updateSecurityBuildTemplate")
    public JsonResult updateSecurityBuildTemplate(SecurityBuildTemplateDto securityBuildTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SecurityBuildTemplate savedSecurityBuildTemplate = securityBuildTemplateService.queryObjById(securityBuildTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSecurityBuildTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedSecurityBuildTemplate.getRealPath())){
                FileUtil.deleteFile(savedSecurityBuildTemplate.getRealPath());
            }
            savedSecurityBuildTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSecurityBuildTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityBuildTemplateDto.getProvinceId());

            savedSecurityBuildTemplate.setProvince(province);
        }else{
            savedSecurityBuildTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityBuildTemplateDto.getCityId());

            savedSecurityBuildTemplate.setCity(city);
        }else{
            savedSecurityBuildTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityBuildTemplateDto.getRegionId());

            savedSecurityBuildTemplate.setRegion(region);
        }else{
            savedSecurityBuildTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityBuildTemplateDto.getOrgCategoryId());

            savedSecurityBuildTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityBuildTemplate.setOrgCategory(null);
        }
        savedSecurityBuildTemplate.setName(securityBuildTemplateDto.getName());
        savedSecurityBuildTemplate.setNote(securityBuildTemplateDto.getNote());
        securityBuildTemplateService.updateObj(savedSecurityBuildTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param securityBuildTemplateDto
     * @return
     */
    @PostMapping("/securityBuildTemplate/updateSecurityBuildTemplateNoFile")
    public JsonResult updateSecurityBuildTemplateNoFile(SecurityBuildTemplateDto securityBuildTemplateDto,HttpServletRequest request){
        SecurityBuildTemplate savedSecurityBuildTemplate = securityBuildTemplateService.queryObjById(securityBuildTemplateDto.getId());
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityBuildTemplateDto.getProvinceId());
            savedSecurityBuildTemplate.setProvince(province);
        }else{
            savedSecurityBuildTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityBuildTemplateDto.getCityId());

            savedSecurityBuildTemplate.setCity(city);
        }else{
            savedSecurityBuildTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityBuildTemplateDto.getRegionId());

            savedSecurityBuildTemplate.setRegion(region);
        }else{
            savedSecurityBuildTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityBuildTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityBuildTemplateDto.getOrgCategoryId());

            savedSecurityBuildTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityBuildTemplate.setOrgCategory(null);
        }
        savedSecurityBuildTemplate.setName(securityBuildTemplateDto.getName());
        savedSecurityBuildTemplate.setNote(securityBuildTemplateDto.getNote());
        securityBuildTemplateService.updateObj(savedSecurityBuildTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/securityBuildTemplate/securityBuildTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityBuildTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

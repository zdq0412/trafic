package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.StandardizationTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IStandardizationTemplateService;
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
 * 标准化自评模板控制器
 */
@RestController
public class StandardizationTemplateController extends CommonController{
    @Autowired
    private IStandardizationTemplateService standardizationTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param standardizationTemplateDto
     * @return
     */
    @GetMapping("/standardizationTemplate/standardizationTemplatesByPage")
    public ModelMap queryStandardizationTemplates(StandardizationTemplateDto standardizationTemplateDto,HttpServletRequest request){
        Page page = standardizationTemplateService.findStandardizationTemplates(standardizationTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param standardizationTemplateDto
     * @return
     */
    @PostMapping("/standardizationTemplate/standardizationTemplate")
    public JsonResult addStandardizationTemplate(StandardizationTemplateDto standardizationTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        StandardizationTemplate savedStandardizationTemplate = new StandardizationTemplate();
        BeanUtils.copyProperties(standardizationTemplateDto,savedStandardizationTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedStandardizationTemplate.setUrl(urlMapping);
            savedStandardizationTemplate.setRealPath(savedFile.getAbsolutePath());
            savedStandardizationTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(standardizationTemplateDto.getProvinceId());

            savedStandardizationTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(standardizationTemplateDto.getCityId());

            savedStandardizationTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(standardizationTemplateDto.getRegionId());

            savedStandardizationTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(standardizationTemplateDto.getOrgCategoryId());

            savedStandardizationTemplate.setOrgCategory(orgCategory);
        }
        savedStandardizationTemplate.setCreateDate(new Date());
        savedStandardizationTemplate.setCreator(getCurrentUsername(request));
        savedStandardizationTemplate.setOrg(getOrg(request));
        standardizationTemplateService.addObj(savedStandardizationTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param standardizationTemplateDto
     * @return
     */
    @PostMapping("/standardizationTemplate/updateStandardizationTemplate")
    public JsonResult updateStandardizationTemplate(StandardizationTemplateDto standardizationTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        StandardizationTemplate savedStandardizationTemplate = standardizationTemplateService.queryObjById(standardizationTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedStandardizationTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedStandardizationTemplate.getRealPath())){
                FileUtil.deleteFile(savedStandardizationTemplate.getRealPath());
            }
            savedStandardizationTemplate.setRealPath(savedFile.getAbsolutePath());
            savedStandardizationTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(standardizationTemplateDto.getProvinceId());

            savedStandardizationTemplate.setProvince(province);
        }else{
            savedStandardizationTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(standardizationTemplateDto.getCityId());

            savedStandardizationTemplate.setCity(city);
        }else{
            savedStandardizationTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(standardizationTemplateDto.getRegionId());

            savedStandardizationTemplate.setRegion(region);
        }else{
            savedStandardizationTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(standardizationTemplateDto.getOrgCategoryId());

            savedStandardizationTemplate.setOrgCategory(orgCategory);
        }else {
            savedStandardizationTemplate.setOrgCategory(null);
        }
        savedStandardizationTemplate.setName(standardizationTemplateDto.getName());
        savedStandardizationTemplate.setNote(standardizationTemplateDto.getNote());
        standardizationTemplateService.updateObj(savedStandardizationTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param standardizationTemplateDto
     * @return
     */
    @PostMapping("/standardizationTemplate/updateStandardizationTemplateNoFile")
    public JsonResult updateStandardizationTemplateNoFile(StandardizationTemplateDto standardizationTemplateDto,HttpServletRequest request){
        StandardizationTemplate savedStandardizationTemplate = standardizationTemplateService.queryObjById(standardizationTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(standardizationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(standardizationTemplateDto.getProvinceId());

            savedStandardizationTemplate.setProvince(province);
        }else{
            savedStandardizationTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(standardizationTemplateDto.getCityId());

            savedStandardizationTemplate.setCity(city);
        }else{
            savedStandardizationTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(standardizationTemplateDto.getRegionId());

            savedStandardizationTemplate.setRegion(region);
        }else{
            savedStandardizationTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(standardizationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(standardizationTemplateDto.getOrgCategoryId());

            savedStandardizationTemplate.setOrgCategory(orgCategory);
        }else {
            savedStandardizationTemplate.setOrgCategory(null);
        }
        savedStandardizationTemplate.setName(standardizationTemplateDto.getName());
        savedStandardizationTemplate.setNote(standardizationTemplateDto.getNote());
        standardizationTemplateService.updateObj(savedStandardizationTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/standardizationTemplate/standardizationTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        standardizationTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

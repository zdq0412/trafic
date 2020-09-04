package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.AccidentRecordTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IAccidentRecordTemplateService;
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
 * 安全事故处理记录模板控制器
 */
@RestController
public class AccidentRecordTemplateController extends CommonController{
    @Autowired
    private IAccidentRecordTemplateService accidentRecordTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param accidentRecordTemplateDto
     * @return
     */
    @GetMapping("/accidentRecordTemplate/accidentRecordTemplatesByPage")
    public ModelMap queryAccidentRecordTemplates(AccidentRecordTemplateDto accidentRecordTemplateDto,HttpServletRequest request){
        Page page = accidentRecordTemplateService.findAccidentRecordTemplates(accidentRecordTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param accidentRecordTemplateDto
     * @return
     */
    @PostMapping("/accidentRecordTemplate/accidentRecordTemplate")
    public JsonResult addAccidentRecordTemplate(AccidentRecordTemplateDto accidentRecordTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        AccidentRecordTemplate savedAccidentRecordTemplate = new AccidentRecordTemplate();
        BeanUtils.copyProperties(accidentRecordTemplateDto,savedAccidentRecordTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedAccidentRecordTemplate.setUrl(urlMapping);
            savedAccidentRecordTemplate.setRealPath(savedFile.getAbsolutePath());
            savedAccidentRecordTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(accidentRecordTemplateDto.getProvinceId());

            savedAccidentRecordTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(accidentRecordTemplateDto.getCityId());

            savedAccidentRecordTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(accidentRecordTemplateDto.getRegionId());

            savedAccidentRecordTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(accidentRecordTemplateDto.getOrgCategoryId());

            savedAccidentRecordTemplate.setOrgCategory(orgCategory);
        }
        savedAccidentRecordTemplate.setOrg(getOrg(request));
        savedAccidentRecordTemplate.setCreateDate(new Date());
        savedAccidentRecordTemplate.setCreator(getCurrentUsername(request));
        accidentRecordTemplateService.addObj(savedAccidentRecordTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param accidentRecordTemplateDto
     * @return
     */
    @PostMapping("/accidentRecordTemplate/updateAccidentRecordTemplate")
    public JsonResult updateAccidentRecordTemplate(AccidentRecordTemplateDto accidentRecordTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        AccidentRecordTemplate savedAccidentRecordTemplate = accidentRecordTemplateService.queryObjById(accidentRecordTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedAccidentRecordTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedAccidentRecordTemplate.getRealPath())){
                FileUtil.deleteFile(savedAccidentRecordTemplate.getRealPath());
            }
            savedAccidentRecordTemplate.setRealPath(savedFile.getAbsolutePath());
            savedAccidentRecordTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(accidentRecordTemplateDto.getProvinceId());

            savedAccidentRecordTemplate.setProvince(province);
        }else{
            savedAccidentRecordTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(accidentRecordTemplateDto.getCityId());

            savedAccidentRecordTemplate.setCity(city);
        }else{
            savedAccidentRecordTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(accidentRecordTemplateDto.getRegionId());

            savedAccidentRecordTemplate.setRegion(region);
        }else{
            savedAccidentRecordTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(accidentRecordTemplateDto.getOrgCategoryId());

            savedAccidentRecordTemplate.setOrgCategory(orgCategory);
        }else {
            savedAccidentRecordTemplate.setOrgCategory(null);
        }
        savedAccidentRecordTemplate.setName(accidentRecordTemplateDto.getName());
        savedAccidentRecordTemplate.setNote(accidentRecordTemplateDto.getNote());
        accidentRecordTemplateService.updateObj(savedAccidentRecordTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param accidentRecordTemplateDto
     * @return
     */
    @PostMapping("/accidentRecordTemplate/updateAccidentRecordTemplateNoFile")
    public JsonResult updateAccidentRecordTemplateNoFile(AccidentRecordTemplateDto accidentRecordTemplateDto,HttpServletRequest request){
        AccidentRecordTemplate savedAccidentRecordTemplate = accidentRecordTemplateService.queryObjById(accidentRecordTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(accidentRecordTemplateDto.getProvinceId());
            savedAccidentRecordTemplate.setProvince(province);
        }else{
            savedAccidentRecordTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(accidentRecordTemplateDto.getCityId());

            savedAccidentRecordTemplate.setCity(city);
        }else{
            savedAccidentRecordTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(accidentRecordTemplateDto.getRegionId());

            savedAccidentRecordTemplate.setRegion(region);
        }else{
            savedAccidentRecordTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(accidentRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(accidentRecordTemplateDto.getOrgCategoryId());

            savedAccidentRecordTemplate.setOrgCategory(orgCategory);
        }else {
            savedAccidentRecordTemplate.setOrgCategory(null);
        }
        savedAccidentRecordTemplate.setName(accidentRecordTemplateDto.getName());
        savedAccidentRecordTemplate.setNote(accidentRecordTemplateDto.getNote());
        accidentRecordTemplateService.updateObj(savedAccidentRecordTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/accidentRecordTemplate/accidentRecordTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        accidentRecordTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

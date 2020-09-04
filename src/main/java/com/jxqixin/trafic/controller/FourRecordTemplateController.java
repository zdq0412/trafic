package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.FourRecordTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IFourRecordTemplateService;
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
 * 四不放过记录模板控制器
 */
@RestController
public class FourRecordTemplateController extends CommonController{
    @Autowired
    private IFourRecordTemplateService fourRecordTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param fourRecordTemplateDto
     * @return
     */
    @GetMapping("/fourRecordTemplate/fourRecordTemplatesByPage")
    public ModelMap queryFourRecordTemplates(FourRecordTemplateDto fourRecordTemplateDto,HttpServletRequest request){
        Page page = fourRecordTemplateService.findFourRecordTemplates(fourRecordTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param fourRecordTemplateDto
     * @return
     */
    @PostMapping("/fourRecordTemplate/fourRecordTemplate")
    public JsonResult addFourRecordTemplate(FourRecordTemplateDto fourRecordTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        FourRecordTemplate savedFourRecordTemplate = new FourRecordTemplate();
        BeanUtils.copyProperties(fourRecordTemplateDto,savedFourRecordTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedFourRecordTemplate.setUrl(urlMapping);
            savedFourRecordTemplate.setRealPath(savedFile.getAbsolutePath());
            savedFourRecordTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(fourRecordTemplateDto.getProvinceId());

            savedFourRecordTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(fourRecordTemplateDto.getCityId());

            savedFourRecordTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(fourRecordTemplateDto.getRegionId());

            savedFourRecordTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(fourRecordTemplateDto.getOrgCategoryId());

            savedFourRecordTemplate.setOrgCategory(orgCategory);
        }
        savedFourRecordTemplate.setOrg(getOrg(request));
        savedFourRecordTemplate.setCreateDate(new Date());
        savedFourRecordTemplate.setCreator(getCurrentUsername(request));
        fourRecordTemplateService.addObj(savedFourRecordTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param fourRecordTemplateDto
     * @return
     */
    @PostMapping("/fourRecordTemplate/updateFourRecordTemplate")
    public JsonResult updateFourRecordTemplate(FourRecordTemplateDto fourRecordTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        FourRecordTemplate savedFourRecordTemplate = fourRecordTemplateService.queryObjById(fourRecordTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedFourRecordTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedFourRecordTemplate.getRealPath())){
                FileUtil.deleteFile(savedFourRecordTemplate.getRealPath());
            }
            savedFourRecordTemplate.setRealPath(savedFile.getAbsolutePath());
            savedFourRecordTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(fourRecordTemplateDto.getProvinceId());

            savedFourRecordTemplate.setProvince(province);
        }else{
            savedFourRecordTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(fourRecordTemplateDto.getCityId());

            savedFourRecordTemplate.setCity(city);
        }else{
            savedFourRecordTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(fourRecordTemplateDto.getRegionId());

            savedFourRecordTemplate.setRegion(region);
        }else{
            savedFourRecordTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(fourRecordTemplateDto.getOrgCategoryId());

            savedFourRecordTemplate.setOrgCategory(orgCategory);
        }else {
            savedFourRecordTemplate.setOrgCategory(null);
        }
        savedFourRecordTemplate.setName(fourRecordTemplateDto.getName());
        savedFourRecordTemplate.setNote(fourRecordTemplateDto.getNote());
        fourRecordTemplateService.updateObj(savedFourRecordTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param fourRecordTemplateDto
     * @return
     */
    @PostMapping("/fourRecordTemplate/updateFourRecordTemplateNoFile")
    public JsonResult updateFourRecordTemplateNoFile(FourRecordTemplateDto fourRecordTemplateDto,HttpServletRequest request){
        FourRecordTemplate savedFourRecordTemplate = fourRecordTemplateService.queryObjById(fourRecordTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(fourRecordTemplateDto.getProvinceId());
            savedFourRecordTemplate.setProvince(province);
        }else{
            savedFourRecordTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(fourRecordTemplateDto.getCityId());

            savedFourRecordTemplate.setCity(city);
        }else{
            savedFourRecordTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(fourRecordTemplateDto.getRegionId());

            savedFourRecordTemplate.setRegion(region);
        }else{
            savedFourRecordTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(fourRecordTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(fourRecordTemplateDto.getOrgCategoryId());

            savedFourRecordTemplate.setOrgCategory(orgCategory);
        }else {
            savedFourRecordTemplate.setOrgCategory(null);
        }
        savedFourRecordTemplate.setName(fourRecordTemplateDto.getName());
        savedFourRecordTemplate.setNote(fourRecordTemplateDto.getNote());
        fourRecordTemplateService.updateObj(savedFourRecordTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/fourRecordTemplate/fourRecordTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        fourRecordTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

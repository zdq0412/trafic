package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.GpsAccountTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IGpsAccountTemplateService;
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
 * GPS记录模板控制器
 */
@RestController
public class GpsAccountTemplateController extends CommonController{
    @Autowired
    private IGpsAccountTemplateService gpsAccountTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param gpsAccountTemplateDto
     * @return
     */
    @GetMapping("/gpsAccountTemplate/gpsAccountTemplatesByPage")
    public ModelMap queryGpsAccountTemplates(GpsAccountTemplateDto gpsAccountTemplateDto,HttpServletRequest request){
        Page page = gpsAccountTemplateService.findGpsAccountTemplates(gpsAccountTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param gpsAccountTemplateDto
     * @return
     */
    @PostMapping("/gpsAccountTemplate/gpsAccountTemplate")
    public JsonResult addGpsAccountTemplate(GpsAccountTemplateDto gpsAccountTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        GpsAccountTemplate savedGpsAccountTemplate = new GpsAccountTemplate();
        BeanUtils.copyProperties(gpsAccountTemplateDto,savedGpsAccountTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedGpsAccountTemplate.setUrl(urlMapping);
            savedGpsAccountTemplate.setRealPath(savedFile.getAbsolutePath());
            savedGpsAccountTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(gpsAccountTemplateDto.getProvinceId());

            savedGpsAccountTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(gpsAccountTemplateDto.getCityId());

            savedGpsAccountTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(gpsAccountTemplateDto.getRegionId());

            savedGpsAccountTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(gpsAccountTemplateDto.getOrgCategoryId());

            savedGpsAccountTemplate.setOrgCategory(orgCategory);
        }
        savedGpsAccountTemplate.setOrg(getOrg(request));
        savedGpsAccountTemplate.setCreateDate(new Date());
        savedGpsAccountTemplate.setCreator(getCurrentUsername(request));
        gpsAccountTemplateService.addObj(savedGpsAccountTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param gpsAccountTemplateDto
     * @return
     */
    @PostMapping("/gpsAccountTemplate/updateGpsAccountTemplate")
    public JsonResult updateGpsAccountTemplate(GpsAccountTemplateDto gpsAccountTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        GpsAccountTemplate savedGpsAccountTemplate = gpsAccountTemplateService.queryObjById(gpsAccountTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedGpsAccountTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedGpsAccountTemplate.getRealPath())){
                FileUtil.deleteFile(savedGpsAccountTemplate.getRealPath());
            }
            savedGpsAccountTemplate.setRealPath(savedFile.getAbsolutePath());
            savedGpsAccountTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(gpsAccountTemplateDto.getProvinceId());

            savedGpsAccountTemplate.setProvince(province);
        }else{
            savedGpsAccountTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(gpsAccountTemplateDto.getCityId());

            savedGpsAccountTemplate.setCity(city);
        }else{
            savedGpsAccountTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(gpsAccountTemplateDto.getRegionId());

            savedGpsAccountTemplate.setRegion(region);
        }else{
            savedGpsAccountTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(gpsAccountTemplateDto.getOrgCategoryId());

            savedGpsAccountTemplate.setOrgCategory(orgCategory);
        }else {
            savedGpsAccountTemplate.setOrgCategory(null);
        }
        savedGpsAccountTemplate.setName(gpsAccountTemplateDto.getName());
        savedGpsAccountTemplate.setNote(gpsAccountTemplateDto.getNote());
        gpsAccountTemplateService.updateObj(savedGpsAccountTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param gpsAccountTemplateDto
     * @return
     */
    @PostMapping("/gpsAccountTemplate/updateGpsAccountTemplateNoFile")
    public JsonResult updateGpsAccountTemplateNoFile(GpsAccountTemplateDto gpsAccountTemplateDto,HttpServletRequest request){
        GpsAccountTemplate savedGpsAccountTemplate = gpsAccountTemplateService.queryObjById(gpsAccountTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(gpsAccountTemplateDto.getProvinceId());
            savedGpsAccountTemplate.setProvince(province);
        }else{
            savedGpsAccountTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(gpsAccountTemplateDto.getCityId());

            savedGpsAccountTemplate.setCity(city);
        }else{
            savedGpsAccountTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(gpsAccountTemplateDto.getRegionId());

            savedGpsAccountTemplate.setRegion(region);
        }else{
            savedGpsAccountTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(gpsAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(gpsAccountTemplateDto.getOrgCategoryId());

            savedGpsAccountTemplate.setOrgCategory(orgCategory);
        }else {
            savedGpsAccountTemplate.setOrgCategory(null);
        }
        savedGpsAccountTemplate.setName(gpsAccountTemplateDto.getName());
        savedGpsAccountTemplate.setNote(gpsAccountTemplateDto.getNote());
        gpsAccountTemplateService.updateObj(savedGpsAccountTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/gpsAccountTemplate/gpsAccountTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        gpsAccountTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

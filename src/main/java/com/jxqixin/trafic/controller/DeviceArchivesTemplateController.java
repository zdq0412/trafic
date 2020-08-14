package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DeviceArchivesTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IDeviceArchivesTemplateService;
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
 * 设备档案模板控制器
 */
@RestController
public class DeviceArchivesTemplateController extends CommonController{
    @Autowired
    private IDeviceArchivesTemplateService deviceArchivesTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param deviceArchivesTemplateDto
     * @return
     */
    @GetMapping("/deviceArchivesTemplate/deviceArchivesTemplatesByPage")
    public ModelMap queryDeviceArchivesTemplates(DeviceArchivesTemplateDto deviceArchivesTemplateDto,HttpServletRequest request){
        Page page = deviceArchivesTemplateService.findDeviceArchivesTemplates(deviceArchivesTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param deviceArchivesTemplateDto
     * @return
     */
    @PostMapping("/deviceArchivesTemplate/deviceArchivesTemplate")
    public JsonResult addDeviceArchivesTemplate(DeviceArchivesTemplateDto deviceArchivesTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        DeviceArchivesTemplate savedDeviceArchivesTemplate = new DeviceArchivesTemplate();
        BeanUtils.copyProperties(deviceArchivesTemplateDto,savedDeviceArchivesTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedDeviceArchivesTemplate.setUrl(urlMapping);
            savedDeviceArchivesTemplate.setRealPath(savedFile.getAbsolutePath());
            savedDeviceArchivesTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(deviceArchivesTemplateDto.getProvinceId());

            savedDeviceArchivesTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(deviceArchivesTemplateDto.getCityId());

            savedDeviceArchivesTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(deviceArchivesTemplateDto.getRegionId());

            savedDeviceArchivesTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(deviceArchivesTemplateDto.getOrgCategoryId());

            savedDeviceArchivesTemplate.setOrgCategory(orgCategory);
        }
        savedDeviceArchivesTemplate.setCreateDate(new Date());
        savedDeviceArchivesTemplate.setCreator(getCurrentUsername(request));
        savedDeviceArchivesTemplate.setOrg(getOrg(request));
        deviceArchivesTemplateService.addObj(savedDeviceArchivesTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param deviceArchivesTemplateDto
     * @return
     */
    @PostMapping("/deviceArchivesTemplate/updateDeviceArchivesTemplate")
    public JsonResult updateDeviceArchivesTemplate(DeviceArchivesTemplateDto deviceArchivesTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        DeviceArchivesTemplate savedDeviceArchivesTemplate = deviceArchivesTemplateService.queryObjById(deviceArchivesTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedDeviceArchivesTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedDeviceArchivesTemplate.getRealPath())){
                FileUtil.deleteFile(savedDeviceArchivesTemplate.getRealPath());
            }
            savedDeviceArchivesTemplate.setRealPath(savedFile.getAbsolutePath());
            savedDeviceArchivesTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(deviceArchivesTemplateDto.getProvinceId());

            savedDeviceArchivesTemplate.setProvince(province);
        }else{
            savedDeviceArchivesTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(deviceArchivesTemplateDto.getCityId());

            savedDeviceArchivesTemplate.setCity(city);
        }else{
            savedDeviceArchivesTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(deviceArchivesTemplateDto.getRegionId());

            savedDeviceArchivesTemplate.setRegion(region);
        }else{
            savedDeviceArchivesTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(deviceArchivesTemplateDto.getOrgCategoryId());

            savedDeviceArchivesTemplate.setOrgCategory(orgCategory);
        }else {
            savedDeviceArchivesTemplate.setOrgCategory(null);
        }
        savedDeviceArchivesTemplate.setName(deviceArchivesTemplateDto.getName());
        savedDeviceArchivesTemplate.setNote(deviceArchivesTemplateDto.getNote());
        deviceArchivesTemplateService.updateObj(savedDeviceArchivesTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param deviceArchivesTemplateDto
     * @return
     */
    @PostMapping("/deviceArchivesTemplate/updateDeviceArchivesTemplateNoFile")
    public JsonResult updateDeviceArchivesTemplateNoFile(DeviceArchivesTemplateDto deviceArchivesTemplateDto,HttpServletRequest request){
        DeviceArchivesTemplate savedDeviceArchivesTemplate = deviceArchivesTemplateService.queryObjById(deviceArchivesTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(deviceArchivesTemplateDto.getProvinceId());

            savedDeviceArchivesTemplate.setProvince(province);
        }else{
            savedDeviceArchivesTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(deviceArchivesTemplateDto.getCityId());

            savedDeviceArchivesTemplate.setCity(city);
        }else{
            savedDeviceArchivesTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(deviceArchivesTemplateDto.getRegionId());

            savedDeviceArchivesTemplate.setRegion(region);
        }else{
            savedDeviceArchivesTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(deviceArchivesTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(deviceArchivesTemplateDto.getOrgCategoryId());

            savedDeviceArchivesTemplate.setOrgCategory(orgCategory);
        }else {
            savedDeviceArchivesTemplate.setOrgCategory(null);
        }
        savedDeviceArchivesTemplate.setName(deviceArchivesTemplateDto.getName());
        savedDeviceArchivesTemplate.setNote(deviceArchivesTemplateDto.getNote());
        deviceArchivesTemplateService.updateObj(savedDeviceArchivesTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/deviceArchivesTemplate/deviceArchivesTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        deviceArchivesTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

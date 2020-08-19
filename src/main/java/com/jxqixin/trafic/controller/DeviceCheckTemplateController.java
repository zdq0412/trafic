package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DeviceCheckTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IDeviceCheckTemplateService;
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
 * 设备点检模板控制器
 */
@RestController
public class DeviceCheckTemplateController extends CommonController{
    @Autowired
    private IDeviceCheckTemplateService deviceCheckTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param deviceCheckTemplateDto
     * @return
     */
    @GetMapping("/deviceCheckTemplate/deviceCheckTemplatesByPage")
    public ModelMap queryDeviceCheckTemplates(DeviceCheckTemplateDto deviceCheckTemplateDto,HttpServletRequest request){
        Page page = deviceCheckTemplateService.findDeviceCheckTemplates(deviceCheckTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param deviceCheckTemplateDto
     * @return
     */
    @PostMapping("/deviceCheckTemplate/deviceCheckTemplate")
    public JsonResult addDeviceCheckTemplate(DeviceCheckTemplateDto deviceCheckTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        DeviceCheckTemplate savedDeviceCheckTemplate = new DeviceCheckTemplate();
        BeanUtils.copyProperties(deviceCheckTemplateDto,savedDeviceCheckTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedDeviceCheckTemplate.setUrl(urlMapping);
            savedDeviceCheckTemplate.setRealPath(savedFile.getAbsolutePath());
            savedDeviceCheckTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(deviceCheckTemplateDto.getProvinceId());

            savedDeviceCheckTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(deviceCheckTemplateDto.getCityId());

            savedDeviceCheckTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(deviceCheckTemplateDto.getRegionId());

            savedDeviceCheckTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(deviceCheckTemplateDto.getOrgCategoryId());

            savedDeviceCheckTemplate.setOrgCategory(orgCategory);
        }
        savedDeviceCheckTemplate.setCreateDate(new Date());
        savedDeviceCheckTemplate.setCreator(getCurrentUsername(request));
        savedDeviceCheckTemplate.setOrg(getOrg(request));
        deviceCheckTemplateService.addObj(savedDeviceCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param deviceCheckTemplateDto
     * @return
     */
    @PostMapping("/deviceCheckTemplate/updateDeviceCheckTemplate")
    public JsonResult updateDeviceCheckTemplate(DeviceCheckTemplateDto deviceCheckTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        DeviceCheckTemplate savedDeviceCheckTemplate = deviceCheckTemplateService.queryObjById(deviceCheckTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedDeviceCheckTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedDeviceCheckTemplate.getRealPath())){
                FileUtil.deleteFile(savedDeviceCheckTemplate.getRealPath());
            }
            savedDeviceCheckTemplate.setRealPath(savedFile.getAbsolutePath());
            savedDeviceCheckTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(deviceCheckTemplateDto.getProvinceId());

            savedDeviceCheckTemplate.setProvince(province);
        }else{
            savedDeviceCheckTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(deviceCheckTemplateDto.getCityId());

            savedDeviceCheckTemplate.setCity(city);
        }else{
            savedDeviceCheckTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(deviceCheckTemplateDto.getRegionId());

            savedDeviceCheckTemplate.setRegion(region);
        }else{
            savedDeviceCheckTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(deviceCheckTemplateDto.getOrgCategoryId());

            savedDeviceCheckTemplate.setOrgCategory(orgCategory);
        }else {
            savedDeviceCheckTemplate.setOrgCategory(null);
        }
        savedDeviceCheckTemplate.setName(deviceCheckTemplateDto.getName());
        savedDeviceCheckTemplate.setNote(deviceCheckTemplateDto.getNote());
        deviceCheckTemplateService.updateObj(savedDeviceCheckTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param deviceCheckTemplateDto
     * @return
     */
    @PostMapping("/deviceCheckTemplate/updateDeviceCheckTemplateNoFile")
    public JsonResult updateDeviceCheckTemplateNoFile(DeviceCheckTemplateDto deviceCheckTemplateDto,HttpServletRequest request){
        DeviceCheckTemplate savedDeviceCheckTemplate = deviceCheckTemplateService.queryObjById(deviceCheckTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(deviceCheckTemplateDto.getProvinceId());

            savedDeviceCheckTemplate.setProvince(province);
        }else{
            savedDeviceCheckTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(deviceCheckTemplateDto.getCityId());

            savedDeviceCheckTemplate.setCity(city);
        }else{
            savedDeviceCheckTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(deviceCheckTemplateDto.getRegionId());

            savedDeviceCheckTemplate.setRegion(region);
        }else{
            savedDeviceCheckTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(deviceCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(deviceCheckTemplateDto.getOrgCategoryId());

            savedDeviceCheckTemplate.setOrgCategory(orgCategory);
        }else {
            savedDeviceCheckTemplate.setOrgCategory(null);
        }
        savedDeviceCheckTemplate.setName(deviceCheckTemplateDto.getName());
        savedDeviceCheckTemplate.setNote(deviceCheckTemplateDto.getNote());
        deviceCheckTemplateService.updateObj(savedDeviceCheckTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/deviceCheckTemplate/deviceCheckTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        deviceCheckTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

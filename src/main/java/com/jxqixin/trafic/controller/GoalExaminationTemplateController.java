package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.GoalExaminationTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IGoalExaminationTemplateService;
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
 * 安全目标考核模板控制器
 */
@RestController
public class GoalExaminationTemplateController extends CommonController{
    @Autowired
    private IGoalExaminationTemplateService goalExaminationTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param goalExaminationTemplateDto
     * @return
     */
    @GetMapping("/goalExaminationTemplate/goalExaminationTemplatesByPage")
    public ModelMap queryGoalExaminationTemplates(GoalExaminationTemplateDto goalExaminationTemplateDto,HttpServletRequest request){
        Page page = goalExaminationTemplateService.findGoalExaminationTemplates(goalExaminationTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param goalExaminationTemplateDto
     * @return
     */
    @PostMapping("/goalExaminationTemplate/goalExaminationTemplate")
    public JsonResult addGoalExaminationTemplate(GoalExaminationTemplateDto goalExaminationTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        GoalExaminationTemplate savedGoalExaminationTemplate = new GoalExaminationTemplate();
        BeanUtils.copyProperties(goalExaminationTemplateDto,savedGoalExaminationTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedGoalExaminationTemplate.setUrl(urlMapping);
            savedGoalExaminationTemplate.setRealPath(savedFile.getAbsolutePath());
            savedGoalExaminationTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(goalExaminationTemplateDto.getProvinceId());

            savedGoalExaminationTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(goalExaminationTemplateDto.getCityId());

            savedGoalExaminationTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(goalExaminationTemplateDto.getRegionId());

            savedGoalExaminationTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(goalExaminationTemplateDto.getOrgCategoryId());

            savedGoalExaminationTemplate.setOrgCategory(orgCategory);
        }
        savedGoalExaminationTemplate.setCreateDate(new Date());
        savedGoalExaminationTemplate.setCreator(getCurrentUsername(request));
        savedGoalExaminationTemplate.setOrg(getOrg(request));
        goalExaminationTemplateService.addObj(savedGoalExaminationTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param goalExaminationTemplateDto
     * @return
     */
    @PostMapping("/goalExaminationTemplate/updateGoalExaminationTemplate")
    public JsonResult updateGoalExaminationTemplate(GoalExaminationTemplateDto goalExaminationTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        GoalExaminationTemplate savedGoalExaminationTemplate = goalExaminationTemplateService.queryObjById(goalExaminationTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedGoalExaminationTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedGoalExaminationTemplate.getRealPath())){
                FileUtil.deleteFile(savedGoalExaminationTemplate.getRealPath());
            }
            savedGoalExaminationTemplate.setRealPath(savedFile.getAbsolutePath());
            savedGoalExaminationTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(goalExaminationTemplateDto.getProvinceId());

            savedGoalExaminationTemplate.setProvince(province);
        }else{
            savedGoalExaminationTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(goalExaminationTemplateDto.getCityId());

            savedGoalExaminationTemplate.setCity(city);
        }else{
            savedGoalExaminationTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(goalExaminationTemplateDto.getRegionId());

            savedGoalExaminationTemplate.setRegion(region);
        }else{
            savedGoalExaminationTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(goalExaminationTemplateDto.getOrgCategoryId());

            savedGoalExaminationTemplate.setOrgCategory(orgCategory);
        }else {
            savedGoalExaminationTemplate.setOrgCategory(null);
        }
        savedGoalExaminationTemplate.setName(goalExaminationTemplateDto.getName());
        savedGoalExaminationTemplate.setNote(goalExaminationTemplateDto.getNote());
        goalExaminationTemplateService.updateObj(savedGoalExaminationTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param goalExaminationTemplateDto
     * @return
     */
    @PostMapping("/goalExaminationTemplate/updateGoalExaminationTemplateNoFile")
    public JsonResult updateGoalExaminationTemplateNoFile(GoalExaminationTemplateDto goalExaminationTemplateDto,HttpServletRequest request){
        GoalExaminationTemplate savedGoalExaminationTemplate = goalExaminationTemplateService.queryObjById(goalExaminationTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(goalExaminationTemplateDto.getProvinceId());

            savedGoalExaminationTemplate.setProvince(province);
        }else{
            savedGoalExaminationTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(goalExaminationTemplateDto.getCityId());

            savedGoalExaminationTemplate.setCity(city);
        }else{
            savedGoalExaminationTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(goalExaminationTemplateDto.getRegionId());

            savedGoalExaminationTemplate.setRegion(region);
        }else{
            savedGoalExaminationTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(goalExaminationTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(goalExaminationTemplateDto.getOrgCategoryId());

            savedGoalExaminationTemplate.setOrgCategory(orgCategory);
        }else {
            savedGoalExaminationTemplate.setOrgCategory(null);
        }
        savedGoalExaminationTemplate.setName(goalExaminationTemplateDto.getName());
        savedGoalExaminationTemplate.setNote(goalExaminationTemplateDto.getNote());
        goalExaminationTemplateService.updateObj(savedGoalExaminationTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/goalExaminationTemplate/goalExaminationTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        goalExaminationTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

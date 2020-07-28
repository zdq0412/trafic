package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SecurityCheckTemplateDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.SecurityCheckTemplate;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.ISecurityCheckTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 安全检查模板控制器
 */
@RestController
public class SecurityCheckTemplateController extends CommonController{
    @Autowired
    private ISecurityCheckTemplateService securityCheckTemplateService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询安全检查
     * @param nameDto
     * @return
     */
    @GetMapping("/securityCheckTemplate/securityCheckTemplatesByPage")
    public ModelMap querySecurityCheckTemplates(NameDto nameDto,String type){
        Page page = securityCheckTemplateService.findSecurityCheckTemplates(nameDto,type);
        return pageModelMap(page);
    }
    /**
     * 新增安全检查
     * @param securityCheckTemplateDto
     * @return
     */
    @PostMapping("/securityCheckTemplate/securityCheckTemplate")
    public JsonResult addSecurityCheckTemplate(SecurityCheckTemplateDto securityCheckTemplateDto,HttpServletRequest request){
        SecurityCheckTemplate savedSecurityCheckTemplate = new SecurityCheckTemplate();
        BeanUtils.copyProperties(securityCheckTemplateDto,savedSecurityCheckTemplate);
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityCheckTemplateDto.getProvinceId());

            savedSecurityCheckTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityCheckTemplateDto.getCityId());

            savedSecurityCheckTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityCheckTemplateDto.getRegionId());

            savedSecurityCheckTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityCheckTemplateDto.getOrgCategoryId());

            savedSecurityCheckTemplate.setOrgCategory(orgCategory);
        }

        if(!StringUtils.isEmpty(securityCheckTemplateDto.getCheckDate())){
            try {
                savedSecurityCheckTemplate.setCheckDate(format.parse(securityCheckTemplateDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityCheckTemplate.setCheckDate(new Date());
            }
        }
        savedSecurityCheckTemplate.setCreateDate(new Date());
        savedSecurityCheckTemplate.setCreator(getCurrentUsername(request));
        securityCheckTemplateService.addObj(savedSecurityCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑安全检查
     * @param securityCheckTemplateDto
     * @return
     */
    @PutMapping("/securityCheckTemplate/securityCheckTemplate")
    public JsonResult updateSecurityCheckTemplate(SecurityCheckTemplateDto securityCheckTemplateDto){
        SecurityCheckTemplate savedSecurityCheckTemplate = securityCheckTemplateService.queryObjById(securityCheckTemplateDto.getId());
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(securityCheckTemplateDto.getProvinceId());

            savedSecurityCheckTemplate.setProvince(province);
        }else{
            savedSecurityCheckTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(securityCheckTemplateDto.getCityId());

            savedSecurityCheckTemplate.setCity(city);
        }else{
            savedSecurityCheckTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(securityCheckTemplateDto.getRegionId());

            savedSecurityCheckTemplate.setRegion(region);
        }else{
            savedSecurityCheckTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(securityCheckTemplateDto.getOrgCategoryId());

            savedSecurityCheckTemplate.setOrgCategory(orgCategory);
        }else {
            savedSecurityCheckTemplate.setOrgCategory(null);
        }
        if(!StringUtils.isEmpty(securityCheckTemplateDto.getCheckDate())){
            try {
                savedSecurityCheckTemplate.setCheckDate(format.parse(securityCheckTemplateDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityCheckTemplate.setCheckDate(new Date());
            }
        }
        savedSecurityCheckTemplate.setName(securityCheckTemplateDto.getName());
        savedSecurityCheckTemplate.setNote(securityCheckTemplateDto.getNote());
        securityCheckTemplateService.updateObj(savedSecurityCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改安全检查内容
     * @param securityCheckTemplateDto
     * @return
     */
    @PostMapping("/securityCheckTemplate/content")
    public JsonResult updateContent(SecurityCheckTemplateDto securityCheckTemplateDto){
        SecurityCheckTemplate savedSecurityCheckTemplate = securityCheckTemplateService.queryObjById(securityCheckTemplateDto.getId());
        savedSecurityCheckTemplate.setContent(securityCheckTemplateDto.getContent());
        savedSecurityCheckTemplate.setProblems(securityCheckTemplateDto.getProblems());
        savedSecurityCheckTemplate.setCheckObject(securityCheckTemplateDto.getCheckObject());
        savedSecurityCheckTemplate.setDeptAndEmp(securityCheckTemplateDto.getDeptAndEmp());
        savedSecurityCheckTemplate.setResult(securityCheckTemplateDto.getResult());
        securityCheckTemplateService.updateObj(savedSecurityCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除安全检查
     * @param id
     * @return
     */
    @DeleteMapping("/securityCheckTemplate/securityCheckTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityCheckTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

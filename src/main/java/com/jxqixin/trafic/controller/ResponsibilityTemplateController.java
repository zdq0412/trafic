package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.ResponsibilityTemplateDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.model.ResponsibilityTemplate;
import com.jxqixin.trafic.service.IResponsibilityTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**
 * 责任书模板控制器
 */
@RestController
public class ResponsibilityTemplateController extends CommonController{
    @Autowired
    private IResponsibilityTemplateService templateService;
    /**
     * 分页查询责任书模板
     * @param nameDto
     * @return
     */
    @GetMapping("/responsibilityTemplate/responsibilityTemplatesByPage")
    public ModelMap queryResponsibilityTemplates(NameDto nameDto,HttpServletRequest request){
        Page page = templateService.findResponsibilityTemplates(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增责任书模板
     * @param templateDto
     * @return
     */
    @PostMapping("/responsibilityTemplate/responsibilityTemplate")
    public JsonResult addResponsibilityTemplate(ResponsibilityTemplateDto templateDto,HttpServletRequest request){
        ResponsibilityTemplate savedResponsibilityTemplate = new ResponsibilityTemplate();
        BeanUtils.copyProperties(templateDto,savedResponsibilityTemplate);
        if(!StringUtils.isEmpty(templateDto.getProvinceId())){
            Category province = new Category();
            province.setId(templateDto.getProvinceId());

            savedResponsibilityTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(templateDto.getCityId())){
            Category city = new Category();
            city.setId(templateDto.getCityId());

            savedResponsibilityTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(templateDto.getRegionId())){
            Category region = new Category();
            region.setId(templateDto.getRegionId());

            savedResponsibilityTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(templateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(templateDto.getOrgCategoryId());

            savedResponsibilityTemplate.setOrgCategory(orgCategory);
        }
        savedResponsibilityTemplate.setCreateDate(new Date());
        savedResponsibilityTemplate.setCreator(getCurrentUsername(request));
        savedResponsibilityTemplate.setOrg(getOrg(request));
        templateService.addObj(savedResponsibilityTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑责任书模板
     * @param templateDto
     * @return
     */
    @PutMapping("/responsibilityTemplate/responsibilityTemplate")
    public JsonResult updateResponsibilityTemplate(ResponsibilityTemplateDto templateDto){
        ResponsibilityTemplate savedResponsibilityTemplate = templateService.queryObjById(templateDto.getId());
        if(!StringUtils.isEmpty(templateDto.getProvinceId())){
            Category province = new Category();
            province.setId(templateDto.getProvinceId());

            savedResponsibilityTemplate.setProvince(province);
        }else{
            savedResponsibilityTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(templateDto.getCityId())){
            Category city = new Category();
            city.setId(templateDto.getCityId());

            savedResponsibilityTemplate.setCity(city);
        }else{
            savedResponsibilityTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(templateDto.getRegionId())){
            Category region = new Category();
            region.setId(templateDto.getRegionId());

            savedResponsibilityTemplate.setRegion(region);
        }else{
            savedResponsibilityTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(templateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(templateDto.getOrgCategoryId());

            savedResponsibilityTemplate.setOrgCategory(orgCategory);
        }else{
            savedResponsibilityTemplate.setOrgCategory(null);
        }
        savedResponsibilityTemplate.setName(templateDto.getName());
        savedResponsibilityTemplate.setNote(templateDto.getNote());
        templateService.updateObj(savedResponsibilityTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改责任书模板内容
     * @param templateDto
     * @return
     */
    @PostMapping("/responsibilityTemplate/content")
    public JsonResult updateContent(ResponsibilityTemplateDto templateDto){
        ResponsibilityTemplate savedResponsibilityTemplate = templateService.queryObjById(templateDto.getId());
        savedResponsibilityTemplate.setContent(templateDto.getContent());
        templateService.updateObj(savedResponsibilityTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除责任书模板
     * @param id
     * @return
     */
    @DeleteMapping("/responsibilityTemplate/responsibilityTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        templateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

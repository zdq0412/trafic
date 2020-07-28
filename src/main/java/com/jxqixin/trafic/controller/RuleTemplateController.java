package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.RuleTemplateDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.model.RuleTemplate;
import com.jxqixin.trafic.service.IRuleTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**
 * 模板控制器
 */
@RestController
public class RuleTemplateController extends CommonController{
    @Autowired
    private IRuleTemplateService templateService;
    /**
     * 分页查询模板
     * @param nameDto
     * @return
     */
    @GetMapping("/template/templatesByPage")
    public ModelMap queryRuleTemplates(NameDto nameDto){
        Page page = templateService.findRuleTemplates(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增模板
     * @param templateDto
     * @return
     */
    @PostMapping("/template/template")
    public JsonResult addRuleTemplate(RuleTemplateDto templateDto,HttpServletRequest request){
        RuleTemplate savedRuleTemplate = new RuleTemplate();
        BeanUtils.copyProperties(templateDto,savedRuleTemplate);
        if(!StringUtils.isEmpty(templateDto.getProvinceId())){
            Category province = new Category();
            province.setId(templateDto.getProvinceId());

            savedRuleTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(templateDto.getCityId())){
            Category city = new Category();
            city.setId(templateDto.getCityId());

            savedRuleTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(templateDto.getRegionId())){
            Category region = new Category();
            region.setId(templateDto.getRegionId());

            savedRuleTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(templateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(templateDto.getOrgCategoryId());

            savedRuleTemplate.setOrgCategory(orgCategory);
        }
        savedRuleTemplate.setCreateDate(new Date());
        savedRuleTemplate.setCreator(getCurrentUsername(request));
        templateService.addObj(savedRuleTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑模板
     * @param templateDto
     * @return
     */
    @PutMapping("/template/template")
    public JsonResult updateRuleTemplate(RuleTemplateDto templateDto){
        RuleTemplate savedRuleTemplate = templateService.queryObjById(templateDto.getId());
        if(!StringUtils.isEmpty(templateDto.getProvinceId())){
            Category province = new Category();
            province.setId(templateDto.getProvinceId());

            savedRuleTemplate.setProvince(province);
        }else{
            savedRuleTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(templateDto.getCityId())){
            Category city = new Category();
            city.setId(templateDto.getCityId());

            savedRuleTemplate.setCity(city);
        }else{
            savedRuleTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(templateDto.getRegionId())){
            Category region = new Category();
            region.setId(templateDto.getRegionId());

            savedRuleTemplate.setRegion(region);
        }else{
            savedRuleTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(templateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(templateDto.getOrgCategoryId());

            savedRuleTemplate.setOrgCategory(orgCategory);
        }else {
            savedRuleTemplate.setOrgCategory(null);
        }
        savedRuleTemplate.setName(templateDto.getName());
        savedRuleTemplate.setNote(templateDto.getNote());
        templateService.updateObj(savedRuleTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改模板内容
     * @param templateDto
     * @return
     */
    @PostMapping("/template/content")
    public JsonResult updateContent(RuleTemplateDto templateDto){
        RuleTemplate savedRuleTemplate = templateService.queryObjById(templateDto.getId());
        savedRuleTemplate.setContent(templateDto.getContent());
        templateService.updateObj(savedRuleTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除模板
     * @param id
     * @return
     */
    @DeleteMapping("/template/template/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        templateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

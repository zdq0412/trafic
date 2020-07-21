package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.RulesDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Rules;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.IOrgRulesService;
import com.jxqixin.trafic.service.IRulesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *安全规章制度文件控制器
 */
@RestController
public class RulesController extends CommonController{
    @Autowired
    private IRulesService rulesService;
    @Autowired
    private IOrgRulesService orgRulesService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询安全规章制度文件
     * @param nameDto
     * @return
     */
    @GetMapping("/rules/rulesByPage")
    public ModelMap queryRuless(NameDto nameDto,HttpServletRequest request){
        Page page = orgRulesService.findOrgRules(nameDto,getOrg(request));
        //Page page = rulesService.findRules(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增安全规章制度文件
     * @param rulesDto
     * @return
     */
    @PostMapping("/rules/rules")
    public JsonResult addRules(RulesDto rulesDto, HttpServletRequest request){
        Rules rules = new Rules();
        BeanUtils.copyProperties(rulesDto,rules);

        if(!StringUtils.isEmpty(rulesDto.getPublishDate())){
            try {
                rules.setPublishDate(format.parse(rulesDto.getPublishDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(rulesDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(rulesDto.getOrgCategoryId());
            rules.setOrgCategory(orgCategory);
        }
        rulesService.addRule(rules,getOrg(request));
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 修改安全规章制度内容
     * @param rulesDto
     * @return
     */
    @PutMapping("/rules/content")
    public JsonResult rulesContent(RulesDto rulesDto){
        Rules rules = rulesService.queryObjById(rulesDto.getId());
        rules.setContent(rulesDto.getContent());
        rulesService.updateObj(rules);
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 编辑安全规章制度文件
     * @param rulesDto
     * @return
     */
    @PutMapping("/rules/rules")
    public JsonResult updateRules(RulesDto rulesDto){
        Rules savedRules = rulesService.queryObjById(rulesDto.getId());
        savedRules.setName(rulesDto.getName());
        savedRules.setNote(rulesDto.getNote());
        if(!StringUtils.isEmpty(rulesDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(rulesDto.getOrgCategoryId());
            savedRules.setOrgCategory(orgCategory);
        }
        if(!StringUtils.isEmpty(rulesDto.getProvinceId())){
            Category province = new Category();
            province.setId(rulesDto.getProvinceId());
            savedRules.setProvince(province);
        }
        if(!StringUtils.isEmpty(rulesDto.getCityId())){
            Category city = new Category();
            city.setId(rulesDto.getCityId());
            savedRules.setCity(city);
        }
        if(!StringUtils.isEmpty(rulesDto.getRegionId())){
            Category region = new Category();
            region.setId(rulesDto.getRegionId());
            savedRules.setRegion(region);
        }
        savedRules.setPublishDepartment(rulesDto.getPublishDepartment());
        savedRules.setTimeliness(rulesDto.getTimeliness());

        if(!StringUtils.isEmpty(rulesDto.getPublishDate())){
            try {
                savedRules.setPublishDate(format.parse(rulesDto.getPublishDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        rulesService.updateObj(savedRules);
        return new JsonResult(Result.SUCCESS);
    }

    /**
     *  发布通知
     * @param id
     * @return
     */
    @GetMapping("/rules/publishRules")
    public JsonResult publishRules(String id){
        orgRulesService.publishRules(id);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除安全规章制度文件
     * @param id
     * @return
     */
    @DeleteMapping("/rules/rules/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        orgRulesService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

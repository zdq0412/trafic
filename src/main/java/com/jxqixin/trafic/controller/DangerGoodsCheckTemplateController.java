package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DangerGoodsCheckTemplateDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.DangerGoodsCheckTemplate;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.IDangerGoodsCheckTemplateService;
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
 * 危险货物运输企业安全生产隐患排查整改台账模板控制器
 */
@RestController
public class DangerGoodsCheckTemplateController extends CommonController{
    @Autowired
    private IDangerGoodsCheckTemplateService dangerGoodsCheckTemplateService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查危险货物隐患排查
     * @param nameDto
     * @return
     */
    @GetMapping("/dangerGoodsCheckTemplate/dangerGoodsCheckTemplatesByPage")
    public ModelMap queryDangerGoodsCheckTemplates(NameDto nameDto,String type){
        Page page = dangerGoodsCheckTemplateService.findDangerGoodsCheckTemplates(nameDto,type);
        return pageModelMap(page);
    }
    /**
     * 新增会议或培训
     * @param dangerGoodsCheckTemplateDto
     * @return
     */
    @PostMapping("/dangerGoodsCheckTemplate/dangerGoodsCheckTemplate")
    public JsonResult addDangerGoodsCheckTemplate(DangerGoodsCheckTemplateDto dangerGoodsCheckTemplateDto,HttpServletRequest request){
        DangerGoodsCheckTemplate savedDangerGoodsCheckTemplate = new DangerGoodsCheckTemplate();
        BeanUtils.copyProperties(dangerGoodsCheckTemplateDto,savedDangerGoodsCheckTemplate);
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(dangerGoodsCheckTemplateDto.getProvinceId());

            savedDangerGoodsCheckTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(dangerGoodsCheckTemplateDto.getCityId());

            savedDangerGoodsCheckTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(dangerGoodsCheckTemplateDto.getRegionId());

            savedDangerGoodsCheckTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(dangerGoodsCheckTemplateDto.getOrgCategoryId());

            savedDangerGoodsCheckTemplate.setOrgCategory(orgCategory);
        }

        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getCheckDate())){
            try {
                savedDangerGoodsCheckTemplate.setCheckDate(format.parse(dangerGoodsCheckTemplateDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedDangerGoodsCheckTemplate.setCheckDate(new Date());
            }
        }
        savedDangerGoodsCheckTemplate.setCreateDate(new Date());
        savedDangerGoodsCheckTemplate.setCreator(getCurrentUsername(request));
        dangerGoodsCheckTemplateService.addObj(savedDangerGoodsCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑会议或培训
     * @param dangerGoodsCheckTemplateDto
     * @return
     */
    @PutMapping("/dangerGoodsCheckTemplate/dangerGoodsCheckTemplate")
    public JsonResult updateDangerGoodsCheckTemplate(DangerGoodsCheckTemplateDto dangerGoodsCheckTemplateDto){
        DangerGoodsCheckTemplate savedDangerGoodsCheckTemplate = dangerGoodsCheckTemplateService.queryObjById(dangerGoodsCheckTemplateDto.getId());
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(dangerGoodsCheckTemplateDto.getProvinceId());

            savedDangerGoodsCheckTemplate.setProvince(province);
        }else{
            savedDangerGoodsCheckTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(dangerGoodsCheckTemplateDto.getCityId());

            savedDangerGoodsCheckTemplate.setCity(city);
        }else{
            savedDangerGoodsCheckTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(dangerGoodsCheckTemplateDto.getRegionId());

            savedDangerGoodsCheckTemplate.setRegion(region);
        }else{
            savedDangerGoodsCheckTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(dangerGoodsCheckTemplateDto.getOrgCategoryId());

            savedDangerGoodsCheckTemplate.setOrgCategory(orgCategory);
        }else {
            savedDangerGoodsCheckTemplate.setOrgCategory(null);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckTemplateDto.getCheckDate())){
            try {
                savedDangerGoodsCheckTemplate.setCheckDate(format.parse(dangerGoodsCheckTemplateDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedDangerGoodsCheckTemplate.setCheckDate(new Date());
            }
        }
        savedDangerGoodsCheckTemplate.setName(dangerGoodsCheckTemplateDto.getName());
        savedDangerGoodsCheckTemplate.setNote(dangerGoodsCheckTemplateDto.getNote());
        dangerGoodsCheckTemplateService.updateObj(savedDangerGoodsCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改会议或培训内容
     * @param dangerGoodsCheckTemplateDto
     * @return
     */
    @PostMapping("/dangerGoodsCheckTemplate/content")
    public JsonResult updateContent(DangerGoodsCheckTemplateDto dangerGoodsCheckTemplateDto){
        DangerGoodsCheckTemplate savedDangerGoodsCheckTemplate = dangerGoodsCheckTemplateService.queryObjById(dangerGoodsCheckTemplateDto.getId());
        dangerGoodsCheckTemplateService.updateObj(savedDangerGoodsCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除会议或培训
     * @param id
     * @return
     */
    @DeleteMapping("/dangerGoodsCheckTemplate/dangerGoodsCheckTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        dangerGoodsCheckTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

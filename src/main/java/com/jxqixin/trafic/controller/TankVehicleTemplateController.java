package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.TankVehicleTemplateDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.TankVehicleTemplate;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.ITankVehicleTemplateService;
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
 * 危险货物道路运输罐式车辆罐体检查记录模板表控制器
 */
@RestController
public class TankVehicleTemplateController extends CommonController{
    @Autowired
    private ITankVehicleTemplateService tankVehicleTemplateService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param nameDto
     * @return
     */
    @GetMapping("/tankVehicleTemplate/tankVehicleTemplatesByPage")
    public ModelMap queryTankVehicleTemplates(NameDto nameDto,HttpServletRequest request){
        Page page = tankVehicleTemplateService.findTankVehicleTemplates(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param tankVehicleTemplateDto
     * @return
     */
    @PostMapping("/tankVehicleTemplate/tankVehicleTemplate")
    public JsonResult addTankVehicleTemplate(TankVehicleTemplateDto tankVehicleTemplateDto,HttpServletRequest request){
        TankVehicleTemplate savedTankVehicleTemplate = new TankVehicleTemplate();
        savedTankVehicleTemplate.setName(tankVehicleTemplateDto.getName());
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(tankVehicleTemplateDto.getProvinceId());
            savedTankVehicleTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(tankVehicleTemplateDto.getCityId());
            savedTankVehicleTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(tankVehicleTemplateDto.getRegionId());
            savedTankVehicleTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(tankVehicleTemplateDto.getOrgCategoryId());
            savedTankVehicleTemplate.setOrgCategory(orgCategory);
        }
        savedTankVehicleTemplate.setCreateDate(new Date());
        savedTankVehicleTemplate.setCreator(getCurrentUsername(request));
        savedTankVehicleTemplate.setOrg(getOrg(request));
        tankVehicleTemplateService.addObj(savedTankVehicleTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param tankVehicleTemplateDto
     * @return
     */
    @PutMapping("/tankVehicleTemplate/tankVehicleTemplate")
    public JsonResult updateTankVehicleTemplate(TankVehicleTemplateDto tankVehicleTemplateDto){
        TankVehicleTemplate savedTankVehicleTemplate = tankVehicleTemplateService.queryObjById(tankVehicleTemplateDto.getId());
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(tankVehicleTemplateDto.getProvinceId());

            savedTankVehicleTemplate.setProvince(province);
        }else{
            savedTankVehicleTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(tankVehicleTemplateDto.getCityId());

            savedTankVehicleTemplate.setCity(city);
        }else{
            savedTankVehicleTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(tankVehicleTemplateDto.getRegionId());

            savedTankVehicleTemplate.setRegion(region);
        }else{
            savedTankVehicleTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(tankVehicleTemplateDto.getOrgCategoryId());

            savedTankVehicleTemplate.setOrgCategory(orgCategory);
        }else {
            savedTankVehicleTemplate.setOrgCategory(null);
        }
        if(!StringUtils.isEmpty(tankVehicleTemplateDto.getCheckDate())){
            try {
                savedTankVehicleTemplate.setCheckDate(format.parse(tankVehicleTemplateDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedTankVehicleTemplate.setCheckDate(new Date());
            }
        }
        savedTankVehicleTemplate.setName(tankVehicleTemplateDto.getName());
        savedTankVehicleTemplate.setNote(tankVehicleTemplateDto.getNote());
        tankVehicleTemplateService.updateObj(savedTankVehicleTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改
     * @param tankVehicleTemplateDto
     * @return
     */
    @PostMapping("/tankVehicleTemplate/content")
    public JsonResult updateContent(TankVehicleTemplateDto tankVehicleTemplateDto){
        TankVehicleTemplate savedTankVehicleTemplate = tankVehicleTemplateService.queryObjById(tankVehicleTemplateDto.getId());
        savedTankVehicleTemplate.setCarNo(tankVehicleTemplateDto.getCarNo());
        savedTankVehicleTemplate.setCheckItem1(tankVehicleTemplateDto.getCheckItem1());
        savedTankVehicleTemplate.setCheckItem2(tankVehicleTemplateDto.getCheckItem2());
        savedTankVehicleTemplate.setCheckItem3(tankVehicleTemplateDto.getCheckItem3());
        savedTankVehicleTemplate.setCheckItem4(tankVehicleTemplateDto.getCheckItem4());
        savedTankVehicleTemplate.setCheckItem5(tankVehicleTemplateDto.getCheckItem5());
        savedTankVehicleTemplate.setCheckItem6(tankVehicleTemplateDto.getCheckItem6());
        savedTankVehicleTemplate.setCheckItem7(tankVehicleTemplateDto.getCheckItem7());
        savedTankVehicleTemplate.setCheckItem8(tankVehicleTemplateDto.getCheckItem8());
        savedTankVehicleTemplate.setCheckItem9(tankVehicleTemplateDto.getCheckItem9());
        savedTankVehicleTemplate.setCheckItem10(tankVehicleTemplateDto.getCheckItem10());
        savedTankVehicleTemplate.setCheckItem11(tankVehicleTemplateDto.getCheckItem11());
        savedTankVehicleTemplate.setCheckItem12(tankVehicleTemplateDto.getCheckItem12());
        savedTankVehicleTemplate.setCheckItem13(tankVehicleTemplateDto.getCheckItem13());
        savedTankVehicleTemplate.setCheckItem14(tankVehicleTemplateDto.getCheckItem14());
        savedTankVehicleTemplate.setCheckItem15(tankVehicleTemplateDto.getCheckItem15());

        savedTankVehicleTemplate.setCheckItem1Msg(tankVehicleTemplateDto.getCheckItem1Msg());
        savedTankVehicleTemplate.setCheckItem2Msg(tankVehicleTemplateDto.getCheckItem2Msg());
        savedTankVehicleTemplate.setCheckItem3Msg(tankVehicleTemplateDto.getCheckItem3Msg());
        savedTankVehicleTemplate.setCheckItem4Msg(tankVehicleTemplateDto.getCheckItem4Msg());
        savedTankVehicleTemplate.setCheckItem5Msg(tankVehicleTemplateDto.getCheckItem5Msg());
        savedTankVehicleTemplate.setCheckItem6Msg(tankVehicleTemplateDto.getCheckItem6Msg());
        savedTankVehicleTemplate.setCheckItem7Msg(tankVehicleTemplateDto.getCheckItem7Msg());
        savedTankVehicleTemplate.setCheckItem8Msg(tankVehicleTemplateDto.getCheckItem8Msg());
        savedTankVehicleTemplate.setCheckItem9Msg(tankVehicleTemplateDto.getCheckItem9Msg());
        savedTankVehicleTemplate.setCheckItem10Msg(tankVehicleTemplateDto.getCheckItem10Msg());
        savedTankVehicleTemplate.setCheckItem11Msg(tankVehicleTemplateDto.getCheckItem11Msg());
        savedTankVehicleTemplate.setCheckItem12Msg(tankVehicleTemplateDto.getCheckItem12Msg());
        savedTankVehicleTemplate.setCheckItem13Msg(tankVehicleTemplateDto.getCheckItem13Msg());
        savedTankVehicleTemplate.setCheckItem14Msg(tankVehicleTemplateDto.getCheckItem14Msg());
        savedTankVehicleTemplate.setCheckItem15Msg(tankVehicleTemplateDto.getCheckItem15Msg());
        savedTankVehicleTemplate.setName(tankVehicleTemplateDto.getName());
        savedTankVehicleTemplate.setSuggestion(tankVehicleTemplateDto.getSuggestion());
        tankVehicleTemplateService.updateObj(savedTankVehicleTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/tankVehicleTemplate/tankVehicleTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        tankVehicleTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

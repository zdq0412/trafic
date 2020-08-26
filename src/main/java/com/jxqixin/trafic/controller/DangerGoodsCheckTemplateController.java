package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DangerGoodsCheckTemplateDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IDangerGoodsCheckTemplateService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * @param dangerGoodsCheckTemplateDto
     * @return
     */
    @GetMapping("/dangerGoodsCheckTemplate/dangerGoodsCheckTemplatesByPage")
    public ModelMap queryDangerGoodsCheckTemplates(DangerGoodsCheckTemplateDto dangerGoodsCheckTemplateDto,HttpServletRequest request){
        Page page = dangerGoodsCheckTemplateService.findDangerGoodsCheckTemplates(dangerGoodsCheckTemplateDto,getOrg(request));
        return pageModelMap(page);
    }

    /**
     * 新增危险货物隐患
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
        savedDangerGoodsCheckTemplate.setCreateDate(new Date());
        savedDangerGoodsCheckTemplate.setCreator(getCurrentUsername(request));
        savedDangerGoodsCheckTemplate.setOrg(getOrg(request));
        dangerGoodsCheckTemplateService.addObj(savedDangerGoodsCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑危险货物隐患
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
        savedDangerGoodsCheckTemplate.setName(dangerGoodsCheckTemplateDto.getName());
        savedDangerGoodsCheckTemplate.setNote(dangerGoodsCheckTemplateDto.getNote());
        dangerGoodsCheckTemplateService.updateObj(savedDangerGoodsCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改危险货物隐患内容
     * @param id 危险货物隐患ID
     * @param details 危险货物隐患详情字符串，字段之间以|分割，对象之间以#分割
     * @return
     */
    @PostMapping("/dangerGoodsCheckTemplate/content")
    public JsonResult updateContent(String id, String details){
        DangerGoodsCheckTemplate template = new DangerGoodsCheckTemplate();
        template.setId(id);

        List<DangerGoodsCheckDetail> detailList = new ArrayList<>();
        if(!StringUtils.isEmpty(details)){
            String[] detailObjs = details.split("#");
            if(detailObjs!=null && detailObjs.length>0){
                for(int i = 0;i<detailObjs.length;i++){
                    DangerGoodsCheckDetail detail = new DangerGoodsCheckDetail();
                    detail.setDangerGoodsCheckTemplate(template);
                    String[] fields = detailObjs[i].split("\\|");
                    if(fields!=null &&fields.length>0){
                        String checkDate = fields[0];
                        String person = fields[1];
                        String cancelDate = fields[2];
                        String checkedOrg = fields[3];
                        String hiddenDanger = fields[4];
                        String correctiveAction = fields[5];
                        String timelimit = fields[6];
                        String endTime = fields[7];
                        String note = fields[8];
                        detail.setCheckedOrg(checkedOrg);
                        detail.setHiddenDanger(hiddenDanger);
                        detail.setCorrectiveAction(correctiveAction);
                        detail.setTimelimit(timelimit);
                        detail.setDetailNote(note);
                        if(!StringUtils.isEmpty(checkDate)){
                            try {
                                detail.setCheckDate(format.parse(checkDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!StringUtils.isEmpty(cancelDate)){
                            try {
                                detail.setCancelDate(format.parse(cancelDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!StringUtils.isEmpty(endTime)){
                            try {
                                detail.setEndTime(format.parse(endTime));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        detail.setPerson(person);

                    }
                    detailList.add(detail);
                }
            }
        }
        dangerGoodsCheckTemplateService.updateDetails(id,detailList);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除危险货物隐患
     * @param id
     * @return
     */
    @DeleteMapping("/dangerGoodsCheckTemplate/dangerGoodsCheckTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        dangerGoodsCheckTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

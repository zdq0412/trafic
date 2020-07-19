package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.LawDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Law;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.ILawService;
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
 * 法律法规文件控制器
 */
@RestController
public class LawController extends CommonController{
    @Autowired
    private ILawService lawService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询法律法规文件
     * @param nameDto
     * @return
     */
    @GetMapping("/law/lawsByPage")
    public ModelMap queryLaws(NameDto nameDto,HttpServletRequest request){
        Page page = lawService.findLaws(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增法律法规文件
     * @param lawDto
     * @return
     */
    @PostMapping("/law/law")
    public JsonResult addLaw(LawDto lawDto, HttpServletRequest request){
        Law law = new Law();
        BeanUtils.copyProperties(lawDto,law);

        if(!StringUtils.isEmpty(lawDto.getPublishDate())){
            try {
                law.setPublishDate(format.parse(lawDto.getPublishDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(lawDto.getImplementDate())){
            try {
                law.setImplementDate(format.parse(lawDto.getImplementDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(lawDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(lawDto.getOrgCategoryId());
            law.setOrgCategory(orgCategory);
        }
        lawService.addLaw(law,getOrg(request));
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 修改法律法规内容
     * @param lawDto
     * @return
     */
    @PutMapping("/law/content")
    public JsonResult lawContent(LawDto lawDto){
        Law law = lawService.queryObjById(lawDto.getId());
        law.setContent(lawDto.getContent());
        lawService.updateObj(law);
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 编辑法律法规文件
     * @param lawDto
     * @return
     */
    @PutMapping("/law/law")
    public JsonResult updateLaw(LawDto lawDto){
        Law savedLaw = lawService.queryObjById(lawDto.getId());
        savedLaw.setName(lawDto.getName());
        savedLaw.setNote(lawDto.getNote());
        savedLaw.setCity(lawDto.getCity());
        if(!StringUtils.isEmpty(lawDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(lawDto.getOrgCategoryId());
            savedLaw.setOrgCategory(orgCategory);
        }
        savedLaw.setProvince(lawDto.getProvince());
        savedLaw.setRegion(lawDto.getRegion());
        savedLaw.setPublishDepartment(lawDto.getPublishDepartment());
        savedLaw.setTimeliness(lawDto.getTimeliness());

        if(!StringUtils.isEmpty(lawDto.getPublishDate())){
            try {
                savedLaw.setPublishDate(format.parse(lawDto.getPublishDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(lawDto.getImplementDate())){
            try {
                savedLaw.setImplementDate(format.parse(lawDto.getImplementDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        lawService.updateObj(savedLaw);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除法律法规文件
     * @param id
     * @return
     */
    @DeleteMapping("/law/law/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        lawService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.TrainingTemplateDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.TrainingTemplate;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.ITrainingTemplateService;
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
 * 培训控制器
 */
@RestController
public class TrainingTemplateController extends CommonController{
    @Autowired
    private ITrainingTemplateService trainingTemplateService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询培训
     * @param nameDto
     * @return
     */
    @GetMapping("/trainingTemplate/trainingTemplatesByPage")
    public ModelMap queryTrainingTemplates(NameDto nameDto,HttpServletRequest request){
        Page page = trainingTemplateService.findTrainingTemplates(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增培训
     * @param trainingTemplateDto
     * @return
     */
    @PostMapping("/trainingTemplate/trainingTemplate")
    public JsonResult addTrainingTemplate(TrainingTemplateDto trainingTemplateDto,HttpServletRequest request){
        TrainingTemplate savedTrainingTemplate = new TrainingTemplate();
        BeanUtils.copyProperties(trainingTemplateDto,savedTrainingTemplate);
        if(!StringUtils.isEmpty(trainingTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(trainingTemplateDto.getProvinceId());

            savedTrainingTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(trainingTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(trainingTemplateDto.getCityId());

            savedTrainingTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(trainingTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(trainingTemplateDto.getRegionId());

            savedTrainingTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(trainingTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(trainingTemplateDto.getOrgCategoryId());

            savedTrainingTemplate.setOrgCategory(orgCategory);
        }

        if(!StringUtils.isEmpty(trainingTemplateDto.getTrainingDate())){
            try {
                savedTrainingTemplate.setTrainingDate(format.parse(trainingTemplateDto.getTrainingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedTrainingTemplate.setTrainingDate(new Date());
            }
        }
        savedTrainingTemplate.setCreateDate(new Date());
        savedTrainingTemplate.setCreator(getCurrentUsername(request));
        savedTrainingTemplate.setOrg(getOrg(request));
        trainingTemplateService.addObj(savedTrainingTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑培训
     * @param trainingTemplateDto
     * @return
     */
    @PutMapping("/trainingTemplate/trainingTemplate")
    public JsonResult updateTrainingTemplate(TrainingTemplateDto trainingTemplateDto){
        TrainingTemplate savedTrainingTemplate = trainingTemplateService.queryObjById(trainingTemplateDto.getId());
        if(!StringUtils.isEmpty(trainingTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(trainingTemplateDto.getProvinceId());

            savedTrainingTemplate.setProvince(province);
        }else{
            savedTrainingTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(trainingTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(trainingTemplateDto.getCityId());

            savedTrainingTemplate.setCity(city);
        }else{
            savedTrainingTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(trainingTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(trainingTemplateDto.getRegionId());

            savedTrainingTemplate.setRegion(region);
        }else{
            savedTrainingTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(trainingTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(trainingTemplateDto.getOrgCategoryId());

            savedTrainingTemplate.setOrgCategory(orgCategory);
        }else {
            savedTrainingTemplate.setOrgCategory(null);
        }
        if(!StringUtils.isEmpty(trainingTemplateDto.getTrainingDate())){
            try {
                savedTrainingTemplate.setTrainingDate(format.parse(trainingTemplateDto.getTrainingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedTrainingTemplate.setTrainingDate(new Date());
            }
        }
        savedTrainingTemplate.setName(trainingTemplateDto.getName());
        savedTrainingTemplate.setNote(trainingTemplateDto.getNote());
        trainingTemplateService.updateObj(savedTrainingTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改培训内容
     * @param trainingTemplateDto
     * @return
     */
    @PostMapping("/trainingTemplate/content")
    public JsonResult updateContent(TrainingTemplateDto trainingTemplateDto){
        TrainingTemplate savedTrainingTemplate = trainingTemplateService.queryObjById(trainingTemplateDto.getId());
        savedTrainingTemplate.setContent(trainingTemplateDto.getContent());
        savedTrainingTemplate.setTrainingName(trainingTemplateDto.getTrainingName());
        savedTrainingTemplate.setTrainingPlace(trainingTemplateDto.getTrainingPlace());
        savedTrainingTemplate.setAttendance(trainingTemplateDto.getAttendance());
        savedTrainingTemplate.setRealAttendance(trainingTemplateDto.getRealAttendance());
        savedTrainingTemplate.setAttendants(trainingTemplateDto.getAttendants());
        savedTrainingTemplate.setPresident(trainingTemplateDto.getPresident());
        savedTrainingTemplate.setRecorder(trainingTemplateDto.getRecorder());
        trainingTemplateService.updateObj(savedTrainingTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除会议或培训
     * @param id
     * @return
     */
    @DeleteMapping("/trainingTemplate/trainingTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        trainingTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.MeetingTemplateDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.model.MeetingTemplate;
import com.jxqixin.trafic.service.IMeetingTemplateService;
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
 * 会议会议或培训控制器
 */
@RestController
public class MeetingTemplateController extends CommonController{
    @Autowired
    private IMeetingTemplateService meetingTemplateService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询会议或培训
     * @param nameDto
     * @return
     */
    @GetMapping("/meetingTemplate/meetingTemplatesByPage")
    public ModelMap queryMeetingTemplates(NameDto nameDto,HttpServletRequest request){
        Page page = meetingTemplateService.findMeetingTemplates(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增会议或培训
     * @param meetingTemplateDto
     * @return
     */
    @PostMapping("/meetingTemplate/meetingTemplate")
    public JsonResult addMeetingTemplate(MeetingTemplateDto meetingTemplateDto,HttpServletRequest request){
        MeetingTemplate savedMeetingTemplate = new MeetingTemplate();
        BeanUtils.copyProperties(meetingTemplateDto,savedMeetingTemplate);
        if(!StringUtils.isEmpty(meetingTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(meetingTemplateDto.getProvinceId());

            savedMeetingTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(meetingTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(meetingTemplateDto.getCityId());

            savedMeetingTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(meetingTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(meetingTemplateDto.getRegionId());

            savedMeetingTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(meetingTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(meetingTemplateDto.getOrgCategoryId());

            savedMeetingTemplate.setOrgCategory(orgCategory);
        }

        if(!StringUtils.isEmpty(meetingTemplateDto.getMeetingDate())){
            try {
                savedMeetingTemplate.setMeetingDate(format.parse(meetingTemplateDto.getMeetingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedMeetingTemplate.setMeetingDate(new Date());
            }
        }
        savedMeetingTemplate.setCreateDate(new Date());
        savedMeetingTemplate.setCreator(getCurrentUsername(request));
        savedMeetingTemplate.setOrg(getOrg(request));
        meetingTemplateService.addObj(savedMeetingTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑会议或培训
     * @param meetingTemplateDto
     * @return
     */
    @PutMapping("/meetingTemplate/meetingTemplate")
    public JsonResult updateMeetingTemplate(MeetingTemplateDto meetingTemplateDto){
        MeetingTemplate savedMeetingTemplate = meetingTemplateService.queryObjById(meetingTemplateDto.getId());
        if(!StringUtils.isEmpty(meetingTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(meetingTemplateDto.getProvinceId());

            savedMeetingTemplate.setProvince(province);
        }else{
            savedMeetingTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(meetingTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(meetingTemplateDto.getCityId());

            savedMeetingTemplate.setCity(city);
        }else{
            savedMeetingTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(meetingTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(meetingTemplateDto.getRegionId());

            savedMeetingTemplate.setRegion(region);
        }else{
            savedMeetingTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(meetingTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(meetingTemplateDto.getOrgCategoryId());

            savedMeetingTemplate.setOrgCategory(orgCategory);
        }else {
            savedMeetingTemplate.setOrgCategory(null);
        }
        if(!StringUtils.isEmpty(meetingTemplateDto.getMeetingDate())){
            try {
                savedMeetingTemplate.setMeetingDate(format.parse(meetingTemplateDto.getMeetingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedMeetingTemplate.setMeetingDate(new Date());
            }
        }
        savedMeetingTemplate.setName(meetingTemplateDto.getName());
        savedMeetingTemplate.setNote(meetingTemplateDto.getNote());
        meetingTemplateService.updateObj(savedMeetingTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改会议内容
     * @param meetingTemplateDto
     * @return
     */
    @PostMapping("/meetingTemplate/content")
    public JsonResult updateContent(MeetingTemplateDto meetingTemplateDto){
        MeetingTemplate savedMeetingTemplate = meetingTemplateService.queryObjById(meetingTemplateDto.getId());
        savedMeetingTemplate.setContent(meetingTemplateDto.getContent());
        savedMeetingTemplate.setMeetingName(meetingTemplateDto.getMeetingName());
        savedMeetingTemplate.setMeetingPlace(meetingTemplateDto.getMeetingPlace());
        savedMeetingTemplate.setAttendants(meetingTemplateDto.getAttendants());
        savedMeetingTemplate.setPresident(meetingTemplateDto.getPresident());
        savedMeetingTemplate.setRecorder(meetingTemplateDto.getRecorder());
        savedMeetingTemplate.setFinalDecision(meetingTemplateDto.getFinalDecision());
        meetingTemplateService.updateObj(savedMeetingTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除会议或培训
     * @param id
     * @return
     */
    @DeleteMapping("/meetingTemplate/meetingTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        meetingTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

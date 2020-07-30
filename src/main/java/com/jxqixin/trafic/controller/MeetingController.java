package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.MeetingDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Meeting;
import com.jxqixin.trafic.service.IMeetingService;
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
public class MeetingController extends CommonController{
    @Autowired
    private IMeetingService meetingService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询会议或培训
     * @param nameDto
     * @return
     */
    @GetMapping("/meeting/meetingsByPage")
    public ModelMap queryMeetings(NameDto nameDto,String type){
        Page page = meetingService.findMeetings(nameDto,type);
        return pageModelMap(page);
    }
    /**
     * 新增会议或培训
     * @param meetingDto
     * @return
     */
    @PostMapping("/meeting/meeting")
    public JsonResult addMeeting(MeetingDto meetingDto,HttpServletRequest request){
        Meeting savedMeeting = new Meeting();
        BeanUtils.copyProperties(meetingDto,savedMeeting);
        if(!StringUtils.isEmpty(meetingDto.getMeetingDate())){
            try {
                savedMeeting.setMeetingDate(format.parse(meetingDto.getMeetingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedMeeting.setMeetingDate(new Date());
            }
        }
        savedMeeting.setCreateDate(new Date());
        savedMeeting.setOrg(getOrg(request ));
        savedMeeting.setCreator(getCurrentUsername(request));
        meetingService.addObj(savedMeeting);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑会议或培训
     * @param meetingDto
     * @return
     */
    @PutMapping("/meeting/meeting")
    public JsonResult updateMeeting(MeetingDto meetingDto){
        Meeting savedMeeting = meetingService.queryObjById(meetingDto.getId());
        if(!StringUtils.isEmpty(meetingDto.getMeetingDate())){
            try {
                savedMeeting.setMeetingDate(format.parse(meetingDto.getMeetingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedMeeting.setMeetingDate(new Date());
            }
        }
        savedMeeting.setName(meetingDto.getName());
        savedMeeting.setNote(meetingDto.getNote());
        meetingService.updateObj(savedMeeting);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改会议或培训内容
     * @param meetingDto
     * @return
     */
    @PostMapping("/meeting/content")
    public JsonResult updateContent(MeetingDto meetingDto){
        Meeting savedMeeting = meetingService.queryObjById(meetingDto.getId());
        savedMeeting.setContent(meetingDto.getContent());
        savedMeeting.setTheme(meetingDto.getTheme());
        savedMeeting.setMeetingName(meetingDto.getMeetingName());
        savedMeeting.setMeetingPlace(meetingDto.getMeetingPlace());
        savedMeeting.setAbsent(meetingDto.getAbsent());
        savedMeeting.setAttendance(meetingDto.getAttendance());
        savedMeeting.setAttendants(meetingDto.getAttendants());
        savedMeeting.setProblems(meetingDto.getProblems());
        savedMeeting.setMethods(meetingDto.getMethods());
        savedMeeting.setPresident(meetingDto.getPresident());
        savedMeeting.setRecorder(meetingDto.getRecorder());
        savedMeeting.setTemplateNote(meetingDto.getTemplateNote());
        meetingService.updateObj(savedMeeting);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除会议或培训
     * @param id
     * @return
     */
    @DeleteMapping("/meeting/meeting/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        meetingService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.MeetingDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Meeting;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IMeetingService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 会议控制器
 */
@RestController
public class MeetingController extends CommonController{
    @Autowired
    private IMeetingService meetingService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询会议
     * @return
     */
    @GetMapping("/meeting/meetingsByPage")
    public ModelMap queryMeetings(MeetingDto meetingDto,HttpServletRequest request){
        Page page = meetingService.findMeetings(meetingDto,getOrg(request));
        return pageModelMap(page);
    }

    /**
     * 引入模板
     * @param templateId
     * @return
     */
    @PostMapping("/meeting/template")
    public JsonResult importTemplate(String templateId, HttpServletRequest request){
        meetingService.importTemplate(templateId,getOrg(request),getCurrentUsername(request));
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 新增会议
     * @param meetingDto
     * @return
     */
    @PostMapping("/meeting/meeting")
    public JsonResult addMeeting(MeetingDto meetingDto,HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Meeting savedMeeting = new Meeting();
        BeanUtils.copyProperties(meetingDto,savedMeeting);
        if(!StringUtils.isEmpty(meetingDto.getMeetingDate())){
            try {
                savedMeeting.setMeetingDate(format.parse(meetingDto.getMeetingDate()));
                //savedMeeting.setEndMeetingDate(savedMeeting.getMeetingDate());
            } catch (ParseException e) {
                e.printStackTrace();
                savedMeeting.setMeetingDate(new Date());
            }
        }
        savedMeeting.setRecorder(user.getRealname());
        savedMeeting.setCreateDate(new Date());
        savedMeeting.setOrg(getOrg(request ));
        savedMeeting.setCreator(getCurrentUsername(request));
        meetingService.addObj(savedMeeting);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑会议
     * @param meetingDto
     * @return
     */
    @PostMapping("/meeting/updateMeeting")
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
        if(!StringUtils.isEmpty(meetingDto.getEndMeetingDate())){
            Calendar meetingDateCal = Calendar.getInstance();
            meetingDateCal.setTime(savedMeeting.getMeetingDate());

            Calendar endMeetingDateCal = Calendar.getInstance();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                endMeetingDateCal.setTime(sdf.parse(meetingDto.getEndMeetingDate()));
                meetingDateCal.set(Calendar.HOUR_OF_DAY,endMeetingDateCal.get(Calendar.HOUR_OF_DAY));
                meetingDateCal.set(Calendar.MINUTE,endMeetingDateCal.get(Calendar.MINUTE));

                savedMeeting.setEndMeetingDate(meetingDateCal.getTime());
            } catch (ParseException e) {
                savedMeeting.setEndMeetingDate(savedMeeting.getMeetingDate());
                e.printStackTrace();
            }
        }
        savedMeeting.setContent(meetingDto.getContent());
        savedMeeting.setMeetingName(meetingDto.getMeetingName());
        savedMeeting.setMeetingPlace(meetingDto.getMeetingPlace());
        savedMeeting.setAttendants(meetingDto.getAttendants());
        savedMeeting.setPresident(meetingDto.getPresident());
        savedMeeting.setRecorder(meetingDto.getRecorder());
        savedMeeting.setFinalDecision(meetingDto.getFinalDecision());
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

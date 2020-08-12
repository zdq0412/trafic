package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.GoalExaminationDto;
import com.jxqixin.trafic.model.GoalExamination;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IGoalExaminationService;
import com.jxqixin.trafic.service.IUserService;
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
import java.util.Date;

/**
 * 安全目标考核控制器
 */
@RestController
public class GoalExaminationController extends CommonController{
    @Autowired
    private IGoalExaminationService goalExaminationService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param goalExaminationDto
     * @return
     */
    @GetMapping("/goalExamination/goalExaminationsByPage")
    public ModelMap queryGoalExaminations(GoalExaminationDto goalExaminationDto){
        Page page = goalExaminationService.findGoalExaminations(goalExaminationDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param goalExaminationDto
     * @return
     */
    @PostMapping("/goalExamination/goalExamination")
    public JsonResult addGoalExamination(GoalExaminationDto goalExaminationDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        GoalExamination savedGoalExamination = new GoalExamination();
        BeanUtils.copyProperties(goalExaminationDto,savedGoalExamination);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedGoalExamination.setUrl(urlMapping);
            savedGoalExamination.setRealPath(savedFile.getAbsolutePath());
            savedGoalExamination.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedGoalExamination.setCreateDate(new Date());
        if(!StringUtils.isEmpty(goalExaminationDto.getBeginDate())){
            try {
                savedGoalExamination.setBeginDate(format.parse(goalExaminationDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedGoalExamination.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(goalExaminationDto.getEndDate())){
            try {
                savedGoalExamination.setEndDate(format.parse(goalExaminationDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedGoalExamination.setEndDate(null);
            }
        }
        savedGoalExamination.setOrg(getOrg(request));
        savedGoalExamination.setCreator(getCurrentUsername(request));
        goalExaminationService.addObj(savedGoalExamination);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param goalExaminationDto
     * @return
     */
    @PostMapping("/goalExamination/updateGoalExamination")
    public JsonResult updateGoalExamination(GoalExaminationDto goalExaminationDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        GoalExamination savedGoalExamination = goalExaminationService.queryObjById(goalExaminationDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedGoalExamination.setUrl(urlMapping);
            savedGoalExamination.setRealPath(savedFile.getAbsolutePath());
            savedGoalExamination.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(goalExaminationDto.getBeginDate())){
            try {
                savedGoalExamination.setBeginDate(format.parse(goalExaminationDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedGoalExamination.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(goalExaminationDto.getEndDate())){
            try {
                savedGoalExamination.setEndDate(format.parse(goalExaminationDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedGoalExamination.setEndDate(null);
            }
        }
        savedGoalExamination.setName(goalExaminationDto.getName());
        savedGoalExamination.setNote(goalExaminationDto.getNote());
        goalExaminationService.updateObj(savedGoalExamination);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/goalExamination/goalExamination/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        goalExaminationService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

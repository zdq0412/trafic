package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.HealthyRecordDto;
import com.jxqixin.trafic.model.HealthyRecord;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IHealthyRecordService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
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
 * 职业健康记录控制器
 */
@RestController
public class HealthyRecordController extends CommonController{
    @Autowired
    private IHealthyRecordService healthyRecordService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param healthyRecordDto
     * @return
     */
    @GetMapping("/healthyRecord/healthyRecordsByPage")
    public ModelMap queryHealthyRecords(HealthyRecordDto healthyRecordDto){
        Page page = healthyRecordService.findHealthyRecords(healthyRecordDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param healthyRecordDto
     * @return
     */
    @PostMapping("/healthyRecord/healthyRecord")
    public JsonResult addHealthyRecord(HealthyRecordDto healthyRecordDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        HealthyRecord savedHealthyRecord = new HealthyRecord();
        BeanUtils.copyProperties(healthyRecordDto,savedHealthyRecord);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedHealthyRecord.setUrl(urlMapping);
            savedHealthyRecord.setRealPath(savedFile.getAbsolutePath());
            savedHealthyRecord.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedHealthyRecord.setCreateDate(new Date());
        if(!StringUtils.isEmpty(healthyRecordDto.getBeginDate())){
            try {
                savedHealthyRecord.setBeginDate(format.parse(healthyRecordDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedHealthyRecord.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(healthyRecordDto.getEndDate())){
            try {
                savedHealthyRecord.setEndDate(format.parse(healthyRecordDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedHealthyRecord.setEndDate(null);
            }
        }
        savedHealthyRecord.setOrg(getOrg(request));
        savedHealthyRecord.setCreator(getCurrentUsername(request));
        healthyRecordService.addObj(savedHealthyRecord);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param healthyRecordDto
     * @return
     */
    @PostMapping("/healthyRecord/updateHealthyRecord")
    public JsonResult updateHealthyRecord(HealthyRecordDto healthyRecordDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        HealthyRecord savedHealthyRecord = healthyRecordService.queryObjById(healthyRecordDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedHealthyRecord.getRealPath())){
                FileUtil.deleteFile(savedHealthyRecord.getRealPath());
            }
            savedHealthyRecord.setUrl(urlMapping);
            savedHealthyRecord.setRealPath(savedFile.getAbsolutePath());
            savedHealthyRecord.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(healthyRecordDto.getBeginDate())){
            try {
                savedHealthyRecord.setBeginDate(format.parse(healthyRecordDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedHealthyRecord.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(healthyRecordDto.getEndDate())){
            try {
                savedHealthyRecord.setEndDate(format.parse(healthyRecordDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedHealthyRecord.setEndDate(null);
            }
        }
        savedHealthyRecord.setName(healthyRecordDto.getName());
        savedHealthyRecord.setNote(healthyRecordDto.getNote());
        healthyRecordService.updateObj(savedHealthyRecord);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param healthyRecordDto
     * @return
     */
    @PostMapping("/healthyRecord/updateHealthyRecordNoFile")
    public JsonResult updateHealthyRecordNoFile(HealthyRecordDto healthyRecordDto, HttpServletRequest request){
        HealthyRecord savedHealthyRecord = healthyRecordService.queryObjById(healthyRecordDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(healthyRecordDto.getBeginDate())){
            try {
                savedHealthyRecord.setBeginDate(format.parse(healthyRecordDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedHealthyRecord.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(healthyRecordDto.getEndDate())){
            try {
                savedHealthyRecord.setEndDate(format.parse(healthyRecordDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedHealthyRecord.setEndDate(null);
            }
        }
        savedHealthyRecord.setName(healthyRecordDto.getName());
        savedHealthyRecord.setNote(healthyRecordDto.getNote());
        healthyRecordService.updateObj(savedHealthyRecord);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/healthyRecord/healthyRecord/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        healthyRecordService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

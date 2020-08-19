package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.PreplanDrillRecordDto;
import com.jxqixin.trafic.model.EmergencyPlanBak;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.PreplanDrillRecord;
import com.jxqixin.trafic.service.IPreplanDrillRecordService;
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
 * 应急救援预案演练记录控制器
 */
@RestController
public class PreplanDrillRecordController extends CommonController{
    @Autowired
    private IPreplanDrillRecordService preplanDrillRecordService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param preplanDrillRecordDto
     * @return
     */
    @GetMapping("/preplanDrillRecord/preplanDrillRecordsByPage")
    public ModelMap queryPreplanDrillRecords(PreplanDrillRecordDto preplanDrillRecordDto){
        Page page = preplanDrillRecordService.findPreplanDrillRecords(preplanDrillRecordDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param preplanDrillRecordDto
     * @return
     */
    @PostMapping("/preplanDrillRecord/preplanDrillRecord")
    public JsonResult addPreplanDrillRecord(PreplanDrillRecordDto preplanDrillRecordDto,HttpServletRequest request){
        PreplanDrillRecord preplanDrillRecord = new PreplanDrillRecord();
        BeanUtils.copyProperties(preplanDrillRecordDto,preplanDrillRecord);
        if(!StringUtils.isEmpty(preplanDrillRecordDto.getEmergencyPlanBakId())){
            EmergencyPlanBak emergencyPlanBak = new EmergencyPlanBak();
            emergencyPlanBak.setId(preplanDrillRecordDto.getEmergencyPlanBakId());

            preplanDrillRecord.setEmergencyPlanBak(emergencyPlanBak);
        }

        if(!StringUtils.isEmpty(preplanDrillRecordDto.getDevelopDate())){
            try {
                preplanDrillRecord.setDevelopDate(format.parse(preplanDrillRecordDto.getDevelopDate()));
            } catch (ParseException e) {
                preplanDrillRecord.setDevelopDate(null);
            }
        }
        preplanDrillRecord.setCreateDate(new Date());
        preplanDrillRecordService.addObj(preplanDrillRecord);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param preplanDrillRecordDto
     * @return
     */
    @PutMapping("/preplanDrillRecord/preplanDrillRecord")
    public JsonResult updatePreplanDrillRecord(PreplanDrillRecordDto preplanDrillRecordDto){
        PreplanDrillRecord savedPreplanDrillRecord = preplanDrillRecordService.queryObjById(preplanDrillRecordDto.getId());
        try {
            savedPreplanDrillRecord.setDevelopDate(format.parse(preplanDrillRecordDto.getDevelopDate()));
        } catch (Exception e) {
            savedPreplanDrillRecord.setDevelopDate(null);
        }
        savedPreplanDrillRecord.setName(preplanDrillRecordDto.getName());
        savedPreplanDrillRecord.setType(preplanDrillRecordDto.getType());
        savedPreplanDrillRecord.setNote(preplanDrillRecordDto.getNote());
        preplanDrillRecordService.updateObj(savedPreplanDrillRecord);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/preplanDrillRecord/preplanDrillRecord/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        preplanDrillRecordService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

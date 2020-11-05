package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.JobHistoryDto;
import com.jxqixin.trafic.model.JobHistory;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IJobHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 人员档案，从业经历控制器
 */
@RestController
public class JobHistoryController extends CommonController{
    @Autowired
    private IJobHistoryService jobHistoryService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param jobHistoryDto
     * @return
     */
    @GetMapping("/jobHistory/jobHistorysByPage")
    public ModelMap queryJobHistorys(JobHistoryDto jobHistoryDto){
        Page page = jobHistoryService.findJobHistorys(jobHistoryDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param jobHistoryDto
     * @return
     */
    @PostMapping("/jobHistory/jobHistory")
    public JsonResult addJobHistory(JobHistoryDto jobHistoryDto){
        JsonResult jsonResult = checkEmpId(jobHistoryDto.getEmpId());
        if(jsonResult.getResult().getResultCode()!=200){
            return jsonResult;
        }
        jsonResult.setResult(Result.SUCCESS);

        JobHistory jobHistory = new JobHistory();
        BeanUtils.copyProperties(jobHistoryDto,jobHistory);
        jobHistory.setCreateDate(new Date());
        try {
            jobHistory.setBeginDate(format.parse(jobHistoryDto.getBeginDate()));
        } catch (Exception e) {
            jobHistory.setBeginDate(null);
        }
        try {
            jobHistory.setEndDate(format.parse(jobHistoryDto.getEndDate()));
        } catch (Exception e) {
            jobHistory.setEndDate(null);
        }
        if(!StringUtils.isEmpty(jobHistoryDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(jobHistoryDto.getEmpId());

            jobHistory.setEmployee(employee);
        }
        //jobHistoryService.addObj(jobHistory);
        jobHistoryService.addJobHistory(jobHistory);
        return jsonResult;
    }
    /**
     * 编辑
     * @param jobHistoryDto
     * @return
     */
    @PostMapping("/jobHistory/updateJobHistory")
    public JsonResult updateJobHistory(JobHistoryDto jobHistoryDto){
        JobHistory savedJobHistory = jobHistoryService.queryObjById(jobHistoryDto.getId());
        try {
            savedJobHistory.setBeginDate(format.parse(jobHistoryDto.getBeginDate()));
        } catch (Exception e) {
            savedJobHistory.setBeginDate(null);
        }
        try {
            savedJobHistory.setEndDate(format.parse(jobHistoryDto.getEndDate()));
        } catch (Exception e) {
            savedJobHistory.setEndDate(null);
        }
        savedJobHistory.setName(jobHistoryDto.getName());
        savedJobHistory.setNote(jobHistoryDto.getNote());
        jobHistoryService.updateObj(savedJobHistory);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/jobHistory/jobHistory/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        jobHistoryService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

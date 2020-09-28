package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.TrainingExamineDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.TrainingExamine;
import com.jxqixin.trafic.service.ITrainingExamineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 人员档案，培训考核控制器
 */
@RestController
public class TrainingExamineController extends CommonController{
    @Autowired
    private ITrainingExamineService trainingExamineService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param trainingExamineDto
     * @return
     */
    @GetMapping("/trainingExamine/trainingExaminesByPage")
    public ModelMap queryTrainingExamines(TrainingExamineDto trainingExamineDto){
        Page page = trainingExamineService.findTrainingExamines(trainingExamineDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param trainingExamineDto
     * @return
     */
    @PostMapping("/trainingExamine/trainingExamine")
    public JsonResult addTrainingExamine(TrainingExamineDto trainingExamineDto){
        JsonResult jsonResult = checkEmpId(trainingExamineDto.getEmpId());
        if(jsonResult.getResult().getResultCode()!=200){
            return jsonResult;
        }
        jsonResult.setResult(Result.SUCCESS);

        TrainingExamine trainingExamine = new TrainingExamine();
        BeanUtils.copyProperties(trainingExamineDto,trainingExamine);
        trainingExamine.setCreateDate(new Date());
        try {
            trainingExamine.setBeginDate(format.parse(trainingExamineDto.getBeginDate()));
        } catch (Exception e) {
            trainingExamine.setBeginDate(null);
        }
        try {
            trainingExamine.setEndDate(format.parse(trainingExamineDto.getEndDate()));
        } catch (Exception e) {
            trainingExamine.setEndDate(null);
        }
        if(!StringUtils.isEmpty(trainingExamineDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(trainingExamineDto.getEmpId());

            trainingExamine.setEmployee(employee);
        }
        trainingExamineService.addObj(trainingExamine);
        return jsonResult;
    }
    /**
     * 编辑
     * @param trainingExamineDto
     * @return
     */
    @PostMapping("/trainingExamine/updateTrainingExamine")
    public JsonResult updateTrainingExamine(TrainingExamineDto trainingExamineDto){
        TrainingExamine savedTrainingExamine = trainingExamineService.queryObjById(trainingExamineDto.getId());
        try {
            savedTrainingExamine.setBeginDate(format.parse(trainingExamineDto.getBeginDate()));
        } catch (Exception e) {
            savedTrainingExamine.setBeginDate(null);
        }
        try {
            savedTrainingExamine.setEndDate(format.parse(trainingExamineDto.getEndDate()));
        } catch (Exception e) {
            savedTrainingExamine.setEndDate(null);
        }
        savedTrainingExamine.setName(trainingExamineDto.getName());
        savedTrainingExamine.setNote(trainingExamineDto.getNote());
        trainingExamineService.updateObj(savedTrainingExamine);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/trainingExamine/trainingExamine/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        trainingExamineService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.InductionTrainingDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.InductionTraining;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IInductionTrainingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 人员档案，入职培训控制器
 */
@RestController
public class InductionTrainingController extends CommonController{
    @Autowired
    private IInductionTrainingService inductionTrainingService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param inductionTrainingDto
     * @return
     */
    @GetMapping("/inductionTraining/inductionTrainingsByPage")
    public ModelMap queryInductionTrainings(InductionTrainingDto inductionTrainingDto){
        Page page = inductionTrainingService.findInductionTrainings(inductionTrainingDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param inductionTrainingDto
     * @return
     */
    @PostMapping("/inductionTraining/inductionTraining")
    public JsonResult addInductionTraining(InductionTrainingDto inductionTrainingDto){
        JsonResult jsonResult = checkEmpId(inductionTrainingDto.getEmpId());
        if(jsonResult.getResult().getResultCode()!=200){
            return jsonResult;
        }
        jsonResult.setResult(Result.SUCCESS);

        InductionTraining inductionTraining = new InductionTraining();
        BeanUtils.copyProperties(inductionTrainingDto,inductionTraining);
        inductionTraining.setCreateDate(new Date());
        try {
            inductionTraining.setBeginDate(format.parse(inductionTrainingDto.getBeginDate()));
        } catch (Exception e) {
            inductionTraining.setBeginDate(null);
        }
        try {
            inductionTraining.setEndDate(format.parse(inductionTrainingDto.getEndDate()));
        } catch (Exception e) {
            inductionTraining.setEndDate(null);
        }
        if(!StringUtils.isEmpty(inductionTrainingDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(inductionTrainingDto.getEmpId());

            inductionTraining.setEmployee(employee);
        }
        inductionTrainingService.addObj(inductionTraining);
        return jsonResult;
    }
    /**
     * 编辑
     * @param inductionTrainingDto
     * @return
     */
    @PostMapping("/inductionTraining/updateInductionTraining")
    public JsonResult updateInductionTraining(InductionTrainingDto inductionTrainingDto){
        InductionTraining savedInductionTraining = inductionTrainingService.queryObjById(inductionTrainingDto.getId());
        try {
            savedInductionTraining.setBeginDate(format.parse(inductionTrainingDto.getBeginDate()));
        } catch (Exception e) {
            savedInductionTraining.setBeginDate(null);
        }
        try {
            savedInductionTraining.setEndDate(format.parse(inductionTrainingDto.getEndDate()));
        } catch (Exception e) {
            savedInductionTraining.setEndDate(null);
        }
        savedInductionTraining.setName(inductionTrainingDto.getName());
        savedInductionTraining.setNote(inductionTrainingDto.getNote());
        inductionTrainingService.updateObj(savedInductionTraining);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/inductionTraining/inductionTraining/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        inductionTrainingService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

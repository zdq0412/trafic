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
 * 控制器
 */
@RestController
public class InductionTrainingController extends CommonController{
    @Autowired
    private IInductionTrainingService inductionTrainingService;
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
        InductionTraining inductionTraining = new InductionTraining();
        BeanUtils.copyProperties(inductionTrainingDto,inductionTraining);
        inductionTraining.setCreateDate(new Date());
        if(!StringUtils.isEmpty(inductionTrainingDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(inductionTrainingDto.getEmpId());

            inductionTraining.setEmployee(employee);
        }
        inductionTrainingService.addObj(inductionTraining);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param inductionTrainingDto
     * @return
     */
    @PutMapping("/inductionTraining/inductionTraining")
    public JsonResult updateInductionTraining(InductionTrainingDto inductionTrainingDto){
        InductionTraining savedInductionTraining = inductionTrainingService.queryObjById(inductionTrainingDto.getId());
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

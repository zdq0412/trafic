package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SafetyResponsibilityAgreementDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.SafetyResponsibilityAgreement;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.ISafetyResponsibilityAgreementService;
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
 * 控制器
 */
@RestController
public class SafetyResponsibilityAgreementController extends CommonController{
    @Autowired
    private ISafetyResponsibilityAgreementService safetyResponsibilityAgreementService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param safetyResponsibilityAgreementDto
     * @return
     */
    @GetMapping("/safetyResponsibilityAgreement/safetyResponsibilityAgreementsByPage")
    public ModelMap querySafetyResponsibilityAgreements(SafetyResponsibilityAgreementDto safetyResponsibilityAgreementDto){
        Page page = safetyResponsibilityAgreementService.findSafetyResponsibilityAgreements(safetyResponsibilityAgreementDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param safetyResponsibilityAgreementDto
     * @return
     */
    @PostMapping("/safetyResponsibilityAgreement/safetyResponsibilityAgreement")
    public JsonResult addSafetyResponsibilityAgreement(SafetyResponsibilityAgreementDto safetyResponsibilityAgreementDto){
        SafetyResponsibilityAgreement safetyResponsibilityAgreement = new SafetyResponsibilityAgreement();
        BeanUtils.copyProperties(safetyResponsibilityAgreementDto,safetyResponsibilityAgreement);
        safetyResponsibilityAgreement.setCreateDate(new Date());
        try {
            safetyResponsibilityAgreement.setBeginDate(format.parse(safetyResponsibilityAgreementDto.getBeginDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            safetyResponsibilityAgreement.setBeginDate(null);
        }

        try {
            safetyResponsibilityAgreement.setEndDate(format.parse(safetyResponsibilityAgreementDto.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            safetyResponsibilityAgreement.setEndDate(null);
        }
        if(!StringUtils.isEmpty(safetyResponsibilityAgreementDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(safetyResponsibilityAgreementDto.getEmpId());

            safetyResponsibilityAgreement.setEmployee(employee);
        }
        safetyResponsibilityAgreementService.addObj(safetyResponsibilityAgreement);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param safetyResponsibilityAgreementDto
     * @return
     */
    @PutMapping("/safetyResponsibilityAgreement/safetyResponsibilityAgreement")
    public JsonResult updateSafetyResponsibilityAgreement(SafetyResponsibilityAgreementDto safetyResponsibilityAgreementDto){
        SafetyResponsibilityAgreement savedSafetyResponsibilityAgreement = safetyResponsibilityAgreementService.queryObjById(safetyResponsibilityAgreementDto.getId());
        savedSafetyResponsibilityAgreement.setName(safetyResponsibilityAgreementDto.getName());
        savedSafetyResponsibilityAgreement.setNote(safetyResponsibilityAgreementDto.getNote());
        try {
            savedSafetyResponsibilityAgreement.setBeginDate(format.parse(safetyResponsibilityAgreementDto.getBeginDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            savedSafetyResponsibilityAgreement.setBeginDate(null);
        }
        try {
            savedSafetyResponsibilityAgreement.setEndDate(format.parse(safetyResponsibilityAgreementDto.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            savedSafetyResponsibilityAgreement.setEndDate(null);
        }
        safetyResponsibilityAgreementService.updateObj(savedSafetyResponsibilityAgreement);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/safetyResponsibilityAgreement/safetyResponsibilityAgreement/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        safetyResponsibilityAgreementService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

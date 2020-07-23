package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SafetyProductionCostPlanDetailDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import com.jxqixin.trafic.service.IEmployeeService;
import com.jxqixin.trafic.service.ISafetyProductionCostPlanDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 年度安全生产费用控制器
 */
@RestController
public class SafetyProductionCostPlanDetailController extends CommonController{
    @Autowired
    private ISafetyProductionCostPlanDetailService safetyProductionCostPlanDetailService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private IEmployeeService employeeService;
    /**
     * 分页查询安全生产台账
     * @param safetyProductionCostPlanDetailDto
     * @return
     */
    @GetMapping("/safetyProductionCostPlanDetail/safetyProductionCostPlanDetailsByPage")
    public ModelMap querySafetyProductionCostPlanDetails(SafetyProductionCostPlanDetailDto safetyProductionCostPlanDetailDto){
        Page page = safetyProductionCostPlanDetailService.findSafetyProductionCostPlanDetails(safetyProductionCostPlanDetailDto);
        return pageModelMap(page);
    }
    /**
     * 新增安全生产台账
     * @param safetyProductionCostPlanDetailDto
     * @return
     */
    @PostMapping("/safetyProductionCostPlanDetail/safetyProductionCostPlanDetail")
    public JsonResult addSafetyProductionCostPlanDetail(SafetyProductionCostPlanDetailDto safetyProductionCostPlanDetailDto, HttpServletRequest request){
        SafetyProductionCostPlanDetail detail = new SafetyProductionCostPlanDetail();
        BeanUtils.copyProperties(safetyProductionCostPlanDetailDto,detail);
        detail.setCreateDate(new Date());
        if(!StringUtils.isEmpty(safetyProductionCostPlanDetailDto.getBillingDate())){
            try {
                detail.setBillingDate(format.parse(safetyProductionCostPlanDetailDto.getBillingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(safetyProductionCostPlanDetailDto.getSafetyProductionCostPlanId())){
            SafetyProductionCostPlan safetyProductionCostPlan = new SafetyProductionCostPlan();
            safetyProductionCostPlan.setId(safetyProductionCostPlanDetailDto.getSafetyProductionCostPlanId());
            detail.setSafetyProductionCostPlan(safetyProductionCostPlan);
        }
        if(!StringUtils.isEmpty(safetyProductionCostPlanDetailDto.getSumOfMoney())){
            detail.setSumOfMoney(new BigDecimal(safetyProductionCostPlanDetailDto.getSumOfMoney()));
        }
        String username = getCurrentUsername(request);
        Employee employee = employeeService.findByUsername(username);
        detail.setOperator(employee==null?username:employee.getName());
        safetyProductionCostPlanDetailService.addObj(detail);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑安全生产台账
     * @param safetyProductionCostPlanDetailDto
     * @return
     */
    @PutMapping("/safetyProductionCostPlanDetail/safetyProductionCostPlanDetail")
    public JsonResult updateSafetyProductionCostPlanDetail(SafetyProductionCostPlanDetailDto safetyProductionCostPlanDetailDto){
        SafetyProductionCostPlanDetail savedSafetyProductionCostPlanDetail = safetyProductionCostPlanDetailService.queryObjById(safetyProductionCostPlanDetailDto.getId());
        savedSafetyProductionCostPlanDetail.setNote(safetyProductionCostPlanDetailDto.getNote());
        if(!StringUtils.isEmpty(safetyProductionCostPlanDetailDto.getBillingDate())){
            try {
                savedSafetyProductionCostPlanDetail.setBillingDate(format.parse(safetyProductionCostPlanDetailDto.getBillingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(safetyProductionCostPlanDetailDto.getSafetyProductionCostPlanId())){
            SafetyProductionCostPlan safetyProductionCostPlan = new SafetyProductionCostPlan();
            safetyProductionCostPlan.setId(safetyProductionCostPlanDetailDto.getSafetyProductionCostPlanId());
            savedSafetyProductionCostPlanDetail.setSafetyProductionCostPlan(safetyProductionCostPlan);
        }

        if(!StringUtils.isEmpty(safetyProductionCostPlanDetailDto.getSumOfMoney())){
            savedSafetyProductionCostPlanDetail.setSumOfMoney(new BigDecimal(safetyProductionCostPlanDetailDto.getSumOfMoney()));
        }
        savedSafetyProductionCostPlanDetail.setBillNo(safetyProductionCostPlanDetailDto.getBillNo());
        savedSafetyProductionCostPlanDetail.setContent(safetyProductionCostPlanDetailDto.getContent());

        safetyProductionCostPlanDetailService.updateObj(savedSafetyProductionCostPlanDetail);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除安全生产台账
     * @param id
     * @return
     */
    @DeleteMapping("/safetyProductionCostPlanDetail/safetyProductionCostPlanDetail/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        safetyProductionCostPlanDetailService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

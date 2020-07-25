package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SafetyProductionCostDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.SafetyProductionCost;
import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import com.jxqixin.trafic.service.ISafetyProductionCostService;
import com.jxqixin.trafic.vo.CostTotalVo;
import com.jxqixin.trafic.vo.SafetyProductionCostVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 年度安全生产费用控制器
 */
@RestController
public class SafetyProductionCostController extends CommonController{
    @Autowired
    private ISafetyProductionCostService safetyProductionCostService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 根据日期查找安全生产费用
     * @param year
     * @param request
     * @return
     */
    @GetMapping("/safetyProductionCost/safetyProductionCost")
    public SafetyProductionCost findByDate(String year, HttpServletRequest request){
        int intYear = 0;
        if(StringUtils.isEmpty(year)){
            Date date = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            intYear = c.get(Calendar.YEAR);
        }else{
            intYear = Integer.parseInt(year);
        }
       return  safetyProductionCostService.findByOrgAndYear(getOrg(request),intYear);
    }
    /**
     * 根据日期查找安全生产费用
     * @param date
     * @param type 按年或按月
     * @param request
     * @return
     */
    @GetMapping("/safetyProductionCost/statistics")
    public SafetyProductionCostVo statistics(String date,@RequestParam(defaultValue = "year") String type, HttpServletRequest request){
        Date d = new Date();
        if(!StringUtils.isEmpty(date)){
            try {
                d = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                d = new Date();
            }
        }
       return  safetyProductionCostService.findByOrgAndDate(getOrg(request),d,type);
    }
    /**
     * 根据年份对安全生产费用汇总
     * @param year 年份
     * @param request
     * @return
     */
    @GetMapping("/safetyProductionCost/total")
    public CostTotalVo total(String year, HttpServletRequest request){
        int intYear = 0;
        if(!StringUtils.isEmpty(year)){
           intYear = Integer.parseInt(year);
        }else{
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());

            intYear = c.get(Calendar.YEAR);
        }
       return  safetyProductionCostService.findTotal(getOrg(request),intYear);
    }
    /**
     * 添加(实际上是更新)生产安全费用使用计划
     * @return
     */
    @PostMapping("/safetyProductionCost/safetyProductionCost")
    public JsonResult updateSafetyProductionCost(SafetyProductionCostDto safetyProductionCostDto){
        SafetyProductionCost cost = new SafetyProductionCost();
        BeanUtils.copyProperties(safetyProductionCostDto,cost);
        String planStr = safetyProductionCostDto.getPlans();

        List<SafetyProductionCostPlan> plans = new ArrayList<>();
        if(!StringUtils.isEmpty(planStr)){
            //通过逗号截取安全费用计划的ID和计划费用
            String[] idAndPlanCost = planStr.split(",");
            if(idAndPlanCost!=null && idAndPlanCost.length>0){
                for(int i = 0;i<idAndPlanCost.length;i++){
                    String[] planObj = idAndPlanCost[i].split(":");
                    SafetyProductionCostPlan p = new SafetyProductionCostPlan();
                    p.setId(planObj[0]);
                    p.setPlanCost(new BigDecimal(planObj[1]));

                    plans.add(p);
                }
            }
        }
        safetyProductionCostService.updateCostAndPlans(cost,plans);
        return new JsonResult(Result.SUCCESS);
    }


}

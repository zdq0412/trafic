package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import com.jxqixin.trafic.service.ISafetyProductionCostPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 年度安全生产费用控制器
 */
@RestController
public class SafetyProductionCostPlanController extends CommonController{
    @Autowired
    private ISafetyProductionCostPlanService safetyProductionCostPlanService;
    /**
     * 查找当前企业下的所有安全生产费用计划
     * @param request
     * @return
     */
    @GetMapping("/safetyProductionCostPlan/safetyProductionCostPlan")
    public List<SafetyProductionCostPlan> findAllPlans(HttpServletRequest request){
        return safetyProductionCostPlanService.findAllPlans(getOrg(request));
    }
}

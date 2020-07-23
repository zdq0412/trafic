package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.model.SafetyProductionCost;
import com.jxqixin.trafic.service.ISafetyProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * 年度安全生产费用控制器
 */
@RestController
public class SafetyProductionCostController extends CommonController{
    @Autowired
    private ISafetyProductionCostService safetyProductionCostService;

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
}

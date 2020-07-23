package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.service.ISafetyInvestmentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
/**
 * 安全生产费用支出类别控制器
 */
@RestController
public class SafetyInvestmentCategoryController extends CommonController{
    @Autowired
    private ISafetyInvestmentCategoryService safetyInvestmentCategoryService;
}

package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.RiskLevelDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.RiskLevel;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IRiskLevelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 风险等级控制器
 */
@RestController
public class RiskLevelController extends CommonController{
    @Autowired
    private IRiskLevelService riskLevelService;
    /**
     * 分页查询风险等级
     * @param riskLevelDto
     * @return
     */
    @GetMapping("/riskLevel/riskLevelsByPage")
    public ModelMap queryRiskLevels(RiskLevelDto riskLevelDto){
        Page page = riskLevelService.findRiskLevels(riskLevelDto);
        return pageModelMap(page);
    }
    /**
     * 新增风险等级
     * @param riskLevelDto
     * @return
     */
    @PostMapping("/riskLevel/riskLevel")
    public JsonResult addRiskLevel(RiskLevelDto riskLevelDto){
        RiskLevel riskLevel = new RiskLevel();
        BeanUtils.copyProperties(riskLevelDto,riskLevel);
        riskLevel.setCreateDate(new Date());
        riskLevelService.addObj(riskLevel);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑风险等级
     * @param riskLevelDto
     * @return
     */
    @PutMapping("/riskLevel/riskLevel")
    public JsonResult updateRiskLevel(RiskLevelDto riskLevelDto){
        RiskLevel riskLevel = riskLevelService.queryObjById(riskLevelDto.getId());
        riskLevel.setColor(riskLevelDto.getColor());
        riskLevel.setNote(riskLevelDto.getNote());
        riskLevel.setLowerLimit(riskLevelDto.getLowerLimit());
        riskLevel.setUpperLimit(riskLevelDto.getUpperLimit());
        riskLevel.setMeasure(riskLevelDto.getMeasure());
        riskLevel.setName(riskLevelDto.getName());
        riskLevel.setTimeLimit(riskLevelDto.getTimeLimit());
        riskLevelService.updateObj(riskLevel);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除风险等级
     * @param id
     * @return
     */
    @DeleteMapping("/riskLevel/riskLevel/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        riskLevelService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

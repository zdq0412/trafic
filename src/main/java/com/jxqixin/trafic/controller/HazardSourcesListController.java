package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.HazardSourcesListDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.HazardSourcesList;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IHazardSourcesListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 危险源控制器
 */
@RestController
public class HazardSourcesListController extends CommonController{
    @Autowired
    private IHazardSourcesListService hazardSourcesListService;
    /**
     * 分页查询危险源
     * @param hazardSourcesListDto
     * @return
     */
    @GetMapping("/hazardSourcesList/hazardSourcesListsByPage")
    public ModelMap queryHazardSourcesLists(HazardSourcesListDto hazardSourcesListDto, HttpServletRequest request){
        Page page = hazardSourcesListService.findHazardSourcesLists(hazardSourcesListDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增危险源
     * @param hazardSourcesListDto
     * @return
     */
    @PostMapping("/hazardSourcesList/hazardSourcesList")
    public JsonResult addHazardSourcesList(HazardSourcesListDto hazardSourcesListDto,HttpServletRequest request){
        HazardSourcesList hazardSourcesList  = new HazardSourcesList();
        BeanUtils.copyProperties(hazardSourcesListDto,hazardSourcesList);
        hazardSourcesList.setOrg(getOrg(request));
        hazardSourcesList.setCreateDate(new Date());
        hazardSourcesListService.addHazardSourcesList(hazardSourcesList);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑危险源
     * @param hazardSourcesListDto
     * @return
     */
    @PutMapping("/hazardSourcesList/hazardSourcesList")
    public JsonResult updateHazardSourcesList(HazardSourcesListDto hazardSourcesListDto){
        HazardSourcesList hazardSourcesList = hazardSourcesListService.queryObjById(hazardSourcesListDto.getId());
        hazardSourcesList.setConsequence(hazardSourcesListDto.getConsequence());
       // hazardSourcesList.setCriterion(hazardSourcesListDto.getCriterion());
       // hazardSourcesList.setFourColor(hazardSourcesListDto.getFourColor());
        hazardSourcesList.setHappen(hazardSourcesListDto.getHappen());
        hazardSourcesList.setMeasures(hazardSourcesListDto.getMeasures());
        hazardSourcesList.setName(hazardSourcesListDto.getName());
        hazardSourcesList.setTimeLimit(hazardSourcesListDto.getTimeLimit());
        //hazardSourcesList.setRiskLevel(hazardSourcesListDto.getRiskLevel());
        hazardSourcesListService.updateHazardSourcesList(hazardSourcesList);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除危险源
     * @param id
     * @return
     */
    @DeleteMapping("/hazardSourcesList/hazardSourcesList/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        hazardSourcesListService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.TankVehicleDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.model.TankVehicle;
import com.jxqixin.trafic.service.ITankVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 危险货物道路运输罐式车辆罐体检查记录控制器
 */
@RestController
public class TankVehicleController extends CommonController{
    @Autowired
    private ITankVehicleService tankVehicleService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @return
     */
    @GetMapping("/tankVehicle/tankVehiclesByPage")
    public ModelMap queryTankVehicles(TankVehicleDto tankVehicleDto,HttpServletRequest request){
        Page page = tankVehicleService.findTankVehicles(tankVehicleDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param tankVehicleDto
     * @return
     */
    @PostMapping("/tankVehicle/tankVehicle")
    public JsonResult addTankVehicle(TankVehicleDto tankVehicleDto,HttpServletRequest request){
        TankVehicle savedTankVehicle = new TankVehicle();
        savedTankVehicle.setName(tankVehicleDto.getName());
        try {
            savedTankVehicle.setCheckDate(format.parse(tankVehicleDto.getCheckDate()));
        } catch (ParseException e) {
            savedTankVehicle.setCheckDate(null);
        }
        savedTankVehicle.setCreateDate(new Date());
        savedTankVehicle.setCreator(getCurrentUsername(request));
        savedTankVehicle.setOrg(getOrg(request));
        savedTankVehicle.setNote(tankVehicleDto.getNote());
        tankVehicleService.addObj(savedTankVehicle);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param tankVehicleDto
     * @return
     */
    @PutMapping("/tankVehicle/tankVehicle")
    public JsonResult updateTankVehicle(TankVehicleDto tankVehicleDto){
        TankVehicle savedTankVehicle = tankVehicleService.queryObjById(tankVehicleDto.getId());
        if(!StringUtils.isEmpty(tankVehicleDto.getCheckDate())){
            try {
                savedTankVehicle.setCheckDate(format.parse(tankVehicleDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedTankVehicle.setCheckDate(new Date());
            }
        }
        savedTankVehicle.setName(tankVehicleDto.getName());
        savedTankVehicle.setNote(tankVehicleDto.getNote());
        tankVehicleService.updateObj(savedTankVehicle);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改
     * @param tankVehicleDto
     * @return
     */
    @PostMapping("/tankVehicle/content")
    public JsonResult updateContent(TankVehicleDto tankVehicleDto){
        TankVehicle savedTankVehicle = tankVehicleService.queryObjById(tankVehicleDto.getId());
        savedTankVehicle.setCarNo(tankVehicleDto.getCarNo());
        savedTankVehicle.setCheckItem1(tankVehicleDto.getCheckItem1());
        savedTankVehicle.setCheckItem2(tankVehicleDto.getCheckItem2());
        savedTankVehicle.setCheckItem3(tankVehicleDto.getCheckItem3());
        savedTankVehicle.setCheckItem4(tankVehicleDto.getCheckItem4());
        savedTankVehicle.setCheckItem5(tankVehicleDto.getCheckItem5());
        savedTankVehicle.setCheckItem6(tankVehicleDto.getCheckItem6());
        savedTankVehicle.setCheckItem7(tankVehicleDto.getCheckItem7());
        savedTankVehicle.setCheckItem8(tankVehicleDto.getCheckItem8());
        savedTankVehicle.setCheckItem9(tankVehicleDto.getCheckItem9());
        savedTankVehicle.setCheckItem10(tankVehicleDto.getCheckItem10());
        savedTankVehicle.setCheckItem11(tankVehicleDto.getCheckItem11());
        savedTankVehicle.setCheckItem12(tankVehicleDto.getCheckItem12());
        savedTankVehicle.setCheckItem13(tankVehicleDto.getCheckItem13());
        savedTankVehicle.setCheckItem14(tankVehicleDto.getCheckItem14());
        savedTankVehicle.setCheckItem15(tankVehicleDto.getCheckItem15());

        savedTankVehicle.setCheckItem1Msg(tankVehicleDto.getCheckItem1Msg());
        savedTankVehicle.setCheckItem2Msg(tankVehicleDto.getCheckItem2Msg());
        savedTankVehicle.setCheckItem3Msg(tankVehicleDto.getCheckItem3Msg());
        savedTankVehicle.setCheckItem4Msg(tankVehicleDto.getCheckItem4Msg());
        savedTankVehicle.setCheckItem5Msg(tankVehicleDto.getCheckItem5Msg());
        savedTankVehicle.setCheckItem6Msg(tankVehicleDto.getCheckItem6Msg());
        savedTankVehicle.setCheckItem7Msg(tankVehicleDto.getCheckItem7Msg());
        savedTankVehicle.setCheckItem8Msg(tankVehicleDto.getCheckItem8Msg());
        savedTankVehicle.setCheckItem9Msg(tankVehicleDto.getCheckItem9Msg());
        savedTankVehicle.setCheckItem10Msg(tankVehicleDto.getCheckItem10Msg());
        savedTankVehicle.setCheckItem11Msg(tankVehicleDto.getCheckItem11Msg());
        savedTankVehicle.setCheckItem12Msg(tankVehicleDto.getCheckItem12Msg());
        savedTankVehicle.setCheckItem13Msg(tankVehicleDto.getCheckItem13Msg());
        savedTankVehicle.setCheckItem14Msg(tankVehicleDto.getCheckItem14Msg());
        savedTankVehicle.setCheckItem15Msg(tankVehicleDto.getCheckItem15Msg());
        savedTankVehicle.setName(tankVehicleDto.getName());
        savedTankVehicle.setSuggestion(tankVehicleDto.getSuggestion());
        tankVehicleService.updateObj(savedTankVehicle);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/tankVehicle/tankVehicle/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        tankVehicleService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 引入模板
     * @param templateId
     * @return
     */
    @PostMapping("/tankVehicle/template")
    public JsonResult importTemplate(String templateId, HttpServletRequest request){
       TankVehicle tankVehicle = tankVehicleService.importTemplate(templateId,getOrg(request),getCurrentUsername(request));
        return new JsonResult(Result.SUCCESS,tankVehicle);
    }
}

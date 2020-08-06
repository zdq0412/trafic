package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.IDCardDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.IDCard;
import com.jxqixin.trafic.service.IIDCardService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 控制器
 */
@RestController
public class IDCardController extends CommonController{
    @Autowired
    private IIDCardService idcardService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param idCardDto
     * @return
     */
    @GetMapping("/idcard/idcardsByPage")
    public ModelMap queryIDCards(IDCardDto idCardDto){
        Page page = idcardService.findIDCards(idCardDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param idcardDto
     * @return
     */
    @PostMapping("/idcard/idcard")
    public JsonResult addIDCard(IDCardDto idcardDto){
        IDCard idcard = new IDCard();
        BeanUtils.copyProperties(idcardDto,idcard);
        idcard.setCreateDate(new Date());
        try {
            idcard.setBeginDate(format.parse(idcardDto.getBeginDate()));
        } catch (Exception e) {
            idcard.setBeginDate(null);
        }

        try {
            idcard.setEndDate(format.parse(idcardDto.getEndDate()));
        } catch (Exception e) {
            idcard.setEndDate(null);
        }
        if(!StringUtils.isEmpty(idcardDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(idcardDto.getEmpId());

            idcard.setEmployee(employee);
        }
        idcardService.addObj(idcard);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param idcardDto
     * @return
     */
    @PutMapping("/idcard/idcard")
    public JsonResult updateIDCard(IDCardDto idcardDto){
        IDCard savedIDCard = idcardService.queryObjById(idcardDto.getId());
        savedIDCard.setName(idcardDto.getName());
        savedIDCard.setNote(idcardDto.getNote());
        try {
            savedIDCard.setBeginDate(format.parse(idcardDto.getBeginDate()));
        } catch (Exception e) {
            savedIDCard.setBeginDate(null);
        }
        try {
            savedIDCard.setEndDate(format.parse(idcardDto.getEndDate()));
        } catch (Exception e) {
            savedIDCard.setEndDate(null);
        }
        idcardService.updateObj(savedIDCard);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/idcard/idcard/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        idcardService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

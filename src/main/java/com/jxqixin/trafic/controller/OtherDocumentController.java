package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.OtherDocumentDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OtherDocument;
import com.jxqixin.trafic.service.IOtherDocumentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 人员档案，其他档案控制器
 */
@RestController
public class OtherDocumentController extends CommonController{
    @Autowired
    private IOtherDocumentService otherDocumentService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param otherDocumentDto
     * @return
     */
    @GetMapping("/otherDocument/otherDocumentsByPage")
    public ModelMap queryOtherDocuments(OtherDocumentDto otherDocumentDto){
        Page page = otherDocumentService.findOtherDocuments(otherDocumentDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param otherDocumentDto
     * @return
     */
    @PostMapping("/otherDocument/otherDocument")
    public JsonResult addOtherDocument(OtherDocumentDto otherDocumentDto){
        JsonResult jsonResult = checkEmpId(otherDocumentDto.getEmpId());
        if(jsonResult.getResult().getResultCode()!=200){
            return jsonResult;
        }
        jsonResult.setResult(Result.SUCCESS);

        OtherDocument otherDocument = new OtherDocument();
        BeanUtils.copyProperties(otherDocumentDto,otherDocument);
        otherDocument.setCreateDate(new Date());
        try {
            otherDocument.setBeginDate(format.parse(otherDocumentDto.getBeginDate()));
        } catch (Exception e) {
            otherDocument.setBeginDate(null);
        }
        try {
            otherDocument.setEndDate(format.parse(otherDocumentDto.getEndDate()));
        } catch (Exception e) {
            otherDocument.setEndDate(null);
        }
        if(!StringUtils.isEmpty(otherDocumentDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(otherDocumentDto.getEmpId());

            otherDocument.setEmployee(employee);
        }
        otherDocumentService.addObj(otherDocument);
        return jsonResult;
    }
    /**
     * 编辑
     * @param otherDocumentDto
     * @return
     */
    @PostMapping("/otherDocument/updateOtherDocument")
    public JsonResult updateOtherDocument(OtherDocumentDto otherDocumentDto){
        OtherDocument savedOtherDocument = otherDocumentService.queryObjById(otherDocumentDto.getId());
        try {
            savedOtherDocument.setBeginDate(format.parse(otherDocumentDto.getBeginDate()));
        } catch (Exception e) {
            savedOtherDocument.setBeginDate(null);
        }
        try {
            savedOtherDocument.setEndDate(format.parse(otherDocumentDto.getEndDate()));
        } catch (Exception e) {
            savedOtherDocument.setEndDate(null);
        }
        savedOtherDocument.setName(otherDocumentDto.getName());
        savedOtherDocument.setNote(otherDocumentDto.getNote());
        otherDocumentService.updateObj(savedOtherDocument);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/otherDocument/otherDocument/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        otherDocumentService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

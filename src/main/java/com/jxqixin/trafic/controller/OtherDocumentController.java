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

import java.util.Date;

/**
 * 控制器
 */
@RestController
public class OtherDocumentController extends CommonController{
    @Autowired
    private IOtherDocumentService otherDocumentService;
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
        OtherDocument otherDocument = new OtherDocument();
        BeanUtils.copyProperties(otherDocumentDto,otherDocument);
        otherDocument.setCreateDate(new Date());
        if(!StringUtils.isEmpty(otherDocumentDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(otherDocumentDto.getEmpId());

            otherDocument.setEmployee(employee);
        }
        otherDocumentService.addObj(otherDocument);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param otherDocumentDto
     * @return
     */
    @PutMapping("/otherDocument/otherDocument")
    public JsonResult updateOtherDocument(OtherDocumentDto otherDocumentDto){
        OtherDocument savedOtherDocument = otherDocumentService.queryObjById(otherDocumentDto.getId());
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

package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.QualificationDocumentDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.QualificationDocument;
import com.jxqixin.trafic.service.IQualificationDocumentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 人员档案，资质文件控制器
 */
@RestController
public class QualificationDocumentController extends CommonController{
    @Autowired
    private IQualificationDocumentService qualificationDocumentService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param qualificationDocumentDto
     * @return
     */
    @GetMapping("/qualificationDocument/qualificationDocumentsByPage")
    public ModelMap queryQualificationDocuments(QualificationDocumentDto qualificationDocumentDto){
        Page page = qualificationDocumentService.findQualificationDocuments(qualificationDocumentDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param qualificationDocumentDto
     * @return
     */
    @PostMapping("/qualificationDocument/qualificationDocument")
    public JsonResult addQualificationDocument(QualificationDocumentDto qualificationDocumentDto){
        JsonResult jsonResult = checkEmpId(qualificationDocumentDto.getEmpId());
        if(jsonResult.getResult().getResultCode()!=200){
            return jsonResult;
        }
        jsonResult.setResult(Result.SUCCESS);

        QualificationDocument qualificationDocument = new QualificationDocument();
        BeanUtils.copyProperties(qualificationDocumentDto,qualificationDocument);
        qualificationDocument.setCreateDate(new Date());
        try {
            qualificationDocument.setBeginDate(format.parse(qualificationDocumentDto.getBeginDate()));
        } catch (Exception e) {
            qualificationDocument.setBeginDate(null);
        }
        try {
            qualificationDocument.setEndDate(format.parse(qualificationDocumentDto.getEndDate()));
        } catch (Exception e) {
            qualificationDocument.setEndDate(null);
        }
        if(!StringUtils.isEmpty(qualificationDocumentDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(qualificationDocumentDto.getEmpId());

            qualificationDocument.setEmployee(employee);
        }
        //qualificationDocumentService.addObj(qualificationDocument);
        qualificationDocumentService.addQualificationDocument(qualificationDocument);
        return jsonResult;
    }
    /**
     * 编辑
     * @param qualificationDocumentDto
     * @return
     */
    @PostMapping("/qualificationDocument/updateQualificationDocument")
    public JsonResult updateQualificationDocument(QualificationDocumentDto qualificationDocumentDto){
        QualificationDocument savedQualificationDocument = qualificationDocumentService.queryObjById(qualificationDocumentDto.getId());
        savedQualificationDocument.setName(qualificationDocumentDto.getName());
        savedQualificationDocument.setNote(qualificationDocumentDto.getNote());
        try {
            savedQualificationDocument.setBeginDate(format.parse(qualificationDocumentDto.getBeginDate()));
        } catch (Exception e) {
            savedQualificationDocument.setBeginDate(null);
        }
        try {
            savedQualificationDocument.setEndDate(format.parse(qualificationDocumentDto.getEndDate()));
        } catch (Exception e) {
            savedQualificationDocument.setEndDate(null);
        }
        qualificationDocumentService.updateObj(savedQualificationDocument);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/qualificationDocument/qualificationDocument/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        qualificationDocumentService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

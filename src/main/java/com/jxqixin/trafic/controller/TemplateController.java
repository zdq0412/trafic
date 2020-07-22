package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.TemplateDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Template;
import com.jxqixin.trafic.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * 模板控制器
 */
@RestController
public class TemplateController extends CommonController{
    @Autowired
    private ITemplateService templateService;
    /**
     * 分页查询模板
     * @param nameDto
     * @return
     */
    @GetMapping("/template/templatesByPage")
    public ModelMap queryTemplates(NameDto nameDto){
        Page page = templateService.findTemplates(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增模板
     * @param template
     * @return
     */
    @PostMapping("/template/template")
    public JsonResult addTemplate(Template template,HttpServletRequest request){
        JsonResult jsonResult = findByName(template.getName());
        if(jsonResult.getResult().getResultCode()==200){
            template.setId(UUID.randomUUID().toString());
            template.setCreateDate(new Date());
            template.setCreator(getCurrentUsername(request));
            templateService.addObj(template);
        }
        return jsonResult;
    }
    /**
     * 编辑模板
     * @param templateDto
     * @return
     */
    @PutMapping("/template/template")
    public JsonResult updateTemplate(TemplateDto templateDto){
        Template s = templateService.findByName(templateDto.getName());
        if(s!=null && !s.getId().equals(templateDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        Template savedTemplate = templateService.queryObjById(templateDto.getId());
        savedTemplate.setName(templateDto.getName());
        savedTemplate.setNote(templateDto.getNote());
        templateService.updateObj(savedTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改模板内容
     * @param templateDto
     * @return
     */
    @PutMapping("/template/content")
    public JsonResult updateContent(TemplateDto templateDto){
        Template savedTemplate = templateService.queryObjById(templateDto.getId());
        savedTemplate.setContent(templateDto.getContent());
        templateService.updateObj(savedTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据模板名称查找
     * @param name
     * @return
     */
    @GetMapping("/template/template/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        Template template = templateService.findByName(name);
        if(template==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除模板
     * @param id
     * @return
     */
    @DeleteMapping("/template/template/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        templateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

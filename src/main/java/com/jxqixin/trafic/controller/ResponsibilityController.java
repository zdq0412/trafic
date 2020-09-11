package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.ResponsibilityDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Responsibility;
import com.jxqixin.trafic.service.IResponsibilityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 责任书控制器
 */
@RestController
public class ResponsibilityController extends CommonController{
    @Autowired
    private IResponsibilityService responsibilityService;
    /**
     * 分页查询责任书
     * @param nameDto
     * @return
     */
    @GetMapping("/responsibility/responsibilitysByPage")
    public ModelMap queryResponsibilitys(NameDto nameDto,HttpServletRequest request){
        Page page = responsibilityService.findResponsibilitys(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增责任书
     * @param templateDto
     * @return
     */
    @PostMapping("/responsibility/responsibility")
    public JsonResult addResponsibility(ResponsibilityDto templateDto,HttpServletRequest request){
        Responsibility savedResponsibility = new Responsibility();
        BeanUtils.copyProperties(templateDto,savedResponsibility);
        savedResponsibility.setCreateDate(new Date());
        savedResponsibility.setCreator(getCurrentUsername(request));
        savedResponsibility.setOrg(getOrg(request));
        responsibilityService.addObj(savedResponsibility);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑责任书
     * @param templateDto
     * @return
     */
    @PutMapping("/responsibility/responsibility")
    public JsonResult updateResponsibility(ResponsibilityDto templateDto){
        Responsibility savedResponsibility = responsibilityService.queryObjById(templateDto.getId());
        savedResponsibility.setName(templateDto.getName());
        savedResponsibility.setNote(templateDto.getNote());
        responsibilityService.updateObj(savedResponsibility);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改责任书内容
     * @param templateDto
     * @return
     */
    @PostMapping("/responsibility/content")
    public JsonResult updateContent(ResponsibilityDto templateDto){
        Responsibility savedResponsibility = responsibilityService.queryObjById(templateDto.getId());
        savedResponsibility.setContent(templateDto.getContent());
        responsibilityService.updateObj(savedResponsibility);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除责任书
     * @param id
     * @return
     */
    @DeleteMapping("/responsibility/responsibility/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        responsibilityService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 引入模板
     * @param templateId
     * @return
     */
    @PostMapping("/responsibility/template")
    public JsonResult importTemplate(String templateId, HttpServletRequest request){
       Responsibility responsibility = responsibilityService.importTemplate(templateId,getOrg(request),getCurrentUsername(request));
        return new JsonResult(Result.SUCCESS,responsibility);
    }
}

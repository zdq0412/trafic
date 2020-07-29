package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.SecurityCheckDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.model.SecurityCheck;
import com.jxqixin.trafic.service.ISecurityCheckService;
import org.springframework.beans.BeanUtils;
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
 * 安全检查控制器
 */
@RestController
public class SecurityCheckController extends CommonController{
    @Autowired
    private ISecurityCheckService securityCheckService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询安全检查
     * @param nameDto
     * @return
     */
    @GetMapping("/securityCheck/securityChecksByPage")
    public ModelMap querySecurityChecks(NameDto nameDto,String type){
        Page page = securityCheckService.findSecurityChecks(nameDto,type);
        return pageModelMap(page);
    }
    /**
     * 新增安全检查
     * @param securityCheckDto
     * @return
     */
    @PostMapping("/securityCheck/securityCheck")
    public JsonResult addSecurityCheck(SecurityCheckDto securityCheckDto,HttpServletRequest request){
        SecurityCheck savedSecurityCheck = new SecurityCheck();
        BeanUtils.copyProperties(securityCheckDto,savedSecurityCheck);

        if(!StringUtils.isEmpty(securityCheckDto.getCheckDate())){
            try {
                savedSecurityCheck.setCheckDate(format.parse(securityCheckDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityCheck.setCheckDate(new Date());
            }
        }
        savedSecurityCheck.setOrg(getOrg(request));
        savedSecurityCheck.setCreateDate(new Date());
        savedSecurityCheck.setCreator(getCurrentUsername(request));
        securityCheckService.addObj(savedSecurityCheck);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑安全检查
     * @param securityCheckDto
     * @return
     */
    @PutMapping("/securityCheck/securityCheck")
    public JsonResult updateSecurityCheck(SecurityCheckDto securityCheckDto){
        SecurityCheck savedSecurityCheck = securityCheckService.queryObjById(securityCheckDto.getId());
        if(!StringUtils.isEmpty(securityCheckDto.getCheckDate())){
            try {
                savedSecurityCheck.setCheckDate(format.parse(securityCheckDto.getCheckDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedSecurityCheck.setCheckDate(new Date());
            }
        }
        savedSecurityCheck.setName(securityCheckDto.getName());
        savedSecurityCheck.setNote(securityCheckDto.getNote());
        securityCheckService.updateObj(savedSecurityCheck);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改安全检查内容
     * @param securityCheckDto
     * @return
     */
    @PostMapping("/securityCheck/content")
    public JsonResult updateContent(SecurityCheckDto securityCheckDto){
        SecurityCheck savedSecurityCheck = securityCheckService.queryObjById(securityCheckDto.getId());
        savedSecurityCheck.setContent(securityCheckDto.getContent());
        savedSecurityCheck.setProblems(securityCheckDto.getProblems());
        savedSecurityCheck.setCheckObject(securityCheckDto.getCheckObject());
        savedSecurityCheck.setDeptAndEmp(securityCheckDto.getDeptAndEmp());
        savedSecurityCheck.setResult(securityCheckDto.getResult());
        securityCheckService.updateObj(savedSecurityCheck);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除安全检查
     * @param id
     * @return
     */
    @DeleteMapping("/securityCheck/securityCheck/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        securityCheckService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

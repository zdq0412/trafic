package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.StandardizationDto;
import com.jxqixin.trafic.model.Standardization;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IStandardizationService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 标准化自评控制器
 */
@RestController
public class StandardizationController extends CommonController{
    @Autowired
    private IStandardizationService standardizationService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param standardizationDto
     * @return
     */
    @GetMapping("/standardization/standardizationsByPage")
    public ModelMap queryStandardizations(StandardizationDto standardizationDto){
        Page page = standardizationService.findStandardizations(standardizationDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param standardizationDto
     * @return
     */
    @PostMapping("/standardization/standardization")
    public JsonResult addStandardization(StandardizationDto standardizationDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Standardization savedStandardization = new Standardization();
        BeanUtils.copyProperties(standardizationDto,savedStandardization);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedStandardization.setUrl(urlMapping);
            savedStandardization.setRealPath(savedFile.getAbsolutePath());
            savedStandardization.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedStandardization.setCreateDate(new Date());
        if(!StringUtils.isEmpty(standardizationDto.getBeginDate())){
            try {
                savedStandardization.setBeginDate(format.parse(standardizationDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedStandardization.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(standardizationDto.getEndDate())){
            try {
                savedStandardization.setEndDate(format.parse(standardizationDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedStandardization.setEndDate(null);
            }
        }
        savedStandardization.setOrg(getOrg(request));
        savedStandardization.setCreator(getCurrentUsername(request));
        standardizationService.addObj(savedStandardization);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param standardizationDto
     * @return
     */
    @PostMapping("/standardization/updateStandardization")
    public JsonResult updateStandardization(StandardizationDto standardizationDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        Standardization savedStandardization = standardizationService.queryObjById(standardizationDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedStandardization.setUrl(urlMapping);
            savedStandardization.setRealPath(savedFile.getAbsolutePath());
            savedStandardization.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(standardizationDto.getBeginDate())){
            try {
                savedStandardization.setBeginDate(format.parse(standardizationDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedStandardization.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(standardizationDto.getEndDate())){
            try {
                savedStandardization.setEndDate(format.parse(standardizationDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedStandardization.setEndDate(null);
            }
        }
        savedStandardization.setName(standardizationDto.getName());
        savedStandardization.setNote(standardizationDto.getNote());
        standardizationService.updateObj(savedStandardization);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/standardization/standardization/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        standardizationService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

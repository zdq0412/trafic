package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.EmergencyPlanDrillDto;
import com.jxqixin.trafic.model.EmergencyPlanDrill;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IEmergencyPlanDrillService;
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
 * 应急预案演练控制器
 */
@RestController
public class EmergencyPlanDrillController extends CommonController{
    @Autowired
    private IEmergencyPlanDrillService emergencyPlanDrillService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param emergencyPlanDrillDto
     * @return
     */
    @GetMapping("/emergencyPlanDrill/emergencyPlanDrillsByPage")
    public ModelMap queryEmergencyPlanDrills(EmergencyPlanDrillDto emergencyPlanDrillDto){
        Page page = emergencyPlanDrillService.findEmergencyPlanDrills(emergencyPlanDrillDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param emergencyPlanDrillDto
     * @return
     */
    @PostMapping("/emergencyPlanDrill/emergencyPlanDrill")
    public JsonResult addEmergencyPlanDrill(EmergencyPlanDrillDto emergencyPlanDrillDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        EmergencyPlanDrill savedEmergencyPlanDrill = new EmergencyPlanDrill();
        BeanUtils.copyProperties(emergencyPlanDrillDto,savedEmergencyPlanDrill);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedEmergencyPlanDrill.setUrl(urlMapping);
            savedEmergencyPlanDrill.setRealPath(savedFile.getAbsolutePath());
            savedEmergencyPlanDrill.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedEmergencyPlanDrill.setCreateDate(new Date());
        if(!StringUtils.isEmpty(emergencyPlanDrillDto.getBeginDate())){
            try {
                savedEmergencyPlanDrill.setBeginDate(format.parse(emergencyPlanDrillDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedEmergencyPlanDrill.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillDto.getEndDate())){
            try {
                savedEmergencyPlanDrill.setEndDate(format.parse(emergencyPlanDrillDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedEmergencyPlanDrill.setEndDate(null);
            }
        }
        savedEmergencyPlanDrill.setOrg(getOrg(request));
        savedEmergencyPlanDrill.setCreator(getCurrentUsername(request));
        emergencyPlanDrillService.addObj(savedEmergencyPlanDrill);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param emergencyPlanDrillDto
     * @return
     */
    @PostMapping("/emergencyPlanDrill/updateEmergencyPlanDrill")
    public JsonResult updateEmergencyPlanDrill(EmergencyPlanDrillDto emergencyPlanDrillDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        EmergencyPlanDrill savedEmergencyPlanDrill = emergencyPlanDrillService.queryObjById(emergencyPlanDrillDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedEmergencyPlanDrill.setUrl(urlMapping);
            savedEmergencyPlanDrill.setRealPath(savedFile.getAbsolutePath());
            savedEmergencyPlanDrill.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillDto.getBeginDate())){
            try {
                savedEmergencyPlanDrill.setBeginDate(format.parse(emergencyPlanDrillDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedEmergencyPlanDrill.setBeginDate(null);
            }
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillDto.getEndDate())){
            try {
                savedEmergencyPlanDrill.setEndDate(format.parse(emergencyPlanDrillDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedEmergencyPlanDrill.setEndDate(null);
            }
        }
        savedEmergencyPlanDrill.setName(emergencyPlanDrillDto.getName());
        savedEmergencyPlanDrill.setNote(emergencyPlanDrillDto.getNote());
        emergencyPlanDrillService.updateObj(savedEmergencyPlanDrill);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/emergencyPlanDrill/emergencyPlanDrill/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        emergencyPlanDrillService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}

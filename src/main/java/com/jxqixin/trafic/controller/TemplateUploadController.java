package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.UploadFileDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传控制器
 */
@RestController
public class TemplateUploadController  extends CommonController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMeetingTemplateService meetingTemplateService;
    @Autowired
    private ITrainingTemplateService trainingTemplateService;
    @Autowired
    private ISecurityCheckTemplateService securityCheckTemplateService;
    @Autowired
    private ITankVehicleTemplateService tankVehicleTemplateService;
    @Autowired
    private IDangerGoodsCheckTemplateService dangerGoodsCheckTemplateService;
    @Autowired
    private IRuleTemplateService ruleTemplateService;
    @Autowired
    private IResponsibilityTemplateService responsibilityTemplateService;
    @Autowired
    private IMeetingService meetingService;
    @Autowired
    private ITrainingService trainingService;
    @Autowired
    private ISecurityCheckService securityCheckService;
    @Autowired
    private ITankVehicleService tankVehicleService;
    @Autowired
    private IDangerGoodsCheckService dangerGoodsCheckService;
    @Autowired
    private IRulesService rulesService;
    @Autowired
    private IResponsibilityService responsibilityService;
    /**
     * 上传公司图片
     * @return
     */
    @PostMapping("/templateUpload")
    public JsonResult uploadImages(@RequestParam("file") MultipartFile file, UploadFileDto uploadFileDto, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + "templates";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
           updateUrlAndRealPath(urlMapping,savedFile.getAbsolutePath(),uploadFileDto);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result,urlMapping);
    }
    /**
     * 更新模板的访问路径和真实存储路径
     */
    private void updateUrlAndRealPath(String url,String realPath,UploadFileDto uploadFileDto){
        switch (uploadFileDto.getType()){
            case "meetingTemplate":{
                MeetingTemplate meetingTemplate = meetingTemplateService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(meetingTemplate.getRealPath())){
                    deleteTemplateFile(meetingTemplate.getRealPath());
                }
                meetingTemplate.setUrl(url);
                meetingTemplate.setRealPath(realPath);
                meetingTemplateService.updateObj(meetingTemplate);
                break;
            }
            case "trainingTemplate":{
               TrainingTemplate trainingTemplate = trainingTemplateService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(trainingTemplate.getRealPath())){
                    deleteTemplateFile(trainingTemplate.getRealPath());
                }
                trainingTemplate.setUrl(url);
                trainingTemplate.setRealPath(realPath);
                trainingTemplateService.updateObj(trainingTemplate);
                break;
            }
            case "securityCheckTemplate":{
                SecurityCheckTemplate securityCheckTemplate = securityCheckTemplateService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(securityCheckTemplate.getRealPath())){
                    deleteTemplateFile(securityCheckTemplate.getRealPath());
                }
                securityCheckTemplate.setUrl(url);
                securityCheckTemplate.setRealPath(realPath);
                securityCheckTemplateService.updateObj(securityCheckTemplate);
                break;
            }
            case "tankVehicleTemplate":{
                TankVehicleTemplate tankVehicleTemplate = tankVehicleTemplateService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(tankVehicleTemplate.getRealPath())){
                    deleteTemplateFile(tankVehicleTemplate.getRealPath());
                }
                tankVehicleTemplate.setUrl(url);
                tankVehicleTemplate.setRealPath(realPath);
                tankVehicleTemplateService.updateObj(tankVehicleTemplate);
                break;
            }
            case "ruleTemplate":{
                RuleTemplate ruleTemplate = ruleTemplateService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(ruleTemplate.getRealPath())){
                    deleteTemplateFile(ruleTemplate.getRealPath());
                }
                ruleTemplate.setUrl(url);
                ruleTemplate.setRealPath(realPath);
                ruleTemplateService.updateObj(ruleTemplate);
                break;
            }
            case "responsibilityTemplate":{
                ResponsibilityTemplate responsibilityTemplate = responsibilityTemplateService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(responsibilityTemplate.getRealPath())){
                    deleteTemplateFile(responsibilityTemplate.getRealPath());
                }
                responsibilityTemplate.setUrl(url);
                responsibilityTemplate.setRealPath(realPath);
                responsibilityTemplateService.updateObj(responsibilityTemplate);
                break;
            }
            case "dangerGoodsCheckTemplate":{
                DangerGoodsCheckTemplate dangerGoodsCheckTemplate = dangerGoodsCheckTemplateService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(dangerGoodsCheckTemplate.getRealPath())){
                    deleteTemplateFile(dangerGoodsCheckTemplate.getRealPath());
                }
                dangerGoodsCheckTemplate.setUrl(url);
                dangerGoodsCheckTemplate.setRealPath(realPath);
                dangerGoodsCheckTemplateService.updateObj(dangerGoodsCheckTemplate);
                break;
            }
            case "training":{
                Training training = trainingService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(training.getRealPath())){
                    deleteTemplateFile(training.getRealPath());
                }
                training.setUrl(url);
                training.setRealPath(realPath);
                trainingService.updateObj(training);
                break;
            }
            case "meeting":{
                Meeting meeting = meetingService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(meeting.getRealPath())){
                    deleteTemplateFile(meeting.getRealPath());
                }
                meeting.setUrl(url);
                meeting.setRealPath(realPath);
                meetingService.updateObj(meeting);
                break;
            }
            case "securityCheck":{
                SecurityCheck securityCheck = securityCheckService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(securityCheck.getRealPath())){
                    deleteTemplateFile(securityCheck.getRealPath());
                }
                securityCheck.setUrl(url);
                securityCheck.setRealPath(realPath);
                securityCheckService.updateObj(securityCheck);
                break;
            }
            case "tankVehicle":{
                TankVehicle tankVehicle = tankVehicleService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(tankVehicle.getRealPath())){
                    deleteTemplateFile(tankVehicle.getRealPath());
                }
                tankVehicle.setUrl(url);
                tankVehicle.setRealPath(realPath);
                tankVehicleService.updateObj(tankVehicle);
                break;
            }
            case "rule":{
                Rules rules = rulesService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(rules.getRealPath())){
                    deleteTemplateFile(rules.getRealPath());
                }
                rules.setUrl(url);
                rules.setRealPath(realPath);
                rulesService.updateObj(rules);
                break;
            }
            case "responsibility":{
                Responsibility responsibility = responsibilityService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(responsibility.getRealPath())){
                    deleteTemplateFile(responsibility.getRealPath());
                }
                responsibility.setUrl(url);
                responsibility.setRealPath(realPath);
                responsibilityService.updateObj(responsibility);
                break;
            }
            case "dangerGoodsCheck":{
                DangerGoodsCheck dangerGoodsCheck = dangerGoodsCheckService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(dangerGoodsCheck.getRealPath())){
                    deleteTemplateFile(dangerGoodsCheck.getRealPath());
                }
                dangerGoodsCheck.setUrl(url);
                dangerGoodsCheck.setRealPath(realPath);
                dangerGoodsCheckService.updateObj(dangerGoodsCheck);
                break;
            }
        }
    }
    /**
     * 删除已存在的模板文件
     * @param realPath
     */
    private void deleteTemplateFile(String realPath) {
        File file = new File(realPath);
        if(file.exists()){
            file.delete();
        }
    }
}

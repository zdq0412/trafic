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
public class UploadController extends CommonController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IResumeService resumeService;
    @Autowired
    private IIDCardService idCardService;
    @Autowired
    private IContractService contractService;
    @Autowired
    private IQualificationDocumentService qualificationDocumentService;
    @Autowired
    private IJobHistoryService jobHistoryService;
    @Autowired
    private IInductionTrainingService inductionTrainingService;
    @Autowired
    private ISafetyResponsibilityAgreementService safetyResponsibilityAgreementService;
    @Autowired
    private ITrainingExamineService trainingExamineService;
    @Autowired
    private IOtherDocumentService otherDocumentService;
    @Autowired
    private ISafetyProductionCostPlanDetailService safetyProductionCostPlanDetailService;
    /**
     * 上传人员档案
     * @return
     */
    @PostMapping("/employeeDocumentUpload")
    public JsonResult employeeDocumentUpload(@RequestParam("file") MultipartFile file, UploadFileDto uploadFileDto, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + uploadFileDto.getType();
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
     * 上传安全投入台账
     * @return
     */
    @PostMapping("/safetyAccountUpload")
    public JsonResult safetyAccountUpload(@RequestParam("file") MultipartFile file, UploadFileDto uploadFileDto, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + "safetyAccount";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }

            SafetyProductionCostPlanDetail  detail = safetyProductionCostPlanDetailService.queryObjById(uploadFileDto.getId());
            if(!StringUtils.isEmpty(detail.getRealPath())){
                deleteTemplateFile(detail.getRealPath());
            }
            detail.setUrl(urlMapping);
            detail.setRealPath(savedFile.getAbsolutePath());

            safetyProductionCostPlanDetailService.updateObj(detail);
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
            case "resume":{
                Resume resume = resumeService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(resume.getRealPath())){
                    deleteTemplateFile(resume.getRealPath());
                }
                resume.setUrl(url);
                resume.setRealPath(realPath);
                resumeService.updateObj(resume);
                break;
            }
            case "idcard":{
               IDCard idCard = idCardService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(idCard.getRealPath())){
                    deleteTemplateFile(idCard.getRealPath());
                }
                idCard.setUrl(url);
                idCard.setRealPath(realPath);
                idCardService.updateObj(idCard);
                break;
            }
            case "contract":{
                Contract contract = contractService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(contract.getRealPath())){
                    deleteTemplateFile(contract.getRealPath());
                }
                contract.setUrl(url);
                contract.setRealPath(realPath);
                contractService.updateObj(contract);
                break;
            }
            case "qualificationDocument":{
                QualificationDocument qualificationDocument = qualificationDocumentService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(qualificationDocument.getRealPath())){
                    deleteTemplateFile(qualificationDocument.getRealPath());
                }
                qualificationDocument.setUrl(url);
                qualificationDocument.setRealPath(realPath);
                qualificationDocumentService.updateObj(qualificationDocument);
                break;
            }
            case "jobHistory":{
                JobHistory jobHistory = jobHistoryService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(jobHistory.getRealPath())){
                    deleteTemplateFile(jobHistory.getRealPath());
                }
                jobHistory.setUrl(url);
                jobHistory.setRealPath(realPath);
                jobHistoryService.updateObj(jobHistory);
                break;
            }
            case "inductionTraining":{
                InductionTraining inductionTraining = inductionTrainingService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(inductionTraining.getRealPath())){
                    deleteTemplateFile(inductionTraining.getRealPath());
                }
                inductionTraining.setUrl(url);
                inductionTraining.setRealPath(realPath);
                inductionTrainingService.updateObj(inductionTraining);
                break;
            }
            case "safetyResponsibilityAgreement":{
                SafetyResponsibilityAgreement safetyResponsibilityAgreement = safetyResponsibilityAgreementService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(safetyResponsibilityAgreement.getRealPath())){
                    deleteTemplateFile(safetyResponsibilityAgreement.getRealPath());
                }
                safetyResponsibilityAgreement.setUrl(url);
                safetyResponsibilityAgreement.setRealPath(realPath);
                safetyResponsibilityAgreementService.updateObj(safetyResponsibilityAgreement);
                break;
            }
            case "trainingExamine":{
                TrainingExamine trainingExamine = trainingExamineService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(trainingExamine.getRealPath())){
                    deleteTemplateFile(trainingExamine.getRealPath());
                }
                trainingExamine.setUrl(url);
                trainingExamine.setRealPath(realPath);
                trainingExamineService.updateObj(trainingExamine);
                break;
            }
            case "otherDocument":{
                OtherDocument otherDocument = otherDocumentService.queryObjById(uploadFileDto.getId());
                if(!StringUtils.isEmpty(otherDocument.getRealPath())){
                    deleteTemplateFile(otherDocument.getRealPath());
                }
                otherDocument.setUrl(url);
                otherDocument.setRealPath(realPath);
                otherDocumentService.updateObj(otherDocument);
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

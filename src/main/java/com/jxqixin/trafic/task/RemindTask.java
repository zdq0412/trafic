package com.jxqixin.trafic.task;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IOrgService;
import com.jxqixin.trafic.service.IRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 定时任务类,企业资质、人员资质、设备资质和企业台账提醒
 * 年度台账：每年2月1日
 * 半年(应急预案演练):每年7月1日和次年1月1日
 * 月度台账：每月1日凌晨2点
 * 每天台账：每天凌晨1点
 */
@Configuration
@EnableScheduling
public class RemindTask {
    @Autowired
    private IRemindService remindService;
    @Autowired
    private IOrgService orgService;
    /**
     * 每天凌晨一点执行，资质文件
     * 企业资质文件、人员资质文件、人员合同
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(fixedRate = 5000)
    public void remindDay(){
        List<Remind> remindList = new ArrayList<>();
        Date now = new Date();
        //查询警告信息，有效期-当前>=30
        //企业资质文件
        findWarningOrgDoc(remindList,now);
        findWarningContract(remindList,now);
        findWarningQualificationDocument(remindList,now);
        if(!CollectionUtils.isEmpty(remindList)){
            remindService.saveAll(remindList);
            remindList.clear();
        }
        //查询过期，当前-有效期>0
        //更新过期记录
        remindService.updateRemind2Expired();
        findExpiredOrgDoc(remindList,now);
        findExpiredContract(remindList,now);
        findExpiredQualificationDocument(remindList,now);
        if(!CollectionUtils.isEmpty(remindList)){
            remindService.saveAll(remindList);
            remindList.clear();
        }
    }
    /**
     * 查找即将过期的人员资质文件
     * @param remindList
     * @param now
     */
    private void findWarningQualificationDocument(List<Remind> remindList,Date now){
        List<QualificationDocument> qualificationDocumentList = remindService.findWarningQualificationDocument();
        if(!CollectionUtils.isEmpty(qualificationDocumentList)){
            qualificationDocumentList.forEach(qualificationDocument -> {
                Remind remind = new Remind();
                remind.setBgColor("#FF9900");
                remind.setCreateDate(now);
                remind.setEndDate(qualificationDocument.getEndDate());
                remind.setName(qualificationDocument.getName());
                //根据人员资质ID查找人员
                Employee employee = remindService.findEmployeeByQualificationDocumentId(qualificationDocument.getId());
                if(employee!=null){
                    Org org = remindService.findOrgByEmployeeId(employee.getId());
                    if(org!=null){
                        remind.setOrgId(org.getId());
                        remind.setOrgName(org.getName());
                    }
                }
                remind.setSubjectName("人员资质");
                remind.setSrcId(qualificationDocument.getId());
                remind.setType("资质");
                remind.setTableName("m011_qualification_document");
                remind.setColName("endDate");
                remind.setDeleted(false);
                remindList.add(remind);
            });
        }
    }
    /**
     * 查找即将过期的人员劳动合同
     * @param remindList
     * @param now
     */
    private void findWarningContract(List<Remind> remindList,Date now){
        List<Contract> contractList = remindService.findWarningContract();
        if(!CollectionUtils.isEmpty(contractList)){
            contractList.forEach(contract -> {
                Remind remind = new Remind();
                remind.setBgColor("#FF9900");
                remind.setCreateDate(now);
                remind.setEndDate(contract.getEndDate());
                remind.setName(contract.getName());
                Employee employee = remindService.findEmployeeByContractId(contract.getId());
                if(employee!=null){
                    Org org = remindService.findOrgByEmployeeId(employee.getId());
                    if(org!=null){
                        remind.setOrgId(org.getId());
                        remind.setOrgName(org.getName());
                    }
                }
                remind.setSrcId(contract.getId());
                remind.setSubjectName("劳动合同");
                remind.setType("资质");
                remind.setTableName("m011_contract");
                remind.setDeleted(false);
                remind.setColName("endDate");
                remindList.add(remind);
            });
        }
    }
    /**
     * 查找即将过期的企业资质文件
     * @param remindList
     * @param now
     */
    private void findWarningOrgDoc(List<Remind> remindList,Date now){
        List<OrgDoc> orgDocList = remindService.findWarningOrgDoc();
        if(!CollectionUtils.isEmpty(orgDocList)){
            orgDocList.forEach(orgDoc -> {
                Remind remind = new Remind();
                remind.setBgColor("#FF9900");
                remind.setCreateDate(now);
                remind.setEndDate(orgDoc.getEndDate());
                remind.setName(orgDoc.getName());
                Org org = remindService.findOrgByOrgDocId(orgDoc.getId());
                if(org!=null){
                    remind.setOrgId(org.getId());
                    remind.setOrgName(org.getName());
                }
                remind.setSrcId(orgDoc.getId());
                remind.setSubjectName("企业资质");
                remind.setType("资质");
                remind.setTableName("m001_org_doc");
                remind.setDeleted(false);
                remind.setColName("endDate");
                remindList.add(remind);
            });
        }
    }
    /**
     * 查找已经过期的人员资质文件
     * @param remindList
     * @param now
     */
    private void findExpiredQualificationDocument(List<Remind> remindList,Date now){
        List<QualificationDocument> qualificationDocumentList = remindService.findExpiredQualificationDocument();
        if(!CollectionUtils.isEmpty(qualificationDocumentList)){
            qualificationDocumentList.forEach(qualificationDocument -> {
                Remind remind = new Remind();
                remind.setBgColor("#FF0000");
                remind.setCreateDate(now);
                remind.setEndDate(qualificationDocument.getEndDate());
                remind.setName(qualificationDocument.getName());
                Employee employee = remindService.findEmployeeByQualificationDocumentId(qualificationDocument.getId());
                if(employee!=null){
                    Org org = remindService.findOrgByEmployeeId(employee.getId());
                    if(org!=null){
                        remind.setOrgId(org.getId());
                        remind.setOrgName(org.getName());
                    }
                }
                remind.setSrcId(qualificationDocument.getId());
                remind.setSubjectName("人员资质");
                remind.setType("资质");
                remind.setTableName("m011_qualification_document");
                remind.setDeleted(false);
                remind.setDeductPoints(5);
                remind.setColName("endDate");
                remindList.add(remind);
            });
        }
    }
    /**
     * 查找已经过期的人员劳动合同
     * @param remindList
     * @param now
     */
    private void findExpiredContract(List<Remind> remindList,Date now){
        List<Contract> contractList = remindService.findExpiredContract();
        if(!CollectionUtils.isEmpty(contractList)){
            contractList.forEach(contract -> {
                Remind remind = new Remind();
                remind.setBgColor("#FF0000");
                remind.setCreateDate(now);
                remind.setEndDate(contract.getEndDate());
                remind.setName(contract.getName());
                //根据劳动合同ID查找人员对象
                Employee employee = remindService.findEmployeeByContractId(contract.getId());
                if(employee!=null){
                    //根据人员ID查找所在企业
                    Org org = remindService.findOrgByEmployeeId(employee.getId());
                    if(org!=null){
                        remind.setOrgId(org.getId());
                        remind.setOrgName(org.getName());
                    }
                }
                remind.setSrcId(contract.getId());
                remind.setSubjectName("劳动合同");
                remind.setType("资质");
                remind.setTableName("m011_contract");
                remind.setDeleted(false);
                remind.setDeductPoints(5);
                remind.setColName("endDate");
                remindList.add(remind);
            });
        }
    }
    /**
     * 查找已经过期的企业资质文件
     * @param remindList
     * @param now
     */
    private void findExpiredOrgDoc(List<Remind> remindList,Date now){
        List<OrgDoc> orgDocList = remindService.findExpiredOrgDoc();
        if(!CollectionUtils.isEmpty(orgDocList)){
            orgDocList.forEach(orgDoc -> {
                Remind remind = new Remind();
                remind.setBgColor("#FF0000");
                remind.setCreateDate(now);
                remind.setEndDate(orgDoc.getEndDate());
                remind.setName(orgDoc.getName());
                //根据ID查找企业对象
                Org org = remindService.findOrgByOrgDocId(orgDoc.getId());
                if(org!=null){
                    remind.setOrgId(org.getId());
                    remind.setOrgName(org.getName());
                }
                remind.setSrcId(orgDoc.getId());
                remind.setSubjectName("企业资质");
                remind.setType("资质");
                remind.setTableName("m001_org_doc");
                remind.setDeleted(false);
                remind.setDeductPoints(5);
                remind.setColName("endDate");
                remindList.add(remind);
            });
        }
    }
    @Scheduled(cron = "0 0 2 1 * ?")
    public void remindMonth(){
        remindService.updatePreAccountOfMonth2Expired();
    }
    /**
     * 每月二十号凌晨两点执行，月度计划
     * 会议、培训和日常安全检查
     */
    @Scheduled(cron = "0 0 2 20 * ?")
    public void warningMonth(){
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,1);
        c.set(Calendar.DAY_OF_MONTH,1);
        c.add(Calendar.DAY_OF_MONTH,-1);
        //查找所有企业
        List<Org> orgList = orgService.findAll();
        if(!CollectionUtils.isEmpty(orgList)){
            for (Org org : orgList) {
                //查询上一个月的会议、培训和日常安全检查
                List<Meeting> meetingList = remindService.findCurrentMonthMeeting(org.getId());
                if(CollectionUtils.isEmpty(meetingList)){
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m021_meeting");
                    remind.setType("月计划");
                    remind.setSubjectName("安全会议台账");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setEndDate(c.getTime());
                    remind.setCreateDate(now);
                    remind.setBgColor("#FF9900");
                    remind.setDeductPoints(0);
                    remind.setColName("meetingDate");
                    remindService.addObj(remind);
                }
                List<Training> trainingList = remindService.findCurrentMonthTraining(org.getId());
                if(CollectionUtils.isEmpty(trainingList)){
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m022_training");
                    remind.setType("月计划");
                    remind.setSubjectName("安全培训台账");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setCreateDate(now);
                    remind.setEndDate(c.getTime());
                    remind.setBgColor("#FF9900");
                    remind.setDeductPoints(0);
                    remind.setColName("trainingDate");
                    remindService.addObj(remind);
                }
                List<SecurityCheck> securityCheckList = remindService.findCurrentMonthSecurityCheck(org.getId());
                if(CollectionUtils.isEmpty(securityCheckList)){
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m023_security_check");
                    remind.setType("月计划");
                    remind.setSubjectName("日常安全检查台账");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setCreateDate(now);
                    remind.setBgColor("#FF9900");
                    remind.setDeductPoints(0);
                    remind.setColName("checkDate");
                    remind.setEndDate(c.getTime());
                    remindService.addObj(remind);
                }
            }
        }
    }
    /**
     * 半年度（应急预案演练）
     */
    @Scheduled(cron = "0 0 3 1 6 ?")
    public void warningJuly(){
        remindHalfYear();
    }
    /**
     * 半年度（应急预案演练）
     */
    @Scheduled(cron = "0 0 3 1 12 ?")
    public void warningJanuary(){
        remindHalfYear();
    }
    /**
     * 半年度（应急预案演练）
     */
    @Scheduled(cron = "0 0 3 1 7 ?")
    public void remindJuly(){
        remindService.updateEmergencyPlanDrill2Expired();
    }
    /**
     * 半年度（应急预案演练）
     */
    @Scheduled(cron = "0 0 3 1 1 ?")
    public void remindJanuary(){
        remindService.updateEmergencyPlanDrill2Expired();
    }
    /**
     * 半年一次的应急预案演练
     */
    private void remindHalfYear(){
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,1);
        c.set(Calendar.DAY_OF_MONTH,1);
        c.add(Calendar.DAY_OF_MONTH,-1);
        //查找所有企业
        List<Org> orgList = orgService.findAll();
        if(!CollectionUtils.isEmpty(orgList)){
            for (Org org : orgList) {
                //查询前半年的应急预案演练
                List<EmergencyPlanDrill> emergencyPlanDrillList = remindService.findPreHalfYearEmergencyPlanDrill(org.getId());
                if(CollectionUtils.isEmpty(emergencyPlanDrillList)){
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m028_emergency_plan_drill");
                    remind.setType("半年计划");
                    remind.setSubjectName("应急预案演练台账");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setEndDate(c.getTime());
                    remind.setCreateDate(now);
                    remind.setBgColor("#FF9900");
                    remind.setDeductPoints(0);
                    remind.setColName("endDate");
                    remindService.addObj(remind);
                }
            }
        }
    }
    /**
     * 年度台账
     */
    @Scheduled(cron = "0 0 1 1 2 ?")
    public void remindYear(){
        remindService.updateAccountOfYear2Expired();
    }
    /**
     * 年度台账
     */
    @Scheduled(cron = "0 0 1 1 1 ?")
    public void warningYear(){
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,1);
        c.add(Calendar.DAY_OF_MONTH,-1);
        List<Org> orgList = orgService.findAll();
        if(!CollectionUtils.isEmpty(orgList)){
            orgList.forEach(org -> {
                //安全责任考核
                List<SecurityExamination> securityExaminationList = remindService.findCurrentYearSecurityExamination(org.getId());
                if(CollectionUtils.isEmpty(securityExaminationList)) {
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m026_security_examination");
                    remind.setType("年计划");
                    remind.setSubjectName("安全责任考核");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setEndDate(c.getTime());
                    remind.setCreateDate(now);
                    remind.setBgColor("#FF9900");
                    remind.setDeductPoints(0);
                    remind.setColName("endDate");
                    remindService.addObj(remind);
                }
                //安全目标考核
                List<GoalExamination> goalExaminationList = remindService.findCurrentYearGoalExamination(org.getId());
                if(CollectionUtils.isEmpty(goalExaminationList)) {
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m027_goal_examination");
                    remind.setType("年计划");
                    remind.setSubjectName("安全目标考核");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setEndDate(c.getTime());
                    remind.setCreateDate(now);
                    remind.setBgColor("#FF9900");
                    remind.setColName("endDate");
                    remind.setDeductPoints(0);
                    remindService.addObj(remind);
                }
                //职业健康记录
                List<HealthyRecord> healthyRecordList = remindService.findCurrentYearHealthyRecord(org.getId());
                if(CollectionUtils.isEmpty(healthyRecordList)) {
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m029_healthy_record");
                    remind.setType("年计划");
                    remind.setSubjectName("职业健康记录");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setEndDate(c.getTime());
                    remind.setCreateDate(now);
                    remind.setBgColor("#FF9900");
                    remind.setDeductPoints(0);
                    remind.setColName("endDate");
                    remindService.addObj(remind);
                }
                //标准化自评
                List<Standardization> standardizationList = remindService.findCurrentYearStandardization(org.getId());
                if(CollectionUtils.isEmpty(standardizationList)) {
                    Remind remind = new Remind();
                    remind.setDeleted(false);
                    remind.setTableName("m030_standardization");
                    remind.setType("年计划");
                    remind.setSubjectName("标准化自评");
                    remind.setOrgName(org.getName());
                    remind.setOrgId(org.getId());
                    remind.setEndDate(c.getTime());
                    remind.setCreateDate(now);
                    remind.setBgColor("#FF9900");
                    remind.setColName("endDate");
                    remind.setDeductPoints(0);
                    remindService.addObj(remind);
                }
            });
        }
    }
}

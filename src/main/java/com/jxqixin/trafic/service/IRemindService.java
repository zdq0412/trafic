package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.RemindDto;
import com.jxqixin.trafic.model.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRemindService extends ICommonService<Remind> {
    /**
     * 分页查询信息
     * @param remindDto
     * @return
     */
    Page findReminds(RemindDto remindDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 查询即将过期企业资质文件，提前三十天
     * @return
     */
    List<OrgDoc> findWarningOrgDoc();
    /**
     * 查询即将过期的人员合同文件，提前三十天
     * @return
     */
    List<Contract> findWarningContract();
    /**
     * 查询即将过期的人员资质文件，提前三十天
     * @return
     */
    List<QualificationDocument> findWarningQualificationDocument();

    void saveAll(List<Remind> reminds);
    /**
     * 更新为过期状态
     */
    void updateRemind2Expired();

    /**
     * 查找已经过期的企业资质文件
     * @return
     */
    List<OrgDoc> findExpiredOrgDoc();

    /**
     * 查找已经过期的人员资质文件
     * @return
     */
    List<QualificationDocument> findExpiredQualificationDocument();
    /**
     * 查找已经过期的合同
     * @return
     */
    List<Contract> findExpiredContract();
    /**
     * 查找已经过期的设备档案
     * @return
     */
    List<DeviceArchive> findExpiredDeviceArchives();
    /**
     * 查找当前月的安全会议台账
     * @param orgId
     * @return
     */
    List<Meeting> findCurrentMonthMeeting(String orgId);
    /**
     * 查找当前月的安全培训台账
     * @param orgId
     * @return
     */
    List<Training> findCurrentMonthTraining(String orgId);
    /**
     * 查找当前月的日常安全检查台账
     * @param orgId
     * @return
     */
    List<SecurityCheck> findCurrentMonthSecurityCheck(String orgId);
    /**
     * 查找前半年的应急预案演练
     * @param orgId
     * @return
     */
    List<EmergencyPlanDrill> findPreHalfYearEmergencyPlanDrill(String orgId);
    /**
     * 更新应急预案演练为过期
     */
    void updateEmergencyPlanDrill2Expired();

    /**
     * 查找当前年的责任考核
     * @param orgId
     * @return
     */
    List<SecurityExamination> findCurrentYearSecurityExamination(String orgId);

    /**
     * 查找当前年的责任目标考核
     * @param orgId
     * @return
     */
    List<GoalExamination> findCurrentYearGoalExamination(String orgId);

    /**
     * 查找当前年的职业健康记录
     * @param orgId
     * @return
     */
    List<HealthyRecord> findCurrentYearHealthyRecord(String orgId);

    /**
     * 查找当前年的标准化自评
     * @param orgId
     * @return
     */
    List<Standardization> findCurrentYearStandardization(String orgId);

    /**
     * 更新当前年年度台账为过期
     */
    void updateAccountOfYear2Expired();
    /**
     * 更新上个月台账为过期
     */
    void updatePreAccountOfMonth2Expired();
    /**
     * 查找所有的提醒记录
     * @param org
     * @return
     */
    List<Remind> findAllReminds(Org org);
    /**
     * 根据企业资质文档ID查找企业
     * @param orgDocId
     * @return
     */
    Org findOrgByOrgDocId(String orgDocId);
    /**
     * 根据劳动合同ID查找人员
     * @param contractId
     * @return
     */
    Employee findEmployeeByContractId(String contractId);

    /**
     * 根据人员ID查找企业
     * @param employeeId
     * @return
     */
    Org findOrgByEmployeeId(String employeeId);

    /**
     * 根据人员资质文件ID查找人员信息
     * @param qualificationDocumentId
     * @return
     */
    Employee findEmployeeByQualificationDocumentId(String qualificationDocumentId);
    /**
     * 根据ID删除提醒信息
     * @param id
     */
    void deleteRemind(String id);
    /**
     * 根据设备档案ID查找设备信息
     * @param deviceArchiveId
     * @return
     */
    Device findDeviceByDeviceArchiveId(String deviceArchiveId);
    /**
     * 根据设备ID查找机构信息
     * @param deviceId
     * @return
     */
    Org findOrgByDeviceId(String deviceId);
    /**
     * * 查询即将过期设备档案，提前三十天
     * @return
     */
    List<DeviceArchive> findWarningDeviceArchives();
}

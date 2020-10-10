package com.jxqixin.trafic.mapper;
import com.jxqixin.trafic.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
@Mapper
public interface RemindMapper {
    /**
     * 提前30天查询即将过期的企业资质文件
     * @return
     */
    @Select("select * from m001_org_doc od  where datediff(od.endDate,now()) BETWEEN 1 and 30 and not EXISTS (select r.srcId from remind r where r.srcId=od.id )")
    List<OrgDoc> findWarningOrgDoc();
    @Select("select * from m011_contract od  where datediff(od.endDate,now()) BETWEEN 1 and 30 and not EXISTS (select r.srcId from remind r where r.srcId=od.id )")
    List<Contract> findWarningContract();
    @Select("select * from m011_qualification_document od  where datediff(od.endDate,now()) BETWEEN 1 and 30 and not EXISTS (select r.srcId from remind r where r.srcId=od.id )")
    List<QualificationDocument> findWarningQualificationDocument();
    @Update("update remind  set bgColor='#FF0000',deductPoints=5  where datediff(now(),endDate)>0 and type='qualifications' and deleted=0")
    void updateRemind2Expired();
    @Select("select * from m001_org_doc od  where datediff(od.endDate,now())<0 and not EXISTS (select r.srcId from remind r where r.srcId=od.id )")
    List<OrgDoc> findExpiredOrgDoc();
    @Select("select * from m011_qualification_document od  where datediff(od.endDate,now())<0 and not EXISTS (select r.srcId from remind r where r.srcId=od.id )")
    List<QualificationDocument> findExpiredQualificationDocument();
    @Select("select * from m011_contract od  where datediff(od.endDate,now())<0 and not EXISTS (select r.srcId from remind r where r.srcId=od.id )")
    List<Contract> findExpiredContract();
    @Select("SELECT * FROM m021_meeting WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( meetingDate, '%Y%m' ) ) =0 and org_id=#{orgId}")
    List<Meeting> findCurrentMonthMeeting(String orgId);
    @Select("SELECT * FROM m022_training WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( trainingDate, '%Y%m' ) ) =0 and org_id=#{orgId}")
    List<Training> findCurrentMonthTraining(String orgId);
    @Select("SELECT * FROM m023_security_check WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( checkDate, '%Y%m' ) ) =0 and org_id=#{orgId}")
    List<SecurityCheck> findCurrentMonthSecurityCheck(String orgId);
    @Select("SELECT * FROM m028_emergency_plan_drill WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( endDate, '%Y%m' ) )>=5 and org_id=#{orgId}")
    List<EmergencyPlanDrill> findPreHalfYearEmergencyPlanDrill(String orgId);
    @Update("update remind  set bgColor='#FF0000',deductPoints=5  WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( endDate, '%Y%m' ) )>=6 and tableName='m028_emergency_plan_drill' and deleted=0")
    void updateEmergencyPlanDrill2Expired();
    @Select("select * from m026_security_examination where year(endDate)=year(now()) and org_id=#{orgId}")
    List<SecurityExamination> findCurrentYearSecurityExamination(String orgId);
    @Select("select * from m027_goal_examination where year(endDate)=year(now()) and org_id=#{orgId}")
    List<GoalExamination> findCurrentYearGoalExamination(String orgId);
    @Select("select * from m029_healthy_record where year(endDate)=year(now()) and org_id=#{orgId}")
    List<HealthyRecord> findCurrentYearHealthyRecord(String orgId);
    @Select("select * from m030_standardization where year(endDate)=year(now()) and org_id=#{orgId}")
    List<Standardization> findCurrentYearStandardization(String orgId);
    @Update("update remind  set bgColor='#FF0000',deductPoints=5  WHERE year(endDate)=year(now()) and deleted=0 and  tableName in('m026_security_examination','m027_goal_examination','m029_healthy_record','m030_standardization')")
    void updateAccountOfYear2Expired();
    @Update("update remind  set bgColor='#FF0000',deductPoints=5  WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( endDate, '%Y%m' ) )=1 and deleted=0 and  tableName in('m021_meeting','m022_training','m023_security_check')")
    void updatePreAccountOfMonth2Expired();
    @Select("select o.* from org o inner join m001_org_doc d on d.org_id=o.id where d.id=#{orgDocId}")
    Org findOrgByOrgDocId(String orgDocId);
    @Select("select o.* from m003_employee o inner join m011_contract d on d.emp_id=o.id where d.id=#{contractId}")
    Employee findEmployeeByContractId(String contractId);
    @Select("select o.* from org o inner join m003_employee d on d.org_id=o.id where d.id=#{employeeId}")
    Org findOrgByEmployeeId(String employeeId);
    @Select("select o.* from m003_employee o inner join m011_qualification_document d on d.emp_id=o.id where d.id=#{qualificationDocumentId}")
    Employee findEmployeeByQualificationDocumentId(String qualificationDocumentId);
    /**
     * 根据SRCID查找截止日期
     * @param srcId
     * @return
     */
    @Select("select endDate from ${param2} where id=#{param1}")
    Date findEndDateById(String srcId,String tableName);
    /**
     * 月度计划、半年计划、年度计划
     * @param beginDate
     * @param endDate
     * @param colName
     * @param tableName
     * @return
     */
    @Select("select count(id) from ${param4} where ${param3} between #{param1} and #{param2}")
    Long findPlanBetween(Date beginDate, Date endDate, String colName, String tableName);
}

package com.jxqixin.trafic.task;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
    /**
     * 每月一号凌晨两点执行，月度计划
     */
   //@Scheduled(cron = "0 0 2 1 * ?")
   @Scheduled(fixedRate = 5000)
    public void remindMonth(){
    }
    /**
     * 每天凌晨一点执行，资质文件
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(fixedRate = 10000)
    public void remindDay(){
    }
}

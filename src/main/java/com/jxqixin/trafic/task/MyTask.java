package com.jxqixin.trafic.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务类
 */
@Configuration
@EnableScheduling
public class MyTask {
    /**
     * 每天0点执行，检查我的简历中是否有未处理，如果存在，则进行系统回收
     */
   @Scheduled(cron = "0 0 1 * * ?")
    public void checkMyResume(){
    }
}

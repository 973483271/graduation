package com.bysj.common.quartz;

import com.bysj.sys.entity.Quartz;
import com.bysj.sys.service.IUserService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * Created by 郑文旭 on 2020/3/13.
 */

@Service
public class MyScheduler {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private IUserService iUserServiceImpl;


    public void scheduleJobs() throws SchedulerException {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        startJob1(scheduler);
        startJob2(scheduler);
        startJob3(scheduler);
    }
    //出题关闭定时任务
    private void startJob1(Scheduler scheduler) throws SchedulerException{
        //获取出题状态信息
        Quartz q =  iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()!=null){
            String year = q.getqCron().substring(0,4);
            String month = q.getqCron().substring(5,7);
            String day = q.getqCron().substring(8);
            //拼接定时任务时间
            String cron = "59 "+"59 "+"23 "+day+" "+month+" "+"? "+year;
            JobDetail jobDetail = JobBuilder.newJob(QuartzAssignTopic.class)
                    .withIdentity("job1", "group1").build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,cronTrigger);
        }
    }
    //审题关闭定时任务
    private void startJob2(Scheduler scheduler) throws SchedulerException{
        //获取审题状态信息
        Quartz q =  iUserServiceImpl.getProcessControlData().get(1);
        if(q.getqCron()!=null) {
            String year = q.getqCron().substring(0, 4);
            String month = q.getqCron().substring(5, 7);
            String day = q.getqCron().substring(8);
            //拼接定时任务时间
            String cron = "59 "+"59 "+"23 "+day+" "+month+" "+"? "+year;
            JobDetail jobDetail = JobBuilder.newJob(QuartzExaminTopic.class)
                    .withIdentity("job2", "group2").build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2")
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }
    }
    //选题关闭定时任务
    private void startJob3(Scheduler scheduler) throws SchedulerException{
        //获取选题状态信息
        Quartz q =  iUserServiceImpl.getProcessControlData().get(2);
        if(q.getqCron()!=null) {
            String year = q.getqCron().substring(0, 4);
            String month = q.getqCron().substring(5, 7);
            String day = q.getqCron().substring(8);
            //拼接定时任务时间
            String cron = "59 "+"59 "+"23 "+day+" "+month+" "+"? "+year;
            JobDetail jobDetail = JobBuilder.newJob(QuartzChooseTopic.class)
                    .withIdentity("job3", "group3").build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group3")
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }
    }

}
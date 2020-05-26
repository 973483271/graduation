package com.bysj.common.quartz;

import com.bysj.sys.entity.Quartz;
import com.bysj.sys.service.IUserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 审题关闭  定时任务
 */
public class QuartzExaminTopic implements Job{
    @Autowired
    private IUserService iUserServiceImpl;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      List<Quartz> quartzs =  iUserServiceImpl.getProcessControlData();
          quartzs.get(1).setqCron(null);
          quartzs.get(1).setqStatus("OFF");
          iUserServiceImpl.setProcessControlData(quartzs.get(1));
      }
}



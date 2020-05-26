package com.bysj.common.quartz;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bysj.sys.entity.Quartz;
import com.bysj.sys.service.IUserService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 出题关闭  定时任务
 */
public class QuartzAssignTopic implements Job{
    @Autowired
    private IUserService iUserServiceImpl;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      List<Quartz> quartzs =  iUserServiceImpl.getProcessControlData();
          quartzs.get(0).setqCron(null);
          quartzs.get(0).setqStatus("OFF");
          iUserServiceImpl.setProcessControlData(quartzs.get(0));
      }
}



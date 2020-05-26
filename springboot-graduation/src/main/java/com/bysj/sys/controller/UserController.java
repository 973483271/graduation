package com.bysj.sys.controller;


import com.bysj.common.model.R;
import com.bysj.common.quartz.MyScheduler;
import com.bysj.sys.entity.Quartz;
import com.bysj.sys.entity.User;
import com.bysj.sys.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.SchedulerException;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  用户 控制器
 * </p>
 *
 * @author jack
 * @since 2020-01-16
 */
@Controller
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private IUserService iUserServiceImpl;
    @Autowired
    private MyScheduler mySchedulerImpl;

    /**
     * 跳转到查看个人信息页面(管理员，老师，学生)
     * @return
     */
    @GetMapping("/data")
    public String main(){
        return "sys/users_data";
    }

    /**
     * 查询个人信息并返回
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public R adminList(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Object object = iUserServiceImpl.getUsersDataById(user.getUserId());
        return R.ok("请求成功",object);
    }

    /**
     * 保存用户修改的信息（admin,teacher,student）
     * @return
     */
    @PostMapping("/updateUserData")
    @ResponseBody
    public R updateUserData(String adminName,String adminTelphone,String adminEmail){
     Integer index =  iUserServiceImpl.updateUserData(adminName,adminTelphone,adminEmail);
     if(index!=0){
         return R.ok("修改成功");
     }
     return R.ok("修改失败");
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @GetMapping("/password")
    public String Password(){
        return "sys/users_password";
    }

    /**
     * 修改用户密码（admin,teacher,student）
     * @return
     */
    @PostMapping("/updateUserPassword")
    @ResponseBody
    public R updateUserPassword(String oldPassword, String comfirmPassword){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //判断原始密码是否正确
        if((iUserServiceImpl.confirmOldPassword(oldPassword))==null){
            return R.error("原始密码不正确");
        };

        Integer index = iUserServiceImpl.updateUserPassword(user.getUserId(),comfirmPassword);
        if(index!=0){
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }
    //  -----------------------------定时任务--------------------------

    /**
     * 跳转到控制流程页面
     * @return
     */
    @GetMapping("/processControl")
    public String processControl(Model model){
        //得到当前时间
        Date dd=new Date();
        //格式化
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        String time=sim.format(dd);
        model.addAttribute("nowTime",time);
        model.addAttribute("defaultTime",iUserServiceImpl.getProcessControlData());

        return "/sys/processControl_byAdmin";
    }

    /**
     * 获取各个阶段状态
     * @return
     */
    @GetMapping("/getProcessControlData")
    @ResponseBody
    public R getProcessControlData(){

         List<Quartz> quartzs = iUserServiceImpl.getProcessControlData();

        return R.ok()
                .put("total",quartzs.size())
                .put("rows", quartzs);
    }

    /**
     * 设置各阶段状态
     * @param quartz
     * @return
     */
    @PostMapping("/setProcessControlData")
    @ResponseBody
    public R setProcessControlData(@RequestBody  Quartz quartz){
       Integer index =  iUserServiceImpl.setProcessControlData(quartz);
       if(index==4){
           try {
               //开启定时任务
               mySchedulerImpl.scheduleJobs();
           } catch (SchedulerException e) {
               e.printStackTrace();
           }
           return R.ok("操作成功");
       }
       return R.error("操作失败");
    }
}

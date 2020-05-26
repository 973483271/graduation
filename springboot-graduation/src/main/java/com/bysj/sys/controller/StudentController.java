package com.bysj.sys.controller;


import com.bysj.common.model.R;
import com.bysj.sys.entity.*;
import com.bysj.sys.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  学生 控制器
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
@Controller
@RequestMapping("/sys/student")
public class StudentController {
    @Autowired
    private IAdminService iAdminServiceImpl;
    @Autowired
    private ICollegeService iCollegeServiceImpl;
    @Autowired
    private ITeacherService iTeacherServiceImpl;
    @Autowired
    private IStudentService iStudentServiceImpl;
    @Autowired
    private IUserService iUserServiceImpl;
    @Autowired
    private INoticeService iNoticeServiceImpl;
    //-------------------------学生自拟题目------------------------------
    /**
     * 跳转到学生自拟题目页面
     * @return
     */
    @GetMapping("/assignTopic")
    public String assignTopicList(Model model){
        model.addAttribute("balanceTime",iUserServiceImpl.getRemainingTime("出题"));
        return "sys/assignTopic_byStudent_List";
    }

    /**
     * 查询学生自拟题目信息
     * @return
     */
    @GetMapping("/assignTopicByStudentList")
    @ResponseBody
    public R assignTopicByStudentList(){
        List<MyTopic> topic = iStudentServiceImpl.getAssignTopic();
        return R.ok()
                .put("total",1)
                .put("rows", topic);
    }

    /**
     * 跳转到学生出题页面
     * @return
     */
    @GetMapping("/assignTopicByStudentAdd")
    public String assignTopic(Model model){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.addAttribute("stuUser",iAdminServiceImpl.getStudentById(user.getUserId()));
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("teaData",iTeacherServiceImpl.getTeacherNameAndIdByStudentId());
        return "sys/assignTopic_byStudent_Add";
    }

    /**
     * 学生出题
     * @return
     */
    @PostMapping("/assignTopicByStudentAdd")
    @ResponseBody
    public R assignTopicAdd(Topic topic){
        //判断出题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index = iStudentServiceImpl.assignTopicAdd(topic);

        if(index==-1){
            return R.error("您已有题目");
        }
        if(index==1){
            return R.ok("操作成功");
        }
        return R.error("操作失败");
    }

    /**
     * 跳转到学生查看题目详情页面
     * @return
     */
    @GetMapping("assignTopicByStudentShow/{id}")
    public String assignTopicByStudentShow(@PathVariable String id , Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("topicMessage",iTeacherServiceImpl.getAssignTopicByTno(id));
        model.addAttribute("teaData",iTeacherServiceImpl.getTeacherNameAndIdByStudentId());
        return "sys/assignTopic_byStudent_Show";
    }

    /**
     * 跳转到学生更新题目页面
     */
    @GetMapping("assignTopicByStudentUpdate/{id}")
    public String assignTopicUpdate(@PathVariable String id , Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("topicMessage",iTeacherServiceImpl.getAssignTopicByTno(id));
        model.addAttribute("teaData",iTeacherServiceImpl.getTeacherNameAndIdByStudentId());
        return "sys/assignTopic_byStudent_Update";
    }
    /**
     * 修改学生个人拟题信息
     */
    @PostMapping("/assignTopicByStudentUpdate")
    @ResponseBody
    public R assignTopicByStudentUpdate(Topic topic){
        //判断出题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index = iStudentServiceImpl.assignTopicByStudentUpdate(topic);
        if(index==-1){
         return R.error("导师已同意选题，不能更改题目信息");
        }
        if(index==-2){
            return R.error("题目已过审，不能更改题目信息");
        }
        if(index==1){
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }

    /**
     * 删除学生个人拟题信息
     * @return
     */
    @GetMapping("assignTopicByStudentDelete/{id}")
    @ResponseBody
    public R assignTopicByStudentDelete(@PathVariable String id){
        //判断出题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index = iStudentServiceImpl.assignTopicByStudentDelete(id);

        if(index==-1){
            return R.error("导师已同意选题，不能删除题目信息");
        }
        if(index==-2){
            return R.error("题目已过审，不能删除题目信息");
        }
        if(index==1){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }

    /**
     * 获取指导老师信息详情
     * @return
     */
    @GetMapping("/getAssignTopicTeacherData/{id}")
    @ResponseBody
    public R getAssignTopicTeacherData(@PathVariable String id){
     User_Teacher us = iStudentServiceImpl.getAssignTopicTeacherData(id);
     if(us!=null){
         return R.ok().put("teaData",us);
     }
        return R.error("获取失败");
    }
    //------------------------------学生个人主页-------------------------------

    @GetMapping("/studentMain")
    public String studentMain(Model model){
        //获取学生个人信息
        model.addAttribute("stuData", iStudentServiceImpl.getStudentData());
        //获取学生题导师，审核老师，课题信息
        model.addAttribute("stutopicData",iStudentServiceImpl.getStudentTopicData());
        System.out.println("haha"+iStudentServiceImpl.getStudentTopicData());
        return "sys/students_Main";
    }
    //---------------------------------学生选题--------------------------------------

    /**
     * 跳转到学生选题页面
     * @return
     */
    @GetMapping("/chooseTopic")
    public  String chooseTopicList(Model model){
        model.addAttribute("balanceTime",iUserServiceImpl.getRemainingTime("选题"));
        return "sys/chooseTopic_byStudent_List";
    }

    /**
     * 获取导师出题列表 供学生选题
     * @return
     */
    @GetMapping("/getChooseTopicList")
    @ResponseBody
    public R getChooseTopicList(String tMakeTeacher){
        String tmakeTeacher = null;
        if(!StringUtils.isEmpty(tMakeTeacher)){
            tmakeTeacher = ("%"+tMakeTeacher+"%");
        }
        List<MyTopic> myTopics = iStudentServiceImpl.getChooseTopicList(tmakeTeacher);
        return R.ok()
                .put("total",myTopics.size())
                .put("rows", myTopics);
    }

    /**
     * 学生选题操作
     * @return
     */
    @GetMapping("/studentChooseTopic/{id}")
    @ResponseBody
    public R chooseTopic(@PathVariable String id){
        //判断选题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(2);
        if(q.getqCron()==null){
            return R.error("选题时间已过，操作失败");
        }

        Integer index= iStudentServiceImpl.studentChooseTopic(id);
        if(index==-1){
            return R.error("您已选题,选题失败");
        }
        if(index==-2){
            return R.error("题目已被选，选题失败");
        }

        if(index==1||index==2){
            return R.ok("选题成功");
        }
        return R.error("操作失败");
    }
    //-----------------------通知管理-----------------------------
    /**
     * 跳转到接收通知列表页面
     * @return
     */
    @GetMapping("/receiveNotice")
    public String receiveNotice(){
        return "sys/receiveNotice_byTeaStuExamintea_List";
    }

    /**
     * 获取接收通知信息
     * @return
     */
    @GetMapping("/getReceiveNoticeList")
    @ResponseBody
    public R getReceiveNoticeList(){
        List<MyNotice> myNotices = iNoticeServiceImpl.getReceiveNoticeListByUserId();
        return R.ok()
                .put("total",myNotices.size())
                .put("rows", myNotices);
    }
}

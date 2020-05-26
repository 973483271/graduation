package com.bysj.sys.controller;


import com.bysj.common.exception.BizException;
import com.bysj.common.model.R;
import com.bysj.sys.entity.*;
import com.bysj.sys.service.*;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  指导老师 控制器
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
@Controller
@RequestMapping("/sys/teacher")
public class TeacherController {
    @Autowired
    private ITeacherService iTeacherServiceImpl;
    @Autowired
    private ICollegeService iCollegeServiceImpl;
    @Autowired
    private IUserService iUserServiceImpl;
    @Autowired
    private INoticeService iNoticeServiceImpl;

    //-------------------------导师出题-------------------------------
    /**
     * 跳转到 导师出题页面
     * @return
     */
    @GetMapping("/assignTopic")
    public String examin_teacherData(Model model){
        model.addAttribute("balanceTime",iUserServiceImpl.getRemainingTime("出题"));
        return "sys/assignTopic_byTeacher_List";
    }

    /**
     * 查询指导老师出题列表
     * @return
     */
    @GetMapping("/assignTopicList")
    @ResponseBody
    public R getAssignTopics(String tTitle){
       //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String ttile =null;
        if(!StringUtils.isEmpty(tTitle)){
            ttile = ("%"+tTitle+"%");
        }
        List<MyTopic> topics = iTeacherServiceImpl.getAssignTopics(user.getUserId(),ttile);
        return R.ok().
                 put("total",topics.size())
                .put("rows", topics);
    }

    /**
     * 跳转到出题页面
     * @return
     */
    @GetMapping("/assignTopicAdd")
    public String assignTopicAdd(Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        return "sys/assignTopic_byTeacher_add";
    }

    /**
     * 添加题目信息
     * @return
     */
    @PostMapping("/assignTopicAdd")
    @ResponseBody
    public R assignTopicAdd(Topic topic){
        //判断出题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index = iTeacherServiceImpl.assignTopicAdd(topic);

        if(index==1){
            return R.ok("操作成功");
        }
        return R.error("操作失败");
    }
    /**
     * 跳转到更新题目页面
     */
    @GetMapping("/assignTopicUpdate/{id}")
    public String assignTopicUpdate(@PathVariable String id , Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("topicMessage",iTeacherServiceImpl.getAssignTopicByTno(id));
        return "sys/assignTopic_byTeacher_Update";
    }

    /**
     * 修改课题信息
     * @param topic
     * @return
     */
    @PostMapping("/assignTopicUpdate")
    @ResponseBody
    public R assignTopicUpdate(Topic topic){
        //判断出题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index = iTeacherServiceImpl.assignTopicUpdate(topic);

        if(index==-1){
            return R.error("题目已过审，不能更改题目信息");
        }
        if(index==1){
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }

    /**
     * 删除题目信息（一条）
     * @param id
     * @return
     */
    @GetMapping("/assignTopicDelete/{id}")
    @ResponseBody
    public R assignTopicDelete(@PathVariable String id){
        //判断出题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index = iTeacherServiceImpl.assignTopicDelete(id);
        if(index==-1){
            return R.error("题目已审核通过，不能删除");
        }
        if(index==1){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }

    /**
     * 删除题目信息（批量）
     * @param ids
     * @return
     */
    @PostMapping("/assignTopicDeleteBatch")
    @ResponseBody
    public R assignTopicDeleteBatch(@RequestBody List<String> ids){
        Integer index =null;
        //判断出题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        try {
           index = iTeacherServiceImpl.assignTopicDeleteBatch(ids);
        }catch(BizException e){
            return R.error(e.getMessage());
        }

        if(index==ids.size()){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }

    /**
     * 跳转到查看题目详情页面
     * @return
     */
    @GetMapping("assignTopicShow/{id}")
    public String assignTopicShow(@PathVariable String id , Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("topicMessage",iTeacherServiceImpl.getAssignTopicByTno(id));
        return "sys/assignTopic_byTeacher_Show";
    }
    //------------------------------导师审核学生拟题-----------------------------------

    /**
     * 跳转到导师审题列表页面
     * @return
     */
    @GetMapping("/examinTopic")
    public String examinTopic(Model model){
        model.addAttribute("balanceTime",iUserServiceImpl.getRemainingTime("出题"));
        return "sys/examinTopic_byTeacher_List";
    }

    /**
     * 查询学生自拟题目列表
     * @return
     */
    @GetMapping("/examinTopicList")
    @ResponseBody
    public R examinTopicList(String tTitle){
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String ttile =null;
        if(!StringUtils.isEmpty(tTitle)){
            ttile = ("%"+tTitle+"%");
        }
        List<MyTopic> topics = iTeacherServiceImpl.getExaminTopicList(user.getUserId(),ttile);
        return R.ok().
                put("total",topics.size())
                .put("rows", topics);
    }

    /**
     * 跳转到查看学生自拟题目详情页面
     * @return
     */
    @GetMapping("examinTopicByTeacherShow/{id}")
    public String examinTopicByTeacherShow(@PathVariable String id , Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("topicMessage",iTeacherServiceImpl.getAssignTopicByTno(id));
        return "sys/examinTopic_byTeacher_Show";
    }

    /**
     * 根据学生id查询学生信息
     * @param id
     * @return
     */
    @GetMapping("/getAssignTopicByStudentData/{id}")
    @ResponseBody
    public R  getAssignTopicByStudentData(@PathVariable String id){
       User_Student us = iTeacherServiceImpl.getStudentDataById(id);
       if(us!=null){
           return R.ok().put("stuData",us);
       }
       return R.error("获取失败");
    }

    /**
     * 导师同意学生自拟题目
     * @return
     */
    @GetMapping("/assignTopicByStudentAgree/{id}")
    @ResponseBody
    public R assignTopicByStudentAgree(@PathVariable String id){
        //判断审题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index= iTeacherServiceImpl.agreeAssignTopicByStudent(id);

        if(index==1){
            return R.ok("操作成功");
        }
        return R.error("操作失败");
    }

    /**
     * 拒绝学生自拟题目
     * @return
     */
    @GetMapping("/assignTopicByStudentDisagree/{id}")
    @ResponseBody
    public R assignTopicByStudentDisagree(@PathVariable String id){
        //判断审题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(0);
        if(q.getqCron()==null){
            return R.error("出题时间已过，操作失败");
        }

        Integer index = iTeacherServiceImpl.disagreeAssignTopicByStudent(id);
        if(index==1){
            return R.ok("操作成功");
        }
        return  R.error("操作失败");
    }
    //----------------------------------导师个人主页------------------------------------------

    /**
     * 跳转到学生选题页面
     * @return
     */
    @GetMapping("/teacherMain")
    public String studentChooseTopic(Model model){
        model.addAttribute("teacherData",iTeacherServiceImpl.getTeacherData());//显示指导老师个人信息
        return "sys/teachers_Main";
    }

    /**
     * 获取学生选题信息（对应导师）
     * @return
     */
    @GetMapping("/getStudentChooseTopic")
    @ResponseBody
    public R getStudentChooseTopic(){
        List<MyTopic> myTopics = iTeacherServiceImpl.getStudentChooseTopic();
        return R.ok().
                put("total",myTopics.size())
                .put("rows", myTopics);
    }

    /**
     * 获取审核导师信息详情
     * @return
     */
    @GetMapping("/getExaminTopicByExaminTeacherData/{id}")
    @ResponseBody
    public R getExaminTopicByExaminTeacherData(@PathVariable String id){
        User_Teacher ut = iTeacherServiceImpl.getExaminTeacherData(id);
        if(ut!=null){
            return R.ok().put("exaTeaData",ut);
        }
        return R.error("操作失败");
    }

    /**
     * 撤回学生选题
     * @return
     */
    @GetMapping("/deleteStudentChooseTopic/{id}")
    @ResponseBody
    public R deleteStudentChooseTopic(@PathVariable String id){
        Integer index=iTeacherServiceImpl.deleteStudentChooseTopic(id);
        System.out.println("hahaha"+index);
        if(index==1){
            return R.ok("撤回成功");
        }
        return R.error("操作失败");
    }

    //-------------------------导师审核学生选题--------------------------

    /**
     * 跳转到审核学生选题页面
     * @return
     */
    @GetMapping("/examinStudentChooseTopic")
    public String examinStudentChooseTopic(Model model){
        model.addAttribute("balanceTime",iUserServiceImpl.getRemainingTime("选题"));
        return "sys/examinStudentChooseTopic_byTeacher_List";
    }
    /**
     *获取学生选题列表 待指导老师审核
     */
    @GetMapping("/getExaminStudentChooseTopicList")
    @ResponseBody
    public R getExaminStudentChooseTopicList(){
        List<MyTopic> myTopics = iTeacherServiceImpl.getExaminStudentChooseTopicList();
        return R.ok().
                put("total",myTopics.size())
                .put("rows", myTopics);
    }

    /**
     * 通过学生选题
     * @param id
     * @return
     */
    @GetMapping("/chooseTopicByStudentAgree/{id}")
    @ResponseBody
    public R chooseTopicByStudentAgree(@PathVariable String id){
        //判断选题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(2);
        if(q.getqCron()==null){
            return R.error("选题时间已过，操作失败");
        }

        Integer index = iTeacherServiceImpl.chooseTopicByStudentAgree(id);
        if(index==1){
            return R.ok("操作成功");
        }
       return R.error("操作失败");
    }

    /**
     * 拒绝学生选题
     * @param id
     * @return
     */
    @GetMapping("/chooseTopicByStudentDisagree/{id}")
    @ResponseBody
    public R chooseTopicByStudentDisagree(@PathVariable String id){
        //判断选题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(2);
        if(q.getqCron()==null){
            return R.error("选题时间已过，操作失败");
        }

        Integer index = iTeacherServiceImpl.chooseTopicByStudentDisagree(id);
        if(index==1){
            return R.ok("操作成功");
        }
        return R.error("操作失败");
    }
    //-----------------------------通知管理-------------------------------

    /**
     * 跳转到发布通知列表页面
     * @return
     */
    @GetMapping("/deliverNotice")
    public String deleiverNotice(){
        return "sys/deliverNotice_byTeacher_List";
    }

    /**
     * 获取发布通知信息
     * @return
     */
    @GetMapping("/getNoticeList")
    @ResponseBody
    public R getDeliverNoticeList(){
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<MyNotice> myNotices = iNoticeServiceImpl.getDeliverNoticeListByUserId(user.getUserId());
        return R.ok()
                .put("total",myNotices.size())
                .put("rows", myNotices);
    }

    /**
     * 跳转到发布通知页面
     * @return
     */
    @GetMapping("/deliverNoticeAdd")
    public String deliverNoticeAdd(Model model){
        model.addAttribute("collData",iTeacherServiceImpl.getCollegeByTeacherId()) ;
        return "sys/deliverNotice_byTeacher_add";
    }

    /**
     * 发布通知
     * @param notice
     * @param files
     * @return
     */
    @PostMapping("/deliverNotice")
    @ResponseBody
    public  R deliverNotice(Notice notice,@RequestParam("file") MultipartFile[] files){
        Integer index = iNoticeServiceImpl.deliverNotice(notice, files);
        if(index==1){
            return R.ok("发布成功");
        }else if(index==-1){
            return R.error("文件上传异常，发布失败");
        }
        return R.error("发布失败");
    }

    /**
     * 查看指导老师接受通知详情
     * @return
     */
    @GetMapping("/getNoticeOneData/{id}")
    @ResponseBody
    public R getNoticeOneData(@PathVariable Integer id){
        MyNotice myNotice = iNoticeServiceImpl.getNoticeOneDataById(id);
        return  R.ok().put("notice",myNotice);
    }

    /**
     * 根据通知id 下载文件
     * @param id
     * @param response
     */
    @GetMapping("/downloadFile/{id}")
    public void downloadFile(@PathVariable String id,HttpServletResponse response){
        iNoticeServiceImpl.downloadFile(id,response);
    }

    /**
     * 删除通知
     * @param id
     * @return
     */
    @GetMapping("/noticeDelete/{id}")
    @ResponseBody
    public R noticeDelete(@PathVariable Integer id){
        Integer index =  iNoticeServiceImpl.noticeDelete(id);
        if(index==1){
            return    R.ok("操作成功");
        }
        return  R.error("操作失败");
    }

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

    /**
     * 查看指导老师发布通知详情
     * @param id
     * @return
     */
    @GetMapping("/getDeliverNoticeOneData/{id}")
    @ResponseBody
    public R getDeliverNoticeOneData(@PathVariable Integer id){
        MyNotice myNotice = iNoticeServiceImpl.getDeliverNoticeOneDataById(id);
        return  R.ok().put("notice",myNotice);
    }
}

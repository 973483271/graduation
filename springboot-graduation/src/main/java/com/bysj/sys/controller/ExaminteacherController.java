package com.bysj.sys.controller;


import com.bysj.common.model.R;
import com.bysj.sys.entity.*;
import com.bysj.sys.service.IExaminteacherService;
import com.bysj.sys.service.INoticeService;
import com.bysj.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  审核组老师 控制器
 * </p>
 *
 * @author jack
 * @since 2020-02-06
 */
@Controller
@RequestMapping("/sys/examinteacher")
public class ExaminteacherController {
    @Autowired
    private IExaminteacherService examinteacherServiceImpl;
    @Autowired
    private IUserService iUserServiceImpl;
    @Autowired
    private INoticeService iNoticeServiceImpl;
    //------------------------------审题老师审题-------------------------
    /**
     * 跳转到审题页面
     * @return
     */
    @GetMapping("/examin")
    public String examin(Model model){
        model.addAttribute("balanceTime",iUserServiceImpl.getRemainingTime("审题"));
        return "sys/examinTopic_byExaminTeacher_List";
    }

    /**
     * 获取审核老师审题列表
     * @return
     */
    @GetMapping("/examinTopicList")
    @ResponseBody
    public R examinTopicList(){

         List<MyTopic> topics =  examinteacherServiceImpl.getExaminTopicsList();
         return R.ok()
                 .put("total",topics.size())
                 .put("rows", topics);
        }

    /**
     * 审题通过
     * @param id
     * @return
     */
    @GetMapping("/examinTopicPass/{id}")
    @ResponseBody
    public  R examinTopicPass(@PathVariable String id){
        //判断审题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(1);
        if(q.getqCron()==null){
            return R.error("审题时间已过，操作失败");
        }

        Integer index= examinteacherServiceImpl.examinTopicPass(id);
        if(index==1){
          return  R.ok("操作成功");
        }
        return R.error("操作失败");
    }

    /**
     * 审题不通过
     * @param id
     * @return
     */
    @GetMapping("/examinTopicNotPass/{id}")
    @ResponseBody
    public R examinTopicNotPass(@PathVariable String id){
        //判断审题时间是否已过
        Quartz q  = iUserServiceImpl.getProcessControlData().get(1);
        if(q.getqCron()==null){
            return R.error("审题时间已过，操作失败");
        }

        Integer index= examinteacherServiceImpl.examinTopicNotPass(id);
        if(index==1){
            return  R.ok("操作成功");
        }
        return R.error("操作失败");
    }
    /**
     * 跳转到审题情况页面
     * @return
     */
    @GetMapping("/examinSituation")
    public String ExaminSituation(){
        return "sys/examinSituation_byExaminTeacher_List";
    }

    /**
     *获取所有已审核题目信息
     * @return
     */
    @GetMapping("/getExaminSituation")
    @ResponseBody
    public R getExaminSituation(String inputData){
        List<MyTopic> myTopics = examinteacherServiceImpl.getExaminSituation(inputData);
        return R.ok()
                .put("total",myTopics.size())
                .put("rows", myTopics);
    }

    //-----------------------------审题教师个人主页----------------------------------

    /**
     * 跳转到审题老师主页
     * @return
     */
    @GetMapping("/examinteacherMain")
    public  String examinteacherMain(Model model){
        //查询审题老师个人信息 包括 审核题目数量 和 通过率
        User_Examinteacher ue = examinteacherServiceImpl.getExaminTeacherData();
        model.addAttribute("examinTea",ue);
        return "sys/examinteachers_Main";
    }
    //----------------------通知管理----------------------------
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

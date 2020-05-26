package com.bysj.sys.controller;


import com.bysj.common.model.R;
import com.bysj.sys.entity.*;
import com.bysj.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>管理员 控制器
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
@Controller
@RequestMapping("/sys/admin")
public class AdminController {

    @Autowired
    private IAdminService iAdminServiceImpl;
    @Autowired
    private ICollegeService iCollegeServiceImpl;
    @Autowired
    private IClassService iClassServiceImpl;
    @Autowired
    private IExaminteacherService iExaminteacherServiceImpl;
    @Autowired
    private INoticeService iNoticeServiceImpl;
    //    ------------------------------------审题小组成员信息----------------------------------------------------

    /**
     * 跳转到 审题小组成员 信息列表页面
     * @return
     */
    @GetMapping("/examin_teacher")
    public String examin_teacherData(Model model){
        model.addAttribute("teaCollege",iCollegeServiceImpl.getCollege());
        return "sys/examin_teachers_list";
    }

    /**
     * 查找所有 审题小组成员 信息列表并返回
     * @return
     */
    @GetMapping("/examin_teachersList")
    @ResponseBody
    public R examin_teachersList(String examinteaId,String examinteaName,Integer status,String examinteaEducation,String teaCollege){
        String examinteaid = null;
        if(!StringUtils.isEmpty(examinteaId)){
            examinteaid = ("%"+examinteaId+"%");
        }
        String examinteaname = null;
        if(!StringUtils.isEmpty(examinteaName)){
            examinteaname = ("%"+examinteaName+"%");
        }
        Integer statu = null;
        if(!StringUtils.isEmpty(status)){
            statu = status;
        }
        String examinteaeducation = null;
        if(!StringUtils.isEmpty(examinteaEducation)){
            examinteaeducation = examinteaEducation;
        }
        String teacollege = null;
        if(!StringUtils.isEmpty(teaCollege)){
            teacollege = teaCollege;
        }
        List<User_Examinteacher> teachers = iAdminServiceImpl.getexamin_teachersList(examinteaid,examinteaname,statu,examinteaeducation,teacollege);

        return R.ok()
                .put("total",teachers.size())
                .put("rows", teachers);
    }

    /**
     * 跳转到新增审题小组成员页面
     * @return
     */
    @GetMapping("/addExamin_teacher")
    public String addAdmin(Model model){
        model.addAttribute(model.addAttribute("teaCollege",iCollegeServiceImpl.getCollege()));
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("stuClass", iClassServiceImpl.getStudentClass());
        return "sys/examin_teachers_add";
    }

    /**
     * 添加审题小组成员信息
     * @return
     */
    @PostMapping("/addExaminteacher")
    @ResponseBody
    public R addExaminteacher(String examinteaId,String password,String examinteaName,Integer status,String examinteaEducation,Integer teaCollId,Integer examinteaResEducation) {
        Integer index = iAdminServiceImpl.addExaminteacherData(examinteaId, password, examinteaName, status,examinteaEducation,teaCollId,examinteaResEducation);
        if(index==-1){
            return R.error("用户名已存在");
        }
        if(index == 4) {
            return R.ok("添加成功");
        }
        return R.error("添加失败");
    }

    /**
     * 跳转到修改审题组成员信息 页面  根据选择修改数据的id查出 审题老师 信息 传递过去
     * @return
     */
    @GetMapping("/examin_teachersUpdate/{id}")
    public String examin_teachersUpdate(@PathVariable String id,Model model){
        model.addAttribute("teaUser",iAdminServiceImpl.getExaminteacherById(id));
        model.addAttribute("teaCollege",iCollegeServiceImpl.getCollege());
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("teaEdu",iExaminteacherServiceImpl.getExamincationIdByExaminId(id));
        return "sys/examin_teachers_update";
    }

    /**
     * 修改审题员信息
     * @return
     */
    @PostMapping("/examin_teachersUpdate")
    @ResponseBody
    public R examin_teachersUpdate(User_Examinteacher ue){
        Integer index = iAdminServiceImpl.examin_teachersUpdate(ue);
        if(index==4){
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }

    /**
     * 删除审题员信息（一条数据）
     * @param id
     * @return
     */
    @GetMapping("/examin_teachersDelete/{id}")
    @ResponseBody
    public R examin_teachersDelete(@PathVariable String id){
       Integer index =  iAdminServiceImpl.examin_teachersDelete(id);
       if(index==4){
           return R.ok("删除成功");
       }
       return R.error("删除失败");
    }

    /**
     * 删除管理员信息（批量）
     * @param ids
     * @return
     */
    @PostMapping("/examin_teachersDeleteBatch")
    @ResponseBody
    public R examin_teachersDeleteBatch(@RequestBody List<String> ids){
        Integer index =  iAdminServiceImpl.examin_teachersDeleteBatch(ids);
        if(index==ids.size()*4){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }
    /**
     * 批量导入审题老师信息数据
     * @param file
     * @return
     */
    @PostMapping("/examin_teachersBatchAdd")
    @ResponseBody
    public R examin_teachersBatchAdd(@RequestParam("file") MultipartFile file){
        try {
            iAdminServiceImpl.importExaminTeacherData(file);
        } catch (Exception e) {
            return R.error("导入失败，请检查字段是否为空或用户名重复或学院(部)、负责专业名称错误");
        }
        return R.ok("导入成功");
    }
//    -------------------------------------------学生信息---------------------------------------------------
    /**
     * 跳转到学生信息列表页面
     * @return
     */
    @GetMapping("/student")
    public String studentData(Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        return "sys/students_list";
    }
    /**
     * 查找所有学生信息列表并返回
     * @return
     */
    @GetMapping("/studentsList")
    @ResponseBody
    public R studentsList(String stuId,String stuName,Integer status,Integer stuClass,String stuCollege,String stuEducation){
        String stuid = null;
        if(!StringUtils.isEmpty(stuId)){
            stuid = ("%"+stuId+"%");
        }
        String stuname = null;
        if(!StringUtils.isEmpty(stuName)){
            stuname = ("%"+stuName+"%");
        }
        Integer statu = null;
        if(!StringUtils.isEmpty(status)){
            statu = status;
        }
        Integer stuclass = null;
        if(!StringUtils.isEmpty(stuClass)){
            stuclass = stuClass;
        }
        String stucollege = null;
        if(!StringUtils.isEmpty(stuCollege)){
            stucollege = stuCollege;
        }
        String stueducation = null;
        if(!StringUtils.isEmpty(stuEducation)){
            stueducation = stuEducation;
        }
        List<User_Student> students =
                iAdminServiceImpl.getStudentsList(stuid,stuname,statu,stuclass,stucollege,stueducation);
        return R.ok()
                .put("total",students.size())
                .put("rows", students);
    }
    /**
     * 跳转到新增学生页面
     * @return
     */
    @GetMapping("/addStudent")
    public String addStudent(Model model){
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("stuClass", iClassServiceImpl.getStudentClass());
        return "sys/students_add";
    }
    /**
     * 添加学生信息
     * @return
     */
    @PostMapping("/addStudent")
    @ResponseBody
    public R addStudent(String userId,String password,String stuName,Integer stuClass,Integer stuEducation,Integer status) {

        Integer index = iAdminServiceImpl.addStudentData(userId, password, stuName, stuClass,stuEducation,status);
        if(index==-1){
            return R.error("用户名已存在");
        }
        if(index == 3) {
            return R.ok("添加成功");
        }
        return R.error("添加失败");
    }
    /**
     * 跳转到修改学生数据页面  根据选择修改数据的id查出学生信息 传递过去
     * @return
     */
    @GetMapping("/studentsUpdate/{id}")
    public String studentsUpdate(@PathVariable String id,Model model){
        model.addAttribute("stuUser",iAdminServiceImpl.getStudentById(id));
        model.addAttribute("eduColl",iCollegeServiceImpl.getCollegeAndEducation());
        model.addAttribute("stuClass", iClassServiceImpl.getStudentClass());
        return "sys/students_update";
    }
    /**
     * 修改学生信息
     * @return
     */
    @PostMapping("/studentsUpdate")
    @ResponseBody
    public R studentsUpdate(User_Student us){
        Integer index = iAdminServiceImpl.studentsUpdate(us);
        if(index==3){
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }
    /**
     * 删除学生信息（一条数据）
     * @param id
     * @return
     */
    @GetMapping("/studentsDelete/{id}")
    @ResponseBody
    public R studentsDelete(@PathVariable String id){
        Integer index =  iAdminServiceImpl.studentsDelete(id);
        if(index==3){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }
    /**
     * 删除学生信息（批量）
     * @param ids
     * @return
     */
    @PostMapping("/studentsDeleteBatch")
    @ResponseBody
    public R studentsDeleteBatch(@RequestBody List<String> ids){
        Integer index =  iAdminServiceImpl.studentsDeleteBatch(ids);
        if(index==ids.size()*3){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }

    /**
     * 批量导入学生信息数据
     * @param file
     * @return
     */
    @PostMapping("/studentBatchAdd")
    @ResponseBody
    public R studentBatchAdd(@RequestParam("file") MultipartFile file){
        try {
             iAdminServiceImpl.importStudentData(file);
        } catch (Exception e) {
            return R.error("导入失败，请检查字段是否为空或用户名重复或学院(部)、专业、班级名称错误");
        }
        return R.ok("导入成功");
    }
//--------------------------------------------老师信息----------------------------------------
    /**
     * 跳转到老师信息列表页面
     * @return
     */
    @GetMapping("/teacher")
    public String teacherData(Model model){
        model.addAttribute("teaCollege",iCollegeServiceImpl.getCollege());
        return "sys/teachers_list";
    }
    /**
     * 查找所有老师信息列表并返回
     * @return
     */
    @GetMapping("/teachersList")
    @ResponseBody
    public R teachersList(String teaId,String teaName,Integer status,String teaEducation,String teaCollege){
        String teaid = null;
        if(!StringUtils.isEmpty(teaId)){
            teaid = ("%"+teaId+"%");
        }
        String teaname = null;
        if(!StringUtils.isEmpty(teaName)){
            teaname = ("%"+teaName+"%");
        }
        Integer statu = null;
        if(!StringUtils.isEmpty(status)){
            statu = status;
        }
        String teaeducation = null;
        if(!StringUtils.isEmpty(teaEducation)){
            teaeducation = teaEducation;
        }
        String teacollege = null;
        if(!StringUtils.isEmpty(teaCollege)){
            teacollege = teaCollege;
        }

        List<User_Teacher> teachers = iAdminServiceImpl.getTeachersList(teaid,teaname,statu,teaeducation,teacollege);

        return R.ok()
                .put("total",teachers.size())
                .put("rows", teachers);
    }
    /**
     * 跳转到新增教师页面
     * @return
     */
    @GetMapping("/addTeacher")
    public String addTeacher(Model model){
        model.addAttribute(model.addAttribute("teaCollege",iCollegeServiceImpl.getCollege()));
        return "sys/teachers_add";
    }
    /**
     * 添加教师信息
     * @return
     */
    @PostMapping("/addTeacher")
    @ResponseBody
    public R addTeacher(String teaId,String password,String teaName,Integer status,String teaEducation,Integer teaCollId) {
        Integer index = iAdminServiceImpl.addTeacherData(teaId, password, teaName, status,teaEducation,teaCollId);
        if(index==-1){
            return R.error("用户名已存在");
        }
        if(index == 3) {
            return R.ok("添加成功");
        }
        return R.error("添加失败");
    }
    /**
     * 跳转到修改老师数据页面  根据选择修改数据的id查出老师信息 传递过去
     * @return
     */
    @GetMapping("/teachersUpdate/{id}")
    public String teachersUpdate(@PathVariable String id,Model model){
        model.addAttribute("teaUser",iAdminServiceImpl.getTeacherById(id));
        model.addAttribute("teaCollege",iCollegeServiceImpl.getCollege());
        return "sys/teachers_update";
    }
    /**
     * 修改教师信息
     * @return
     */
    @PostMapping("/teachersUpdate")
    @ResponseBody
    public R teachersUpdate(User_Teacher ut){
        Integer index = iAdminServiceImpl.teachersUpdate(ut);
        if(index==3){
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }
    /**
     * 删除教师信息（一条数据）
     * @param id
     * @return
     */
    @GetMapping("/teachersDelete/{id}")
    @ResponseBody
    public R teachersDelete(@PathVariable String id){
        Integer index =  iAdminServiceImpl.teachersDelete(id);
        if(index==3){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }
    /**
     * 删除老师信息（批量）
     * @param ids
     * @return
     */
    @PostMapping("/teachersDeleteBatch")
    @ResponseBody
    public R teachersDeleteBatch(@RequestBody List<String> ids){
        Integer index =  iAdminServiceImpl.teachersDeleteBatch(ids);
        if(index==ids.size()*3){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }
    /**
     * 批量导入指导老师信息数据
     * @param file
     * @return
     */
    @PostMapping("/teacherBatchAdd")
    @ResponseBody
    public R teacherBatchAdd(@RequestParam("file") MultipartFile file) {
        try {
            iAdminServiceImpl.importTeacherData(file);
        } catch (Exception e) {
            return R.error("导入失败，请检查字段是否为空或用户名重复或学院(部)名称错误");
        }
        return R.ok("导入成功");
    }
//-----------------------出题统计-------------------------------

    /**
     * 跳转到管理员统计出题情况页面
     * @return
     */
    @GetMapping("/assignTopicStatistics")
    public String assignTopicStatistics(){
        return "sys/assignTopicStatistics_byAdmin_List";
    }

    /**
     * 获取各个专业出题情况
     * @return
     */
    @GetMapping("/assignTopicSituation")
    @ResponseBody
    public R assignTopicSituation(String tTitle){
        String ttitle = null;
        if(!StringUtils.isEmpty(tTitle)){
            ttitle = ("%"+tTitle+"%");
        }
        List<AssignTopicSituation> ats = iAdminServiceImpl.getAssignTopicSituation(ttitle);
        return R.ok()
                .put("total",ats.size())
                .put("rows", ats);
    }

    /**
     * 查看指定专业的出题详情用于统计图显示
     * @param id
     * @return
     */
    @GetMapping("/getAssignTopicDetails/{id}")
    @ResponseBody
    public R getAssignTopicDetails(@PathVariable Integer id){
       AssignTopicSituation ats =  iAdminServiceImpl.getAssignTopicDetails(id);
       return R.ok().put("data",ats);
    }

    /**
     * 查看每个专业指导老师出题详情
     * @return
     */
    @GetMapping("/getAppointEducationAssignTopicDetails/{id}")
    @ResponseBody
    public R getAppointEducationAssignTopicDetails(@PathVariable Integer id){
        List<AssignTopicSituation> ats = iAdminServiceImpl.getAppointEducationAssignTopicDetails(id);
        return R.ok()
                .put("total",ats.size())
                .put("rows", ats);
    }
    //--------------------------选题统计-------------------------------

    /**
     * 跳转到选题统计页面
     * @return
     */
    @GetMapping("/chooseTopicStatistics")
    public String chooseTopicStatistics(Model model){
        //设置学生选题数据 用于主页显示
        model.addAttribute("chooseTopicNum",iAdminServiceImpl.getChooseTopicStudentNum());
        return "sys/chooseTopicStatistics_byAdmin_List";
    }

    /**
     * 获取全部已选题学生列表
     * @return
     */
    @GetMapping("/hasChooseTopicStudentSituation")
    @ResponseBody
    public R hasChooseTopicStudentSituation(String inputData){
        String inputdata = null;
        if(!StringUtils.isEmpty(inputData)){
            inputdata = ("%"+inputData+"%");
        }
        List<User_Student> us = iAdminServiceImpl.getHasAlreadyChooseTopicStudentData(inputdata);
        return R.ok()
                .put("total",us.size())
                .put("rows", us);

    }

    /**
     * 获取全部未选题学生列表
     * @param iData
     * @return
     */
    @GetMapping("/notChooseTopicStudentSituation")
    @ResponseBody
    public  R notChooseTopicStudentSituation(String iData){
        String idata = null;
        if(!StringUtils.isEmpty(iData)){
            idata = ("%"+iData+"%");
        }
        List<User_Student> us = iAdminServiceImpl.getNotChooseTopicStudentSituation(idata);
        return R.ok()
                .put("total",us.size())
                .put("rows", us);
    }
//---------------------------------审题统计-------------------------------
    /**
     * 跳转到审题统计页面
     * @return
     */
    @GetMapping("/examinTopicStatistics")
    public String examinTopicStatistics(){
        return "sys/examinTopicStatistics_byAdmin_List";
    }

    /**
     * 获取各个专业审题情况
     * @param tTitle
     * @return
     */
    @GetMapping("/examinTopicSituation")
    @ResponseBody
    public R getExaminTopicSituationList(String tTitle){
        String ttitle = null;
        if(!StringUtils.isEmpty(tTitle)){
            ttitle = ("%"+tTitle+"%");
        }
        List<AssignTopicSituation> ats = iAdminServiceImpl.getExaminTopicSituationList(ttitle);
        return R.ok()
                .put("total",ats.size())
                .put("rows", ats);
    }

    /**
     * 获取指定专业审题老师审核题目信息详情
     * @param id
     * @return
     */
    @GetMapping("/getAppointEducationExaminTopicDetails/{id}")
    @ResponseBody
    public R getAppointEducationExaminTopicDetails(@PathVariable Integer id){
        List<AssignTopicSituation> ats = iAdminServiceImpl.getAppointEducationExaminTopicDetails(id);
        return R.ok()
                .put("total",ats.size())
                .put("rows", ats);
    }
    /**
     * 查看指定专业的审题详情用于统计图显示
     * @param id
     * @return
     */
    @GetMapping("/getExaminTopicDetails/{id}")
    @ResponseBody
    public R getExaminTopicDetails(@PathVariable Integer id){
        AssignTopicSituation ats =  iAdminServiceImpl.getExaminTopicDetails(id);
        return R.ok().put("data",ats);
    }
//------------------------------------通知管理---------------------------------
    /**
     * 跳转到通知列表页面
     * @return
     */
    @GetMapping("/notice")
    public String deliverNoticeList(){
        return "sys/deliverNotice_byAdmin_List";
    }

    /**
     * 跳转到发布通知页面
     * @return
     */
    @GetMapping("/deliverNoticeAdd")
    public String deliverNoticeAdd(Model model){
        model.addAttribute("college",iCollegeServiceImpl.getCollege());
        return "sys/deliverNotice_byAdmin_add";
    }

    /**
     * 获取本人出的  公告列表
     * @return
     */
    @GetMapping("/getNoticeList")
    @ResponseBody
    public R getNoticeList(){
        List<MyNotice> notices = iNoticeServiceImpl.getDeliverNoticeListByUserId("admin");
        return R.ok()
                .put("total",notices.size())
                .put("rows", notices);
    }

    /**
     * 发布通知
     * @param
     * @return
     */
    @PostMapping("/deliverNotice")
    @ResponseBody
    public R deliverNotice(Notice notice,@RequestParam("file") MultipartFile[] files){
        Integer index = iNoticeServiceImpl.deliverNotice(notice, files);
        if(index==1){
            return R.ok("发布成功");
        }else if(index==-1){
            return R.error("文件上传异常，发布失败");
        }
        return R.error("发布失败");
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
        return  R.ok("操作成功");
       }
       return  R.error("操作失败");
    }

    /**
     * 获取通知详情
     * @param id
     * @return
     */
    @GetMapping("/getNoticeOneData/{id}")
    @ResponseBody
    public R getNoticeOneData(@PathVariable Integer id){
        MyNotice myNotice = iNoticeServiceImpl.getDeliverNoticeOneDataById(id);
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

}

package com.bysj.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bysj.sys.entity.*;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
public interface IStudentService extends IService<Student> {
    //------------------------------学生自拟题目---------------------------
    /**
     * 学生出题
     * @param topic
     * @return
     */
    Integer assignTopicAdd(Topic topic);

    /**
     * 查询学生个人拟题信息
     * @return
     */
    List<MyTopic> getAssignTopic();

    /**
     *删除学生个人拟题信息
     */
    Integer assignTopicByStudentDelete(String id);

    /**
     * 修改学生自拟题目信息
     * @param topic
     * @return
     */
    Integer assignTopicByStudentUpdate(Topic topic);

    /**
     * 获取指导老师信息用于学生查看
     * @param id
     * @return
     */
    User_Teacher getAssignTopicTeacherData(String id);
    //------------------------------学生个人主页------------------------------

    /**
     * 获取学生个人信息用于主页显示
     * @return
     */
    User_Student getStudentData();

    /**
     * 根据学生学号获取题目相关信息（导师，审核老师，题目ID，名称）
     * @return
     */
    MyTopic getStudentTopicData();

    //----------------------------学生选题-------------------------------------

    /**
     * 获取所有老师出题列表--供学生选题
     * @return
     */
    List<MyTopic> getChooseTopicList(String tmakeTeacher);

    /**
     * 学生选题操作
     * @param id
     * @return
     */
    Integer studentChooseTopic(String id);
}

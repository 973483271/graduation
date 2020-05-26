package com.bysj.sys.service;

import com.bysj.common.exception.BizException;
import com.bysj.sys.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  指导老师 服务类
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
public interface ITeacherService extends IService<Teacher> {

    //---------------------指导老师出题---------------------
    /**
     * 获取指导老师出题列表
     * @return
     */
    List<MyTopic> getAssignTopics(String teaId,String tTitle);

    /**
     * 老师出题
     * @param topic
     * @return
     */
    Integer assignTopicAdd(Topic topic);

    /**
     * 根据题目编号查询题目详细信息
     * @return
     */
    Topic getAssignTopicByTno(String tno);

    /**
     * 修改题目信息
     * @param topic
     * @return
     */
    Integer assignTopicUpdate(Topic topic);

    /**
     * 删除题目信息（一条）
     * @param tno
     * @return
     */
    Integer assignTopicDelete(String tno);

    /**
     * 删除题目信息（批量）
     * @param ids
     * @return
     */
    Integer assignTopicDeleteBatch(List<String> ids) throws BizException;

    /**
     * 根据学生 id 查出本学院（部）的所有导师工号和姓名
     */
    List<Teacher> getTeacherNameAndIdByStudentId();

    //----------------指导老师审题----------------------

    /**
     * 查询学生自拟题目信息用于导师审核
     * @return
     */
    List<MyTopic> getExaminTopicList(String teaId,String tTitle);

    /**
     * 根据学生学号查询学生信息用于指导老师查看
     * @return
     */
    User_Student getStudentDataById(String id);

    /**
     * 导师同意学生自拟题目
     */
    Integer agreeAssignTopicByStudent(String id);

    /**
     * 导师拒绝学生自拟题目
     * @param id
     * @return
     */
    Integer disagreeAssignTopicByStudent(String id);
    //----------------------------导师个人主页---------------------------------

    /**
     * 获取学生选题信息
     * @return
     */
    List<MyTopic> getStudentChooseTopic();

    /**
     * 根据审题教师工号查询审题教师信息用于指导老师查看
     * @param id
     * @return
     */
    User_Teacher getExaminTeacherData(String id);

    /**
     * 查询指导老师个人信息 用于主页显示
     * @return
     */
    User_Teacher getTeacherData();
    /**
     * 获取学生选题信息
     * @return
     */
    Integer deleteStudentChooseTopic(String id);
    //-----------------------------导师审核学生选题------------------

    /**
     * 获取学生选题列表，用于指导老师审核
     * @return
     */
    List<MyTopic> getExaminStudentChooseTopicList();

    /**
     * 同意学生选题
     * @return
     */
    Integer chooseTopicByStudentAgree(String id);
    /**
     * 拒绝学生选题
     * @return
     */
    Integer chooseTopicByStudentDisagree(String id);
    //--------------------------发布通知---------------------
    /**
     * 根据老师id查询老师所在学院信息
     */
    College getCollegeByTeacherId();
}

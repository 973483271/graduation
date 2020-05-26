package com.bysj.sys.mapper;

import com.bysj.sys.entity.AssignTopicSituation;
import com.bysj.sys.entity.MyTopic;
import com.bysj.sys.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bysj.sys.entity.User_Student;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2020-02-10
 */
public interface TopicMapper extends BaseMapper<Topic> {
    /**
     * 获取指导老师出题列表
     * @return
     */
    List<MyTopic> getAssignTopics(@Param("teaId") String teaId, @Param("tTitle") String tTitles);

    /**
     * 获取题目的最大编号
     * @return
     */
    String getMaxTno();
    /**
     * 根据题目编号获取题目信息
     */
    List<MyTopic> getAssignTopicByTopicNo(@Param("tNo") String tNo);

    /**
     * 获取学生自拟题目信息用于审核
     * @return
     */
    List<MyTopic> getExaminTopicList(@Param("teaId") String teaId, @Param("tTitle") String tTitle);

    /**
     * 获取出指定专业题目的所有导师id，包括出题总数
     */
    List<AssignTopicSituation> getMaketeacherAppointEducation(@Param("eduId") Integer eduId);

    /**
     * 获取所有符合条件的课题 ----供学生选题
     * @return
     */
    List<MyTopic> getTopicListForStudentsToChoose(@Param("eduId") Integer eduId,@Param("tmakeTeacher") String tmakeTeacher);

    /**
     *导师拒绝学生选题
     */
    Integer chooseTopicByStudentDisagree(@Param("id") String id);

    /**
     * 获取指定专业所有审题教师id,包括审题总数
     */
    List<AssignTopicSituation> getExaminteacherAppointEducation(@Param("eduId") Integer eduId);
}

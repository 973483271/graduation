package com.bysj.sys.service;

import com.bysj.sys.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jack
 * @since 2020-02-06
 */
public interface IExaminteacherService extends IService<Examinteacher> {
    //-----------------审题页面----------

    /**
     * 根据审核老师id获取 负责审核的专业id
     * @param id
     * @return
     */
    Integer getExamincationIdByExaminId(String id);

    /**
     * 查询审查组导师审核题目列表
     * @return
     */
    List<MyTopic> getExaminTopicsList();

    /**
     * 审题通过
     * @param id
     * @return
     */
    Integer examinTopicPass(String id);

    /**
     * 审题不通过
     * @param id
     * @return
     */
    Integer examinTopicNotPass(String id);

    /**
     * 获取所有已审核题目信息
     * @return
     */
    List<MyTopic> getExaminSituation(String inputData);

    //--------------------------------审题教师主页-----------------------------------

    /**
     * 获取审题教师数据用于主页显示
     * @return
     */
    User_Examinteacher getExaminTeacherData();
}

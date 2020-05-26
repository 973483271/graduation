package com.bysj.sys.mapper;

import com.bysj.sys.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2020-02-06
 */
public interface ExaminteacherMapper extends BaseMapper<Examinteacher> {
    /**
     * 根据审核老师id获取负责专业名
     * @return
     */
    String getEducationByExaminTeaId(@Param("id") String id);

    /**
     * 将审核老师负责的专业id 插入 审核老师-专业联合表 sys_examinteacher_education
     * @param id
     * @return
     */
    Integer insertIntoExaminteacherEducation(@Param("examteaId")String examteaId,@Param("id")Integer id);

    /**
     * 根据审核老师id获取 负责审核的专业id
     * @param id
     * @return
     */
    Integer getExamincationIdByExaminId(@Param("id")String id);

    /**
     * 更新审核老师-负责专业表
     * @param examId
     * @param eduId
     * @return
     */
    Integer updateExamincationEducation(@Param("examId")String examId,@Param("eduId")Integer eduId);

    /**
     * 删除审核老师-负责专业表
     * @param examId
     * @return
     */
    Integer deleteExamincationEducation(@Param("examId")String examId);
    /**
     * 批量删除审核老师-负责专业表
     * @param examId
     * @return
     */
    Integer deleteExamincationEducationBatch(@Param("examIds")List<String> examId);

    /**
     * 根据专业 id 获取审核老师 id列表，升序排序
     * @param id
     * @return
     */
    List<String> getExaminteachersIdByEducationId(@Param("id")Integer id);

    /**
     * 根据审核老师id获取学院id
     * @param id
     * @return
     */
    College getCollegeIdAndNameByExaminId(@Param("id") String id);

    /**
     *根据审题老师id获取老师个人信息
     * @return
     */
    User_Examinteacher getExaminTeacherData(@Param("id") String id);

    //----------------------------批量插入审题老师数据--------------------------
    /**
     * 批量插入审题老师用户个人信息数据
     * @param list
     * @return
     */
    Integer importUserData(List<User_Examinteacher> list);

    /**
     * 批量插入审题老师表信息
     * @param list
     * @return
     */
    Integer importExminTeacherData(List<User_Examinteacher> list);

    /**
     * 批量导入审题老师对应学院信息
     * @param list
     * @return
     */
    Integer importExminTeacherCollData(List<User_Examinteacher> list);

    /**
     * 批量导入审题老师负责专业信息
     * @param list
     * @return
     */
    Integer importExminTeacherEduData(List<User_Examinteacher> list);
}

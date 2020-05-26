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
 * @since 2020-01-19
 */
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 获取所有审题小组成员信息列表
     * @return
     */
    List<User_Examinteacher> getexamin_teachersList(@Param("teaId")String teaId, @Param("teaName") String teaName, @Param("status") Integer status, @Param("teaEducation") String teaEducation, @Param("teaCollege")String teaCollege);
    /**
     * 获取所有学生信息列表
     * @return
     */
    List<User_Student> getStudentsList(@Param("stuId")String stuId, @Param("stuName") String stuName, @Param("status") Integer status,@Param("stuClass") Integer stuClass,@Param("stuCollege") String stuCollege,@Param("stuEducation") String stuEducation);
    /**
     * 获取所有老师信息列表
     * @return
     */
    List<User_Teacher> getTeachersList(@Param("teaId")String teaId, @Param("teaName") String teaName, @Param("status") Integer status,@Param("teaEducation") String teaEducation,@Param("teaCollege")String teaCollege);

}

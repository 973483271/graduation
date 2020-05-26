package com.bysj.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bysj.sys.entity.Student;
import com.bysj.sys.entity.User_Student;
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
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 获取已经选题的学生全部信息
     * @return
     */
    List<User_Student> getHasAlreadyChooseTopicStudentData(@Param("inputData") String inputData);

    /**
     * 获取未选题学生全部信息
     * @param iData
     * @return
     */
    List<User_Student> getNotChooseTopicStudentSituation(@Param("iData") String iData);

    /**
     * 根据学生id查询学生所属组织id
     * @param id
     * @return
     */
    Integer getCollIdByStudentId(String id);

    //----------------------------批量插入学生数据--------------------------
    /**
     * 批量插入学生用户个人信息数据
     * @param list
     * @return
     */
    Integer importUserData(List<User_Student> list);

    /**
     * 批量插入学生表信息
     * @param list
     * @return
     */
    Integer importStudentData(List<User_Student> list);

    /**
     * 批量导入学生对应专业信息
     * @param list
     * @return
     */
    Integer importStudentEduData(List<User_Student> list);
}

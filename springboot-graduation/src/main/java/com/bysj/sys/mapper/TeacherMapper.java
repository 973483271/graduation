package com.bysj.sys.mapper;

import com.bysj.sys.entity.College;
import com.bysj.sys.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bysj.sys.entity.User_Student;
import com.bysj.sys.entity.User_Teacher;
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
public interface TeacherMapper extends BaseMapper<Teacher> {
    /**
     * 根据学生所在学院（部）id 查出本学院（部）的所有导师工号和姓名
     */
    List<Teacher>  getTeacherNameAndIdByStudentId(@Param("stuId") String stuId);
    //----------------------------批量插入指导老师数据--------------------------
    /**
     * 批量插入指导老师用户个人信息数据
     * @param list
     * @return
     */
    Integer importUserData(List<User_Teacher> list);

    /**
     * 批量插入指导老师表信息
     * @param list
     * @return
     */
    Integer importTeacherData(List<User_Teacher> list);

    /**
     * 批量导入指导老师对应学院信息
     * @param list
     * @return
     */
    Integer importTeacherCollData(List<User_Teacher> list);
}

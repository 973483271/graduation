package com.bysj.sys.service;

import com.bysj.sys.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
public interface IAdminService extends IService<Admin> {
//-----------------------------------审题小组成员信息--------------------------------------------
    /**
     * 获取所有审题小组成员信息列表
     * @return
     */
    List<User_Examinteacher> getexamin_teachersList(String teaId, String teaName, Integer status, String teaEducation, String teaCollege);

    /**
     * 添加审题小组成员信息
     * @param examinteaId
     * @param password
     * @param examinteaName
     * @param status
     * @param examinteaEducation
     * @param teaCollId
     * @return
     */
    Integer addExaminteacherData(String examinteaId,String password,String examinteaName,Integer status,String examinteaEducation,Integer teaCollId,Integer examinteaResEducation);

    /**
     * 根据id查询审题小组成员信息 用于修改审题小组成员信息时显示
     */
    User_Examinteacher getExaminteacherById(String id);

    /**
     * 更新审题小组成员信息
     * @param ue
     * @return
     */
    Integer examin_teachersUpdate(User_Examinteacher ue);

    /**
     * 删除审题员信息（一条数据）
     * @param id
     * @return
     */
    Integer examin_teachersDelete(String id);

    /**
     * 删除审题员信息（批量）
     * @param ids
     * @return
     */
    Integer examin_teachersDeleteBatch(List<String> ids);

    /**
     * 批量新增审题老师信息
     */
    Integer importExaminTeacherData(MultipartFile file)  throws  Exception;
//--------------------------------------学生信息-------------------------------------------------
    /**
     * 获取所有学生信息列表
     * @return
     */
    List<User_Student> getStudentsList(String stuId, String stuName, Integer status,Integer stuClass,String stuCollege,String stuEducation);

    /**
     * 新增学生信息
     * @param userId
     * @param password
     * @param stuName
     * @param stuClass
     * @param stuEducation
     * @param status
     * @return
     */
    Integer addStudentData(String userId,String password,String stuName,Integer stuClass,Integer stuEducation,Integer status);

    /**
     * 根据id查询学生信息 用于修改学生信息时显示
     */
    User_Student getStudentById(String id);
    /**
     * 更新学生信息
     * @param us
     * @return
     */
    Integer studentsUpdate(User_Student us);
    /**
     * 删除学生信息（一条数据）
     * @param id
     * @return
     */
    Integer studentsDelete(String id);
    /**
     * 删除学生信息（批量）
     * @param ids
     * @return
     */
    Integer studentsDeleteBatch(List<String> ids);

    /**
     * 批量导入学生数据
     * @param file
     * @return
     */
    Integer importStudentData( MultipartFile file) throws Exception;
//------------------------------------教师信息-------------------------------------
    /**
     * 获取所有教师信息列表
     * @return
     */
    List<User_Teacher> getTeachersList(String teaId,String teaName,Integer status,String teaEducation,String teaCollege);

    /**
     * 添加老师信息
     * @param teaId
     * @param password
     * @param teaName
     * @param status
     * @param teaEducation
     * @return
     */
    Integer addTeacherData(String teaId,String password,String teaName,Integer status,String teaEducation,Integer teaCollId);
    /**
     * 根据id查询教师信息 用于修改教师信息时显示
     */
    User_Teacher getTeacherById(String id);
    /**
     * 更新老师信息
     * @param ut
     * @return
     */
    Integer teachersUpdate(User_Teacher ut);
    /**
     * 删除教师信息（一条数据）
     * @param id
     * @return
     */
    Integer teachersDelete(String id);
    /**
     * 删除教师信息（批量）
     * @param ids
     * @return
     */
    Integer teachersDeleteBatch(List<String> ids);

    /**
     * 批量导入教师信息
     * @param file
     * @return
     */
    Integer importTeacherData(MultipartFile file) throws Exception ;
//-------------------------------------出题情况统计分析------------------------------------
    /**
     * 获取出题情况信息
     * @return
     */
    List<AssignTopicSituation> getAssignTopicSituation(String tTitle);

    /**
     * 获取指定专业指导教师出题信息
     * @return
     */
    List<AssignTopicSituation> getAppointEducationAssignTopicDetails(Integer id);

    /**
     * 获取导师名称及导师题目过审率，用于条形统计显示
     */
    AssignTopicSituation getAssignTopicDetails(Integer id);
    //-------------------------------选题情况统计---------------------------------------------

    /**
     * 获取已经选题所有学生信息
     * @return
     */
    List<User_Student> getHasAlreadyChooseTopicStudentData(String inputData);

    /**
     * 获取未选题全部学生信息
     * @param iData
     * @return
     */
    List<User_Student> getNotChooseTopicStudentSituation(String iData);

    /**
     * 获取学生总数，已选人数，未选人数 用于主页显示
     * @return
     */
    User_Student getChooseTopicStudentNum();
    //---------------------------------审题统计--------------------------------------------

    /**
     * 获取整体审核题目信息详情
     * @param tTitle
     * @return
     */
    List<AssignTopicSituation> getExaminTopicSituationList(String tTitle);

    /**
     *获取指定专业审核题目信息详情
     * @param id
     * @return
     */
    List<AssignTopicSituation> getAppointEducationExaminTopicDetails(Integer id);

    /**
     * 获取审题教师名称及审题通过率，用于条形统计显示
     */
    AssignTopicSituation getExaminTopicDetails(Integer id);


}

package com.bysj.sys.service.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.common.util.MD5Util;
import com.bysj.common.util.WebExcelUtil;
import com.bysj.sys.entity.*;
import com.bysj.sys.mapper.*;
import com.bysj.sys.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeacherCollegeMapper teacherCollegeMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private StudentEducationMapper studentEducationMapper;
    @Autowired
    private ExaminteacherMapper examinteacherMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private ClassMapper classMapper;
//----------------------------------------审题小组成员信息------------------------------------------------

    @Override
    public List<User_Examinteacher> getexamin_teachersList(String teaId, String teaName, Integer status, String teaEducation, String teaCollege) {
        List<User_Examinteacher> examinteachers = adminMapper.getexamin_teachersList(teaId,teaName,status,teaEducation,teaCollege);
        //根据审核老师id获取负责专业
        //1.根据审核老师ID获取专业id集合
        List<User_Examinteacher> examinteacherList =new ArrayList<>();
        for(User_Examinteacher examinteacher:examinteachers){
           String resReducation =  examinteacherMapper.getEducationByExaminTeaId(examinteacher.getExaminteaId());
            examinteacher.setExaminteaResEducation(resReducation);
            examinteacherList.add(examinteacher);
        }
        return examinteacherList;
    }

    @Override
    @Transactional      //事务回滚
    public Integer addExaminteacherData(String examinteaId,String password,String examinteaName,Integer status,String examinteaEducation,Integer teaCollId,Integer examinteaResEducation) {
        //判断用户名是否存在
        QueryWrapper qwJudgeUserId =new QueryWrapper();
        qwJudgeUserId.eq("user_id",examinteaId);
        if((userMapper.selectOne(qwJudgeUserId))!=null){
            return -1;
        }
        //存储审题成员信息到 sys_user表中用于登录
        User user  =new User();
        user.setUserId(examinteaId);
        //加密密码
        user.setPassword(MD5Util.md5_public_salt(password));
        user.setStatus(status);
        user.setUsertype(3);
        Integer index =  userMapper.insert(user);
        //存储审题成员信息到sys_examinteacher中
        Examinteacher examinteacher = new Examinteacher();
        examinteacher.setExaminteaId(examinteaId);
        examinteacher.setExaminteaName(examinteaName);
        examinteacher.setExaminteaEducation(examinteaEducation);
        examinteacher.setExaminteaSchool("五邑大学");
        index+=examinteacherMapper.insert(examinteacher);
        //存储审题成员id和学院id到 老师学院关联表 sys_teacher_college
        TeacherCollege tc = new TeacherCollege();
        tc.setTeaId(examinteaId);
        tc.setCollId(teaCollId);
        index+=teacherCollegeMapper.insert(tc);
        //存储负责专业id到 审核老师-专业关联表 sys_examinteacher_education
        index+=examinteacherMapper.insertIntoExaminteacherEducation(examinteaId,examinteaResEducation);
        return index;
    }

    @Override
    public User_Examinteacher getExaminteacherById(String id) {
        User_Examinteacher ue = new User_Examinteacher();
        //查询用户表获取用户账号状态（status）、密码
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        User user = userMapper.selectOne(qwUser);
        ue.setExaminteaId(id);
        ue.setPassword(user.getPassword());
        ue.setStatus(user.getStatus());
        //查询审题老师表，获取姓名、学位、邮箱、手机号
        QueryWrapper qwexaminTeacher = new QueryWrapper();
        qwexaminTeacher.eq("examintea_id",id);
        Examinteacher examinteacher = examinteacherMapper.selectOne(qwexaminTeacher);

        ue.setExaminteaEmail(examinteacher.getExaminteaEmail());
        ue.setExaminteaTelphone(examinteacher.getExaminteaTelphone());
        ue.setExaminteaName(examinteacher.getExaminteaName());
        ue.setExaminteaEducation(examinteacher.getExaminteaEducation());
        //查询老师-学院关联表获取学院名称
        //1.通过关联表查出学院
        QueryWrapper qwTeaColl = new QueryWrapper();
        qwTeaColl.eq("tea_id",id);
        Integer collId = teacherCollegeMapper.selectOne(qwTeaColl).getCollId();
        //2.通过id查学院表获取学院名称
        QueryWrapper qwColl = new QueryWrapper();
        qwColl.eq("coll_id",collId);
        String collName = collegeMapper.selectOne(qwColl).getCollName();

        ue.setTeaCollege(collName);
        return ue;
    }

    @Override
    @Transactional      //事务回滚
    public Integer examin_teachersUpdate(User_Examinteacher ue) {
        //存储更新 用户登录表 及 审题老师表 老师-学院关联表 数据
        User user = new User();
        Examinteacher examinteacher = new Examinteacher();
        TeacherCollege tc = new TeacherCollege();
        //更新用户登录表数据
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",ue.getExaminteaId());
        user.setStatus(ue.getStatus());
        Integer index = userMapper.update(user,qwUser);
        //更新审题老师表数据
        QueryWrapper qwexaminTeacher =new QueryWrapper();
        qwexaminTeacher.eq("examintea_id",ue.getExaminteaId());
        examinteacher.setExaminteaName(ue.getExaminteaName());
        examinteacher.setExaminteaEmail(ue.getExaminteaEmail());
        examinteacher.setExaminteaTelphone(ue.getExaminteaTelphone());
        examinteacher.setExaminteaEducation(ue.getExaminteaEducation());
        examinteacher.setExaminteaUpdateTime(new Date());

        index +=  examinteacherMapper.update(examinteacher,qwexaminTeacher);
        //更新老师-学院关联表
        QueryWrapper qwTeaColl =new QueryWrapper();
        qwTeaColl.eq("tea_id",ue.getExaminteaId());
        tc.setCollId(ue.getTeaCollId());
        index+=teacherCollegeMapper.update(tc,qwTeaColl);
        //更新审核老师-负责专业 关联表
        index+=examinteacherMapper.updateExamincationEducation(ue.getExaminteaId(),ue.getExaminteaResEducationId());
        return index;
    }

    @Override
    @Transactional      //事务回滚
    public Integer examin_teachersDelete(String id) {
        //删除登录用户表数据
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        Integer index = userMapper.delete(qwUser);
        //删除审题员表数据
        QueryWrapper qwexaminTeacher = new QueryWrapper();
        qwexaminTeacher.eq("examintea_id",id);
        index+=examinteacherMapper.delete(qwexaminTeacher);
        //删除老师-学院关联表数据
        QueryWrapper qwTeaColl = new QueryWrapper();
        qwTeaColl.eq("tea_id",id);
        index+=teacherCollegeMapper.delete(qwTeaColl);
        //删除老师-负责专业关联表
        index+=examinteacherMapper.deleteExamincationEducation(id);
        return index;
    }

    @Override
    @Transactional      //事务回滚
    public Integer examin_teachersDeleteBatch(List<String> ids) {
        //批量删除用户表信息
        QueryWrapper qwUsers = new QueryWrapper();
        qwUsers.in("user_id",ids);
        Integer index = userMapper.delete(qwUsers);
        //批量删除审题员表信息
        QueryWrapper qwTeachers = new QueryWrapper();
        qwTeachers.in("examintea_id",ids);
        index +=examinteacherMapper.delete(qwTeachers);
        //批量删除教师-学院关联表信息
        QueryWrapper qwTeaColl = new QueryWrapper();
        qwTeaColl.in("tea_id",ids);
        index +=teacherCollegeMapper.delete(qwTeaColl);
        //批量删除教师-专业关联表信息
        index+=examinteacherMapper.deleteExamincationEducationBatch(ids);

        return index;
    }

    @Override
    @Transactional
    public Integer importExaminTeacherData(MultipartFile file) throws  Exception {
        Integer index;
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<User_Examinteacher> listUser = new ArrayList<>(); //存储审题老师信息
        List<User_Examinteacher> list;             //获取Excl表信息
        // excel的数据
        list = WebExcelUtil.importExcel(file, 0, 1, User_Examinteacher.class);
        for (User_Examinteacher u : list) {
            User_Examinteacher ue = new User_Examinteacher();
            ue.setExaminteaId(u.getExaminteaId()); //工号
            ue.setExaminteaName(u.getExaminteaName());//姓名
            ue.setExaminteaEducation(u.getExaminteaEducation()); //职称
            ue.setExaminteaTelphone(u.getExaminteaTelphone()); //手机号
            ue.setExaminteaEmail(u.getExaminteaEmail()); //邮箱
            ue.setStatus(0);      //状态 正常
            ue.setPassword(MD5Util.md5_public_salt("123456")); //密码 默认 123456
            ue.setUsertype(3);      // 用户类型  审题老师
            ue.setExaminteaSchool("五邑大学");   //学校
            //将学院名称转为id存储
            String teaColl = u.getTeaCollege();
            QueryWrapper qwTeaColl = new QueryWrapper();
            qwTeaColl.eq("coll_name",teaColl);
            ue.setTeaCollId(collegeMapper.selectOne(qwTeaColl).getCollId());
            //将负责专业名称转为id存储
            String resEdu = u.getExaminteaResEducation();
            QueryWrapper qwResEdu = new QueryWrapper();
            qwResEdu.eq("edu_name",u.getExaminteaResEducation());
            ue.setExaminteaResEducationId(educationMapper.selectOne(qwResEdu).getEduId());
            listUser.add(ue);
        }
        //插入数据
        index = examinteacherMapper.importUserData(listUser); //用户表
        index += examinteacherMapper.importExminTeacherData(listUser);//老师信息表
        index += examinteacherMapper.importExminTeacherCollData(listUser); //老师-学院表
        index+= examinteacherMapper.importExminTeacherEduData(listUser); //负责专业表
        return index;
    }


    //------------------------------学生信息---------------------------------------
    @Override
    public List<User_Student> getStudentsList(String stuId, String stuName, Integer status, Integer stuClass,String stuCollege,String stuEducation) {
        List<User_Student> students = adminMapper.getStudentsList(stuId,stuName,status,stuClass,stuCollege,stuEducation);
        return students;
    }

    @Override
    @Transactional      //事务回滚
    public Integer addStudentData(String userId,String password,String stuName,Integer stuClass,Integer stuEducation,Integer status) {
        //判断用户名是否存在
        QueryWrapper qwJudgeUserId =new QueryWrapper();
        qwJudgeUserId.eq("user_id",userId);
        if((userMapper.selectOne(qwJudgeUserId))!=null){
            return -1;
        }
        //存储学生信息到 sys_user表中用于登录
        User user  =new User();
        user.setUserId(userId);
        //加密密码
        user.setPassword(MD5Util.md5_public_salt(password));
        user.setStatus(status);
        user.setUsertype(2);
        Integer index =  userMapper.insert(user);
        //存储学生信息到sys_student中
        Student student = new Student();
        student.setStuId(userId);
        student.setStuName(stuName);
        student.setStuClass(stuClass);
        student.setStuSchool("五邑大学");
        index+= studentMapper.insert(student);
        //存储学生Id和专业Id 到 学生-专业联合表 sys_student_education
        StudentEducation se = new StudentEducation();
        se.setStuId(userId);
        se.setEduId(stuEducation);
        index+= studentEducationMapper.insert(se);
        return index;
    }

    @Override
    public User_Student getStudentById(String id) {
        User_Student us = new User_Student();
        //查询用户表获取用户账号状态（status）、密码
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        User user = userMapper.selectOne(qwUser);
        us.setStuId(id);
        us.setPassword(user.getPassword());
        us.setStatus(user.getStatus());
        //查询学生表，获取姓名、邮箱、手机号
        QueryWrapper qwStudent = new QueryWrapper();
        qwStudent.eq("stu_id",id);
        Student student = studentMapper.selectOne(qwStudent);
        us.setStuEmail(student.getStuEmail());
        us.setStuTelphone(student.getStuTelphone());
        us.setStuName(student.getStuName());
        us.setStuClass(student.getStuClass());
        //查询学生-专业关联表获取学生专业Id
        StudentEducation se = studentEducationMapper.selectOne(qwStudent);
        us.setStuEducationId(se.getEduId());
        return us;
    }

    @Override
    @Transactional      //事务回滚
    public Integer studentsUpdate(User_Student us) {
        //存储更新 用户登录表 及 学生表 数据
        User user = new User();
        Student student = new Student();
        //更新用户登录表数据
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",us.getStuId());
        user.setStatus(us.getStatus());

        Integer index = userMapper.update(user,qwUser);
        //更新学生表数据
        QueryWrapper qwStudent =new QueryWrapper();
        qwStudent.eq("stu_id",us.getStuId());
        student.setStuName(us.getStuName());
        student.setStuEmail(us.getStuEmail());
        student.setStuTelphone(us.getStuTelphone());
        student.setStuClass(us.getStuClass());
        student.setStuUpdateTime(new Date());
        index +=  studentMapper.update(student,qwStudent);
        //更新学生-专业联合表数据
        QueryWrapper qwStuEdu =new QueryWrapper();
        qwStuEdu.eq("stu_id",us.getStuId());
        StudentEducation se = new StudentEducation();
        se.setEduId(us.getStuEducationId());
        index +=  studentEducationMapper.update(se,qwStuEdu);
        return index;
    }

    @Override
    @Transactional      //事务回滚
    public Integer studentsDelete(String id) {
        //删除登录用户表数据
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        Integer index = userMapper.delete(qwUser);
        //删除学生表数据
        QueryWrapper qwStudent = new QueryWrapper();
        qwStudent.eq("stu_id",id);
        index+=studentMapper.delete(qwStudent);
        //删除学生-专业关联表信息
        QueryWrapper qwStudentEdu = new QueryWrapper();
        qwStudentEdu.eq("stu_id",id);
        index+=studentEducationMapper.delete(qwStudentEdu);
        return index;
    }

    @Override
    @Transactional      //事务回滚
    public Integer studentsDeleteBatch(List<String> ids) {
        //批量删除用户表信息
        QueryWrapper qwUsers = new QueryWrapper();
        qwUsers.in("user_id",ids);
        Integer index = userMapper.delete(qwUsers);
        //批量删除学生表信息
        QueryWrapper qwStudents = new QueryWrapper();
        qwStudents.in("stu_id",ids);
        index +=studentMapper.delete(qwStudents);
        //批量删除学生-专业关联表信息
        QueryWrapper qwStudentsEdu = new QueryWrapper();
        qwStudentsEdu.in("stu_id",ids);
        index +=studentEducationMapper.delete(qwStudentsEdu);
        return index;
    }

    @Override
    @Transactional
    public Integer importStudentData(MultipartFile file) throws Exception {
        Integer index;
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<User_Student> listUser = new ArrayList<>(); //存储学生信息
        List<User_Student> list;             //获取Excl表信息
            // excel的数据
            list = WebExcelUtil.importExcel(file, 0, 1, User_Student.class);
            for (User_Student u : list) {
                User_Student us = new User_Student();
                us.setStuId(u.getStuId());   //用户名
                us.setPassword(MD5Util.md5_public_salt("123456"));     //密码 默认123456
                us.setUsertype(2);            //用户类型
                us.setStatus(0);              //账号状态
                us.setStuName(u.getStuName());//学生姓名
                //将学生专业转为id形式存储
                String stuEducation = u.getStuEducation();
                QueryWrapper qwEdu = new QueryWrapper();
                qwEdu.eq("edu_name",stuEducation);
                Education e  = educationMapper.selectOne(qwEdu);
                us.setStuEducationId(e.getEduId());
                us.setStuTelphone(u.getStuTelphone());//学生电话
                us.setStuSchool("五邑大学");//学生学校
                //学生班级(将班级名称转为id形式存储)
                Integer stuClass = u.getStuClassName();
                QueryWrapper qwClass = new QueryWrapper();
                qwClass.eq("class_name",stuClass);
                us.setStuClass(classMapper.selectOne(qwClass).getStuClass());
                us.setStuEmail(u.getStuEmail());//学生邮箱
                listUser.add(us);
            }
            //插入数据
            index=  studentMapper.importUserData(listUser);   //用户表
            index+= studentMapper.importStudentEduData(listUser); //学生-专业表
            index+= studentMapper.importStudentData(listUser);//学生信息表
        return index;
    }

    //----------------------------------------老师信息---------------------------------------------------
    @Override
    public List<User_Teacher> getTeachersList(String teaId, String teaName, Integer status, String teaEducation,String teaCollege) {
        List<User_Teacher> teachers = adminMapper.getTeachersList(teaId,teaName,status,teaEducation,teaCollege);
        return teachers;
    }

    @Override
    @Transactional      //事务回滚
    public Integer addTeacherData(String teaId, String password, String teaName, Integer status, String teaEducation,Integer  teaCollId) {
        //判断用户名是否存在
        QueryWrapper qwJudgeUserId =new QueryWrapper();
        qwJudgeUserId.eq("user_id",teaId);
        if((userMapper.selectOne(qwJudgeUserId))!=null){
            return -1;
        }
        //存储老师信息到 sys_user表中用于登录
        User user  =new User();
        user.setUserId(teaId);
        //加密密码
        user.setPassword(MD5Util.md5_public_salt(password));
        user.setStatus(status);
        user.setUsertype(1);
        Integer index =  userMapper.insert(user);
        //存储老师信息到sys_teacher中
        Teacher teacher = new Teacher();
        teacher.setTeaId(teaId);
        teacher.setTeaName(teaName);
        teacher.setTeaEducation(teaEducation);
        teacher.setTeaSchool("五邑大学");
        index+=teacherMapper.insert(teacher);
        //存储老师和学院id到 老师学院关联表 sys_teacher_college
        TeacherCollege tc = new TeacherCollege();
        tc.setTeaId(teaId);
        tc.setCollId(teaCollId);
        index+=teacherCollegeMapper.insert(tc);
        return index;
    }

    @Override
    public User_Teacher getTeacherById(String id) {
        User_Teacher ut = new User_Teacher();
        //查询用户表获取用户账号状态（status）、密码
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        User user = userMapper.selectOne(qwUser);
        ut.setTeaId(id);
        ut.setPassword(user.getPassword());
        ut.setStatus(user.getStatus());
        //查询老师表，获取姓名、学位、邮箱、手机号
        QueryWrapper qwTeacher = new QueryWrapper();
        qwTeacher.eq("tea_id",id);
        Teacher teacher = teacherMapper.selectOne(qwTeacher);

        ut.setTeaEmail(teacher.getTeaEmail());
        ut.setTeaTelphone(teacher.getTeaTelphone());
        ut.setTeaName(teacher.getTeaName());
        ut.setTeaEducation(teacher.getTeaEducation());
        //查询老师-学院关联表获取学院名称
        //1.通过关联表查出学院
        QueryWrapper qwTeaColl = new QueryWrapper();
        qwTeaColl.eq("tea_id",id);
        Integer collId = teacherCollegeMapper.selectOne(qwTeaColl).getCollId();
        //2.通过id查学院表获取学院名称
        QueryWrapper qwColl = new QueryWrapper();
        qwColl.eq("coll_id",collId);
        String collName = collegeMapper.selectOne(qwColl).getCollName();

        ut.setTeaCollege(collName);
        return ut;
    }

    @Override
    @Transactional      //事务回滚
    public Integer teachersUpdate(User_Teacher ut) {
        //存储更新 用户登录表 及 老师表 老师-学院关联表 数据
        User user = new User();
        Teacher teacher = new Teacher();
        TeacherCollege tc = new TeacherCollege();
        //更新用户登录表数据
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",ut.getTeaId());
        user.setStatus(ut.getStatus());
        Integer index = userMapper.update(user,qwUser);
        //更新老师表数据
        QueryWrapper qwTeacher =new QueryWrapper();
        qwTeacher.eq("tea_id",ut.getTeaId());
        teacher.setTeaName(ut.getTeaName());
        teacher.setTeaEmail(ut.getTeaEmail());
        teacher.setTeaTelphone(ut.getTeaTelphone());
        teacher.setTeaEducation(ut.getTeaEducation());
        teacher.setTeaUpdateTime(new Date());
        index +=  teacherMapper.update(teacher,qwTeacher);
        //更新老师-学院关联表
        QueryWrapper qwTeaColl =new QueryWrapper();
        qwTeaColl.eq("tea_id",ut.getTeaId());
        tc.setCollId(ut.getTeaCollId());
        index+=teacherCollegeMapper.update(tc,qwTeaColl);
        return index;
    }

    @Override
    @Transactional
    public Integer teachersDelete(String id) {
        //删除登录用户表数据
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        Integer index = userMapper.delete(qwUser);
        //删除教师表数据
        QueryWrapper qwTeacher = new QueryWrapper();
        qwTeacher.eq("tea_id",id);
        index+=teacherMapper.delete(qwTeacher);
        //删除老师-学院关联表数据
        QueryWrapper qwTeaColl = new QueryWrapper();
        qwTeaColl.eq("tea_id",id);
        index+=teacherCollegeMapper.delete(qwTeaColl);

        return index;
    }

    @Override
    @Transactional
    public Integer teachersDeleteBatch(List<String> ids) {
        //批量删除用户表信息
        QueryWrapper qwUsers = new QueryWrapper();
        qwUsers.in("user_id",ids);
        Integer index = userMapper.delete(qwUsers);
        //批量删除教师表信息
        QueryWrapper qwTeachers = new QueryWrapper();
        qwTeachers.in("tea_id",ids);
        index +=teacherMapper.delete(qwTeachers);
        //批量删除教师-学院关联表信息
        QueryWrapper qwTeaColl = new QueryWrapper();
        qwTeaColl.in("tea_id",ids);
        index +=teacherCollegeMapper.delete(qwTeaColl);
        return index;
    }

    @Override
    @Transactional
    public Integer importTeacherData(MultipartFile file) throws Exception {
        Integer index;
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<User_Teacher> listUser = new ArrayList<>(); //存储指导老师信息
        List<User_Teacher> list;             //获取Excl表信息
        // excel的数据
        list = WebExcelUtil.importExcel(file, 0, 1, User_Teacher.class);
        for (User_Teacher u : list) {
            User_Teacher us = new User_Teacher();
            us.setTeaId(u.getTeaId());     //工号
            us.setTeaName(u.getTeaName()); //姓名
            us.setTeaEducation(u.getTeaEducation()); //职称
            us.setTeaEmail(u.getTeaEmail()); //邮箱
            us.setTeaTelphone(u.getTeaTelphone()); //手机号
            us.setPassword(MD5Util.md5_public_salt("123456")); //密码
            us.setStatus(0);                //状态 正常
            us.setTeaSchool("五邑大学");   //指导老师学校
            us.setUsertype(1);           //用户类型
            //将学院名称转为id存储
            String teaColl = u.getTeaCollege();
            QueryWrapper qwTeaColl = new QueryWrapper();
            qwTeaColl.eq("coll_name",teaColl);
            us.setTeaCollId(collegeMapper.selectOne(qwTeaColl).getCollId());
            listUser.add(us);
        }
        //插入数据
        index = teacherMapper.importUserData(listUser);   //用户表
        index+= teacherMapper.importTeacherData(listUser); //老师信息表
        index+= teacherMapper.importTeacherCollData(listUser);//老师-学院表
        return index;
    }

    @Override
    public List<AssignTopicSituation>  getAssignTopicSituation(String tTitle) {
        List<AssignTopicSituation> atsList = new ArrayList<>();
        AssignTopicSituation ats ;
        //查出所有专业
        List<AssignTopicSituation> educations = educationMapper.getAssignTopicSituation(tTitle);
        for(AssignTopicSituation edu: educations){
            ats = new AssignTopicSituation();
            ats.setEduId(edu.getEduId()); //设置专业id
            ats.setEducation(edu.getEducation());//设置专业名称
            //设置学院名称
            ats.setCollege(edu.getCollege()); //设置学院名称
            //根据id获取专业学生人数
            QueryWrapper qwStuEdu= new QueryWrapper();
            qwStuEdu.eq("edu_id",edu.getEduId());
            Integer studentNum = studentEducationMapper.selectCount(qwStuEdu);
            ats.setStudentNum(studentNum);//设置专业学生人数
            //查询通过审核的题目数
            QueryWrapper qwTopicNum = new QueryWrapper();
            qwTopicNum.eq("t_eduId",ats.getEduId());
            qwTopicNum.in("t_status",2,3);
            Integer topicNum = topicMapper.selectCount(qwTopicNum);
            ats.setTopicNum(topicNum);//设置该专业题目数量
            //设置结果 多/少 多少题
            ats.setResultNum(topicNum-studentNum);
            atsList.add(ats);
        }
        return atsList;
    }

    @Override
    public List<AssignTopicSituation> getAppointEducationAssignTopicDetails(Integer id) {
        List<AssignTopicSituation> ats = new ArrayList<>();
        AssignTopicSituation teacher;
        //获取出指定专业 所有指导老师id和出题数目
        List<AssignTopicSituation> teachers =  topicMapper.getMaketeacherAppointEducation(id);
        for(AssignTopicSituation a:teachers){
            teacher = new AssignTopicSituation();
            //设置指导教师id
            teacher.setTeacherId(a.getTeacherId());
            //设置指导教师名称
            QueryWrapper qwTea = new QueryWrapper();
            qwTea.eq("tea_id",a.getTeacherId());
            teacher.setTeacherName(teacherMapper.selectOne(qwTea).getTeaName());
            //设置导师出题数目
            Integer teacherTopicNum =a.getTeacherTopicNum();
            teacher.setTeacherTopicNum(teacherTopicNum);
            //设置过审题目数量
            QueryWrapper qwPassCount = new QueryWrapper();
            qwPassCount.eq("t_eduId",id);
            qwPassCount.eq("t_makeTeacher",a.getTeacherId());
            qwPassCount.in("t_status",2,3);
            Integer teacherPassNum =topicMapper.selectCount(qwPassCount);
            teacher.setTeacherPassNum(teacherPassNum);
            //设置通过率
            double f1 = new BigDecimal((float)teacherPassNum/teacherTopicNum).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            teacher.setTeacherPassingRate(f1*100);
            ats.add(teacher);
        }
        return ats;
    }

    @Override
    public AssignTopicSituation getAssignTopicDetails(Integer id) {
        List<AssignTopicSituation> ats = new ArrayList<>();
        AssignTopicSituation teacher ;
        //获取出指定专业题目的 所有老师id
        List<AssignTopicSituation> teachers = topicMapper.getMaketeacherAppointEducation(id);
        StringBuffer xsb = new StringBuffer("[");
        StringBuffer ysb = new StringBuffer("[");
        for (AssignTopicSituation a : teachers) {
            teacher = new AssignTopicSituation();
            //拼接x轴数据
            QueryWrapper qwTea = new QueryWrapper();
            qwTea.eq("tea_id", a.getTeacherId());
            xsb.append("\""+teacherMapper.selectOne(qwTea).getTeaName()+"\",");
            //获取导师出题数目
            Integer teacherTopicNum = a.getTeacherTopicNum();
            //获取过审题目数量
            QueryWrapper qwPassCount = new QueryWrapper();
            qwPassCount.eq("t_eduId", id);
            qwPassCount.eq("t_makeTeacher", a.getTeacherId());
            qwPassCount.in("t_status",2,3);

            Integer teacherPassNum = topicMapper.selectCount(qwPassCount);
            //计算通过率
            double f1 = new BigDecimal((float) teacherPassNum / teacherTopicNum).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            //拼接y轴数据
            ysb.append(f1*100+",");
        }
        xsb.append("]");
        ysb.append("]");
        //去除最后一个逗号
        if(teachers.size()!=0) {
            xsb.delete(xsb.length() - 2, xsb.length() - 1);
            ysb.delete(ysb.length() - 2, ysb.length() - 1);
        }
        //存储拼接好的数据
        AssignTopicSituation xyData = new AssignTopicSituation();
        xyData.setxAxisData(xsb.toString());
        xyData.setyAxisData(ysb.toString());
        return xyData;
    }

    @Override
    public List<User_Student> getHasAlreadyChooseTopicStudentData(String inputData) {
        List<User_Student> us = studentMapper.getHasAlreadyChooseTopicStudentData(inputData);
        return us;
    }

    @Override
    public List<User_Student> getNotChooseTopicStudentSituation(String iData) {
        List<User_Student> us =  studentMapper.getNotChooseTopicStudentSituation(iData);
        return us;
    }

    @Override
    public User_Student getChooseTopicStudentNum() {
        User_Student us = new User_Student();
        //设置已选题学生总数
        Integer hasChoose = studentMapper.getHasAlreadyChooseTopicStudentData(null).size();
        us.setStuHasChooseTopicNum(hasChoose);
        //设置未选题学生总数
        Integer notChoose = studentMapper.getNotChooseTopicStudentSituation(null).size();
        us.setStuNotChooseTopicNum(notChoose);
        //设置选题学生总数
        QueryWrapper qwStuTotal = new QueryWrapper();
        Integer total =studentMapper.selectCount(qwStuTotal);
        us.setStuChooseTopicTotalNum(total);
        return us;
    }

    @Override
    public List<AssignTopicSituation> getExaminTopicSituationList(String tTitle) {
        List<AssignTopicSituation> atsList = new ArrayList<>();
        AssignTopicSituation ats ;
        //查出所有专业
        List<AssignTopicSituation> educations = educationMapper.getAssignTopicSituation(tTitle);
        for(AssignTopicSituation edu: educations){
            ats = new AssignTopicSituation();
            ats.setEduId(edu.getEduId()); //设置专业id
            ats.setEducation(edu.getEducation());//设置专业名称
            //设置学院名称
            ats.setCollege(edu.getCollege()); //设置学院名称
            //根据专业id 和审题老师不为空查询审核题目总数
            QueryWrapper qw = new QueryWrapper();
            qw.eq("t_eduId",edu.getEduId());
            qw.isNotNull("t_examinTeacher");
            Integer examinTopicNum = topicMapper.selectCount(qw);
            ats.setExaminTopicNum(examinTopicNum); //设置当前专业审核题目总数
            //查询审核通过题目数量
            QueryWrapper qwPass = new QueryWrapper();
            qwPass.eq("t_eduId",edu.getEduId());
            qwPass.isNotNull("t_examinTeacher");
            qwPass.in("t_status",2,3);
            Integer examinTopicPassingNum = topicMapper.selectCount(qwPass);
            ats.setExaminTopicPassingNum(examinTopicPassingNum); //设置当前专业审核通过题目数
            //计算通过率
            if(examinTopicNum!=0){
                double f1 = new BigDecimal((float) examinTopicPassingNum / examinTopicNum).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                ats.setExaminTopicPassingRate(f1*100);
            }else {
                ats.setExaminTopicPassingRate(0.00);
            }
            atsList.add(ats);
    }
        return atsList;
    }

    @Override
    public List<AssignTopicSituation> getAppointEducationExaminTopicDetails(Integer id) {
        List<AssignTopicSituation> ats = new ArrayList<>();
        AssignTopicSituation teacher;
        //获取出指定专业 所有审题老师id和审题数目
        List<AssignTopicSituation> teachers =  topicMapper.getExaminteacherAppointEducation(id);
        for(AssignTopicSituation a:teachers){
            teacher = new AssignTopicSituation();
            //设置审题教师id
            teacher.setTeacherId(a.getTeacherId());
            //设置审题教师名称
            QueryWrapper qwTea = new QueryWrapper();
            qwTea.eq("examintea_id",a.getTeacherId());
            teacher.setTeacherName(examinteacherMapper.selectOne(qwTea).getExaminteaName());
            //设置审题教师审题数目
            Integer  examTopNumByExamTea =a.getExaminTopicNumByExaminTea();
            teacher.setExaminTopicNumByExaminTea(examTopNumByExamTea);
            //设置审核通过题目数量
            QueryWrapper qwPassCount = new QueryWrapper();
            qwPassCount.eq("t_eduId",id);
            qwPassCount.eq("t_examinTeacher",a.getTeacherId());
            qwPassCount.in("t_status",2,3);
            Integer examTopPassNumByExamTea =topicMapper.selectCount(qwPassCount);
            teacher.setExaminTopicPassingNumByExaminTea(examTopPassNumByExamTea);
            //设置通过率
            double f1 = new BigDecimal((float)examTopPassNumByExamTea/examTopNumByExamTea).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            teacher.setExaminTopicPassingRateByExaminTea(f1*100);
            ats.add(teacher);
        }
        return ats;
    }

    @Override
    public AssignTopicSituation getExaminTopicDetails(Integer id) {
        List<AssignTopicSituation> ats = new ArrayList<>();
        AssignTopicSituation teacher ;
        //获取出指定专业题目的 所有老师id
        List<AssignTopicSituation> teachers = topicMapper.getExaminteacherAppointEducation(id);
        StringBuffer xsb = new StringBuffer("[");
        StringBuffer ysb = new StringBuffer("[");
        for (AssignTopicSituation a : teachers) {
            teacher = new AssignTopicSituation();
            //拼接x轴数据
            QueryWrapper qwTea = new QueryWrapper();
            qwTea.eq("examintea_id", a.getTeacherId());
            xsb.append("\""+examinteacherMapper.selectOne(qwTea).getExaminteaName()+"\",");
            //获取审题教师出题数目
            Integer examTopNumByExamTea = a.getExaminTopicNumByExaminTea();
            //获取过审题目数量
            QueryWrapper qwPassCount = new QueryWrapper();
            qwPassCount.eq("t_eduId", id);
            qwPassCount.eq("t_examinTeacher", a.getTeacherId());
            qwPassCount.in("t_status",2,3);

            Integer examTopPassNumByExamTea = topicMapper.selectCount(qwPassCount);
            //计算通过率
            double f1 = new BigDecimal((float) examTopPassNumByExamTea / examTopNumByExamTea).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            //拼接y轴数据
            ysb.append(f1*100+",");
        }
        xsb.append("]");
        ysb.append("]");
        //去除最后一个逗号
        if(teachers.size()!=0) {
            xsb.delete(xsb.length() - 2, xsb.length() - 1);
            ysb.delete(ysb.length() - 2, ysb.length() - 1);
        }
        //存储拼接好的数据
        AssignTopicSituation xyData = new AssignTopicSituation();
        xyData.setxAxisData(xsb.toString());
        xyData.setyAxisData(ysb.toString());
        return xyData;
    }
}

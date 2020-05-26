package com.bysj.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bysj.common.exception.BizException;
import com.bysj.sys.entity.*;
import com.bysj.sys.mapper.*;
import com.bysj.sys.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private StudentEducationMapper studentEducationMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private ExaminteacherMapper examinteacherMapper;
    @Autowired
    private TeacherCollegeMapper teacherCollegeMapper;
//--------------------------------导师出题-----------------------------------------------
    @Override
    public List<MyTopic> getAssignTopics(String teaId,String tTitle) {
        //按指导老师id查询题目题目列表
        List<MyTopic> topics = topicMapper.getAssignTopics(teaId,tTitle);
        List<MyTopic> topicList = new ArrayList<>();
        for(MyTopic topic : topics){
            //根据查出题目的专业id,查出专业名称
            //存储题目所属的专业名称
            QueryWrapper qwEdu = new QueryWrapper();
            qwEdu.eq("edu_id",topic.gettEduid());
            String eduName = educationMapper.selectOne(qwEdu).getEduName();
            topic.settEducation(eduName);
            //存储题目指导老师名称
            QueryWrapper qwTea = new QueryWrapper();
            qwTea.eq("tea_id",teaId);
            String teaName = teacherMapper.selectOne(qwTea).getTeaName();
            topic.settTeacherName(teaName);
            //存储题目审题老师名称
            if(topic.gettExaminteacher()!=null){
            QueryWrapper qwExTea = new QueryWrapper();
            qwExTea.eq("examintea_id",topic.gettExaminteacher());
            String examinTea = examinteacherMapper.selectOne(qwExTea).getExaminteaName();
            topic.settExaminTeacherName(examinTea);
            }
            topicList.add(topic);
        }
        return topicList;
    }

    @Override
    @Transactional
    public Integer assignTopicAdd(Topic topic) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //1.存储出题老师id
        topic.settMaketeacher(user.getUserId());
        //查出题目表 题目编号字段最大值
        String tno = topicMapper.getMaxTno();
        //判断是否为空
        if(tno!=null) {
            //题目的编号加 1
            DecimalFormat decimalFormat = new DecimalFormat("000000");
            String code = tno;
            String codenew = code.substring(1, code.length());
            int i = Integer.parseInt(codenew) + 1;
            String k = decimalFormat.format(i);
            //重新组成题目编号格式
            StringBuffer k2 = new StringBuffer().append("T").append(k);
            //将StringBuffer转String
            String tnonew = new String(k2);
            //2.存储课题编号
            topic.settNo(tnonew);
            //3.存储课题状态 默认 1 即待审核状态
            topic.settStatus(1);
            Integer index = topicMapper.insert(topic);
            return index;
        }else{
            //2.存储课题编号
            topic.settNo("T000001");
            //3.存储课题状态 默认 1 即待审核状态
            topic.settStatus(1);
            Integer index = topicMapper.insert(topic);
            return index;
        }
    }

    @Override
    public Topic getAssignTopicByTno(String tno) {
        QueryWrapper qwTopic = new QueryWrapper();
        qwTopic.eq("t_no",tno);
        Topic topic = topicMapper.selectOne(qwTopic);
        return topic;
    }

    @Override
    @Transactional
    public Integer assignTopicUpdate(Topic topic) {
        QueryWrapper qwTopicUpdate = new QueryWrapper();
        qwTopicUpdate.eq("t_no",topic.gettNo());
        //判断题目是否审核通过，通过则不能更改
        Topic topUpdate =topicMapper.selectOne(qwTopicUpdate);
        if(topUpdate.gettStatus()==2||topUpdate.gettStatus()==3){
            return -1;
        }
        topic.settStatus(1);
        Integer index = topicMapper.update(topic,qwTopicUpdate);
        return index;
    }

    @Override
    @Transactional
    public Integer assignTopicDelete(String tno) {
        QueryWrapper qwTopicDele = new QueryWrapper();
        qwTopicDele.eq("t_no",tno);
        //判断题目是否为学生自拟，若是学生拟题则设置状态，不能删除，删除绝对权在学生
        Topic topic = topicMapper.selectOne(qwTopicDele);
        //判断题目是否审核通过，通过则不能删除
        if(topic.gettStatus()==2||topic.gettStatus()==3){
            return -1;
        }
        if(topic.gettSource().equals("学生自拟")){
          topic.settStatus(0);
         return topicMapper.update(topic,qwTopicDele);
        }
        Integer index = topicMapper.delete(qwTopicDele);
        return index;
    }

    @Override
    @Transactional
    public Integer assignTopicDeleteBatch(List<String> ids) throws  BizException{
        QueryWrapper qwTopicDele = new QueryWrapper();
        qwTopicDele.in("t_no",ids);
        //判断题目是否为学生自拟，若是学生拟题则设置状态，不能删除，删除绝对权在学生
       List<Topic> topics = topicMapper.selectList(qwTopicDele);
       Integer index = 0;
       for(Topic topic: topics){
           if(topic.gettStatus()==2||topic.gettStatus()==3){
               throw new BizException("有题目已审核通过，操作失败");
           }
           if(topic.gettSource().equals("学生自拟")){
               QueryWrapper qwUp = new QueryWrapper();
               qwUp.eq("t_no",topic.gettNo());
               topic.settStatus(0);
               index += topicMapper.update(topic,qwUp);
           }else {
               QueryWrapper qwDe = new QueryWrapper();
               qwDe.eq("t_no",topic.gettNo());
               index += topicMapper.delete(qwDe);
           }
       }
        return index;
    }

    @Override
    public List<Teacher> getTeacherNameAndIdByStudentId() {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Teacher> teacher = teacherMapper.getTeacherNameAndIdByStudentId(user.getUserId());
        return teacher;
    }
//--------------------------------导师审题-------------------------------------
    @Override
    public List<MyTopic> getExaminTopicList(String teaId, String tTitle) {
        //按指导老师id查询题目列表
        List<MyTopic> examinTopics = topicMapper.getExaminTopicList(teaId,tTitle);
        //根据查出题目的专业id,查出专业名称
        List<MyTopic> topicList = new ArrayList<>();
        for(MyTopic topic : examinTopics){
            //存储题目所属的专业名称
            QueryWrapper qwEdu = new QueryWrapper();
            qwEdu.eq("edu_id",topic.gettEduid());
            String eduName = educationMapper.selectOne(qwEdu).getEduName();
            topic.settEducation(eduName);
            //存储题目指导老师名称
            QueryWrapper qwTea = new QueryWrapper();
            qwTea.eq("tea_id",teaId);
            String teaName = teacherMapper.selectOne(qwTea).getTeaName();
            topic.settTeacherName(teaName);
            //存储题目选题学生名称
            QueryWrapper qwStudent = new QueryWrapper();
            qwStudent.eq("stu_id",topic.gettChoosestudent());
            String stuName = studentMapper.selectOne(qwStudent).getStuName();
            topic.settChooseStudentName(stuName);
            topicList.add(topic);
        }
        return topicList;
    }

    @Override
    public User_Student getStudentDataById(String id) {
        User_Student us = new User_Student();
        //查询用户表获取用户账号状态（status）、密码
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        User user = userMapper.selectOne(qwUser);
        us.setStuId(id);
        //查询学生表，获取姓名、邮箱、手机号
        QueryWrapper qwStudent = new QueryWrapper();
        qwStudent.eq("stu_id",id);
        Student student = studentMapper.selectOne(qwStudent);
        us.setStuEmail(student.getStuEmail());
        us.setStuTelphone(student.getStuTelphone());
        us.setStuName(student.getStuName());
        //查询班级表获取学生班级
        QueryWrapper qwClass= new QueryWrapper();
        qwClass.eq("stu_class",student.getStuClass());
        us.setStuClassName(classMapper.selectOne(qwClass).getClassName());
        //查询学生-专业关联表获取学生专业Id 再去专业表查专业名称
        StudentEducation se = studentEducationMapper.selectOne(qwStudent);
        QueryWrapper qwEduName = new QueryWrapper();
        qwEduName.eq("edu_id",se.getEduId());
        us.setStuEducation(educationMapper.selectOne(qwEduName).getEduName());
        //根据学院id查学院名称
        QueryWrapper qwColl = new QueryWrapper();
        qwColl.eq("coll_id",educationMapper.selectOne(qwEduName).getCollId());
        us.setStuCollege(collegeMapper.selectOne(qwColl).getCollName());
        return us;
    }

    @Override
    @Transactional
    public Integer agreeAssignTopicByStudent(String id) {
        //根据题目编号 查出学生题目信息
        QueryWrapper qwTopic = new QueryWrapper();
        qwTopic.eq("t_no",id);
        Topic topic = topicMapper.selectOne(qwTopic);
        topic.settStatus(1);
        //更新题目状态
        Integer index= topicMapper.update(topic,qwTopic);
        return index;
    }

    @Override
    @Transactional
    public Integer disagreeAssignTopicByStudent(String id) {
        //根据题目编号 查出学生题目信息
        QueryWrapper qwTopic = new QueryWrapper();
        qwTopic.eq("t_no",id);
        Topic topic = topicMapper.selectOne(qwTopic);
        topic.settStatus(-2);
        //更新题目状态
        Integer index= topicMapper.update(topic,qwTopic);
        return index;
    }

    @Override
    public List<MyTopic> getStudentChooseTopic() {
        //存储学生选题信息
        List<MyTopic> myTopics = new ArrayList<>();
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //查询已选题目的学生选题信息  条件是 1.导师id 2.状态为3
        QueryWrapper qwChoose = new QueryWrapper();
        qwChoose.eq("t_makeTeacher",user.getUserId());
        qwChoose.eq("t_status",3);
        List<Topic> topics = topicMapper.selectList(qwChoose);
        //遍历存储
        for(Topic t : topics){
            MyTopic myTopic = new MyTopic();
            myTopic.settNo(t.gettNo()); //设置题目编号
            myTopic.settTitle(t.gettTitle());//设置题目标题
            myTopic.settChoosestudent(t.gettChoosestudent());//设置选题学生id
            //根据选题学生id查询学生姓名
            QueryWrapper qwStu  = new QueryWrapper();
            qwStu.eq("stu_id",t.gettChoosestudent());
            myTopic.settChooseStudentName(studentMapper.selectOne(qwStu).getStuName());//设置选题学生姓名
            myTopic.settExaminteacher(t.gettExaminteacher());//设置审题老师ID
            //根据审题老师id查询审题老师姓名
            QueryWrapper qwExaTea = new QueryWrapper();
            qwExaTea.eq("examintea_id",t.gettExaminteacher());
            //设置审题老师姓名
            myTopic.settExaminTeacherName( examinteacherMapper.selectOne(qwExaTea).getExaminteaName());
            myTopics.add(myTopic);
        }
        return myTopics;
    }

    @Override
    public User_Teacher getExaminTeacherData(String id) {
        User_Teacher ut = new User_Teacher();
        //存储审核老师工号
        ut.setTeaId(id);
        //存储审核老师名称
        QueryWrapper qwExaminTea = new QueryWrapper();
        qwExaminTea.eq("examintea_id",id);
        Examinteacher et = examinteacherMapper.selectOne(qwExaminTea);
        ut.setTeaName(et.getExaminteaName());
        //存储审核老师职称
        ut.setTeaEducation(et.getExaminteaEducation());
        //存储审核老师电话和邮箱
        ut.setTeaTelphone(et.getExaminteaTelphone());
        ut.setTeaEmail(et.getExaminteaEmail());
        //存储审核老师学院名称
        College college = examinteacherMapper.getCollegeIdAndNameByExaminId(id);
        ut.setTeaCollege(college.getCollName());
        return  ut;
    }

    @Override
    public User_Teacher getTeacherData() {
        //存储指导老师个人信息
        User_Teacher ut = new User_Teacher();

        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //查询指导老师个人信息
        QueryWrapper qwTea = new QueryWrapper();
        qwTea.eq("tea_id",user.getUserId());
        Teacher teacher = teacherMapper.selectOne(qwTea);
        //设置导师工号
        ut.setTeaId(teacher.getTeaId());
        //设置导师姓名
        ut.setTeaName(teacher.getTeaName());
        //设置指导老师职称
        ut.setTeaEducation(teacher.getTeaEducation());
        //设置指导老师学校
        ut.setTeaSchool(teacher.getTeaSchool());
        //设置指导老师学院
        //1.根据导师id查询学院id
        QueryWrapper qwTeaColl = new QueryWrapper();
        qwTeaColl.eq("tea_id",user.getUserId());
        Integer collId = teacherCollegeMapper.selectOne(qwTeaColl).getCollId();
        //2.根据学院Id查询学院名称
        QueryWrapper qwColl = new QueryWrapper();
        qwColl.eq("coll_id",collId);
        ut.setTeaCollege(collegeMapper.selectOne(qwColl).getCollName());
        //设置导师队伍已选题人数
        QueryWrapper qwTopCount = new QueryWrapper();
        qwTopCount.eq("t_makeTeacher",user.getUserId());
        qwTopCount.eq("t_status",3);
        ut.setTeaStudentNum(topicMapper.selectCount(qwTopCount));
        return ut;
    }

    @Override
    @Transactional
    public Integer deleteStudentChooseTopic(String id) {
        Integer index=0;
        QueryWrapper qwTopic = new QueryWrapper();
        qwTopic.eq("t_no",id);
        Topic t = topicMapper.selectOne(qwTopic);
        //教师自拟题目，撤回时将学生选题字段设为null,状态为 2
        if(t.gettSource().equals("教师自拟")){
            UpdateWrapper uw = new UpdateWrapper();
            uw.set("t_chooseStudent",null);
            uw.set("t_status",2);
            uw.eq("t_no",id);
            index= topicMapper.update(t,uw);
        }else {          //若是学生自拟题目，撤回即为导师拒绝状态
            t.settStatus(-2);
            index= topicMapper.update(t,qwTopic);
        }

        return index;
    }

    @Override
    public List<MyTopic> getExaminStudentChooseTopicList() {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        List<MyTopic> myTopics = new ArrayList<>();
        MyTopic mt ;
        //查询学生选题列表 用于审核
        QueryWrapper qwCheck = new QueryWrapper();
        qwCheck.eq("t_makeTeacher",user.getUserId());
        qwCheck.isNotNull("t_chooseStudent");

        qwCheck.eq("t_status",2);
        List<Topic> topic =topicMapper.selectList(qwCheck);
        for(Topic t : topic){
            mt = new MyTopic();
            //设置课题编号
            mt.settNo(t.gettNo());
            //设置课题名称
            mt.settTitle(t.gettTitle());
            //设置学生学号
            mt.settChoosestudent(t.gettChoosestudent());
            //设置学生名称
            //1.根据学生学号查询学生名称
            QueryWrapper qwStu = new QueryWrapper();
            qwStu.eq("stu_id",t.gettChoosestudent());
            mt.settChooseStudentName(studentMapper.selectOne(qwStu).getStuName());
            myTopics.add(mt);
        }
        return myTopics;
    }

    @Override
    @Transactional
    public Integer chooseTopicByStudentAgree(String id) {
        //查询题目信息
        QueryWrapper qwTopic = new QueryWrapper();
        qwTopic.eq("t_no",id);
        Topic topic = topicMapper.selectOne(qwTopic);
        //修改题目状态  同意改为 3
        topic.settStatus(3);
        Integer index = topicMapper.update(topic,qwTopic);
        return index;
    }

    @Override
    @Transactional
    public Integer chooseTopicByStudentDisagree(String id) {
        //修改题目状态  拒绝则清空选题学生id
        Integer index = topicMapper.chooseTopicByStudentDisagree(id);
        return index;
    }

    @Override
    public College getCollegeByTeacherId() {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据指导老师id获取学院id
        QueryWrapper qw = new QueryWrapper();
        qw.eq("tea_id",user.getUserId());
        Integer collId = teacherCollegeMapper.selectOne(qw).getCollId();
        //根据学院id获取学院信息
        QueryWrapper qwColl = new QueryWrapper();
        qwColl.eq("coll_id",collId);
        return collegeMapper.selectOne(qwColl);
    }
}

package com.bysj.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bysj.sys.entity.*;
import com.bysj.sys.mapper.*;
import com.bysj.sys.service.IAdminService;
import com.bysj.sys.service.IStudentService;
import net.sf.ehcache.transaction.xa.commands.StorePutCommand;
import org.apache.ibatis.jdbc.Null;
import org.apache.shiro.SecurityUtils;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherCollegeMapper teacherCollegeMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private StudentEducationMapper studentEducationMapper;
    @Autowired
    private ExaminteacherMapper examinteacherMapper;

    @Override
    @Transactional
    public Integer assignTopicAdd(Topic topic) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        //判断学生是否已选题
        QueryWrapper qwSt = new QueryWrapper();
        qwSt.eq("t_choosestudent",user.getUserId());
        Topic stCheck = topicMapper.selectOne(qwSt);
        if(stCheck!=null){
            return -1;
        }

        //查出题目表 题目编号字段最大值
        String tno = topicMapper.getMaxTno();
        //题目的编号加 1
        DecimalFormat decimalFormat=new DecimalFormat("000000");
        String code=tno;
        String codenew=code.substring(1, code.length());
        System.out.println(codenew);
        int i=Integer.parseInt(codenew)+1;
        String k=decimalFormat.format(i);
        //重新组成题目编号格式
        StringBuffer k2 = new StringBuffer().append("T").append(k);
        //将StringBuffer转String
        String tnonew = new String(k2);
        //1.存储课题编号
        topic.settNo(tnonew);
        //2.存储课题状态 默认 1 即待审核状态
        topic.settStatus(0);
        //3.存储学生学号
        topic.settChoosestudent(user.getUserId());
        Integer index = topicMapper.insert(topic);
        return index;
    }

    @Override
    public List<MyTopic> getAssignTopic() {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据学生'学号'和'学生自拟'   去题目表查询题目信息，获取题目编号
        //1.结果为null,说明学生没有题目或者没有自拟题目 则不显示
        //2.有结果，说明此学生题目为自拟

        QueryWrapper qwTopic = new QueryWrapper();
        qwTopic.eq("t_choosestudent",user.getUserId());
        qwTopic.eq("t_source","学生自拟");
        String tNo= topicMapper.selectOne(qwTopic).gettNo();
        if(tNo==null){
            return null;
        }
        //获取题目编号,查询题目信息
        List<MyTopic> topics = topicMapper.getAssignTopicByTopicNo(tNo);
        //存储题目所属的专业名称
        QueryWrapper qwEdu = new QueryWrapper();
        qwEdu.eq("edu_id",topics.get(0).gettEduid());
        String eduName = educationMapper.selectOne(qwEdu).getEduName();
        topics.get(0).settEducation(eduName);
        //存储题目指导老师名称
        QueryWrapper qwTea = new QueryWrapper();
        qwTea.eq("tea_id",topics.get(0).gettMaketeacher());
        String teaName = teacherMapper.selectOne(qwTea).getTeaName();
        topics.get(0).settTeacherName(teaName);
        return topics;
    }

    @Override
    @Transactional
    public Integer assignTopicByStudentDelete(String id) {
        //删除课题表数据
        QueryWrapper qwTopic = new QueryWrapper();
        qwTopic.eq("t_no",id);
        //判断学生题目是否已被老师同意，若同意则不能删除
        Topic topicCheck = topicMapper.selectOne(qwTopic);
        if(topicCheck.gettStatus()==1){
            return -1;
        }else if(topicCheck.gettStatus()==2||topicCheck.gettStatus()==3){  //过审题目不能删除
            return -2;
        }
        Integer index = topicMapper.delete(qwTopic);

        return index;
    }

    @Override
    public Integer assignTopicByStudentUpdate(Topic topic) {
        //判断学生题目是否已被老师同意，若同意则不能修改
        QueryWrapper qwCheck = new QueryWrapper();
        qwCheck.eq("t_no",topic.gettNo());
        Topic topicCheck = topicMapper.selectOne(qwCheck);
        if(topicCheck.gettStatus()==1){
            return -1;
        }else if(topicCheck.gettStatus()==2||topicCheck.gettStatus()==3){  //过审题目不能更改
            return -2;
        }
        QueryWrapper qwTopicUpdate = new QueryWrapper();
        qwTopicUpdate.eq("t_no",topic.gettNo());
        //设题目状态为 0 学生自拟题目
        topic.settStatus(0);
        Integer index = topicMapper.update(topic,qwTopicUpdate);
        return index;
    }

    @Override
    public User_Teacher getAssignTopicTeacherData(String id) {
        User_Teacher ut = new User_Teacher();
        //查询用户表获取导师工号
        QueryWrapper qwUser = new QueryWrapper();
        qwUser.eq("user_id",id);
        User user = userMapper.selectOne(qwUser);
        ut.setTeaId(id);
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
    public User_Student getStudentData() {
        User_Student us = new User_Student();
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //查询学生个人信息
        QueryWrapper qwStu = new QueryWrapper();
        qwStu.eq("stu_id",user.getUserId());
        Student student = studentMapper.selectOne(qwStu);
        us.setStuId(student.getStuId()); //设置学生学号
        us.setStuName(student.getStuName()); //设置学生姓名
        us.setStuSchool(student.getStuSchool()); //设置学生学校

        QueryWrapper qwClass = new QueryWrapper(); //设置学生班级
        qwClass.eq("stu_class",student.getStuClass());
        us.setStuClass(classMapper.selectOne(qwClass).getClassName());
        //设置学生学院，专业
        //1.获取专业id
        QueryWrapper qwEduColl = new QueryWrapper();
        qwEduColl.eq("stu_id",user.getUserId());
        Integer eduId = studentEducationMapper.selectOne(qwEduColl).getEduId();
        //2.查询专业名称
        QueryWrapper qwEdu = new QueryWrapper();
        qwEdu.eq("edu_id",eduId);
        Education edu = educationMapper.selectOne(qwEdu);
        us.setStuEducation(edu.getEduName());  //设置专业名称
        //3.获取学院id
        Integer collId = edu.getCollId();
        //4.查询学院名称
        QueryWrapper qwColl = new QueryWrapper();
        qwColl.eq("coll_id",collId);
        us.setStuCollege(collegeMapper.selectOne(qwColl).getCollName()); //设置学院名称
        return us;
    }

    @Override
    public MyTopic getStudentTopicData() {
        MyTopic mt = new MyTopic();
        // shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据学生学号查询题目信息
        QueryWrapper qwStu = new QueryWrapper();
        qwStu.eq("t_chooseStudent",user.getUserId()); //选题学生ID
        qwStu.in("t_status",0,1,2,3,-1,-2); //课题所有状态
        Topic topic = topicMapper.selectOne(qwStu);
        if(topic!=null){
            mt.settNo(topic.gettNo());//设置题目编号
            mt.settTitle(topic.gettTitle());//设置课题名称
            mt.settStatus(topic.gettStatus());//设置课题状态
            mt.settMaketeacher(topic.gettMaketeacher());//设置导师ID
            //根据导师id获取导师名称
            QueryWrapper tea = new QueryWrapper();
            tea.eq("tea_id",topic.gettMaketeacher());
            mt.settTeacherName(teacherMapper.selectOne(tea).getTeaName());//设置导师名称
            mt.settExaminteacher(topic.gettExaminteacher());//设置审核老师id
            //根据据审核老师id查询审核老师名称
            if(topic.gettExaminteacher()!=null){
                QueryWrapper qwExamtea = new QueryWrapper();
                qwExamtea.eq("examintea_id",topic.gettExaminteacher());
                mt.settExaminTeacherName(examinteacherMapper.selectOne(qwExamtea).getExaminteaName());
            }
            return mt;
        }
        return null;
    }

    @Override
    public List<MyTopic> getChooseTopicList(String tmakeTeacher) {
        // shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //获取学生所在专业id
        QueryWrapper qwStuEduId = new QueryWrapper();
        qwStuEduId.eq("stu_id",user.getUserId());
        Integer eduId = studentEducationMapper.selectOne(qwStuEduId).getEduId();
        //查询题目 1.课题所属专业和学生相同 2.课题选题学生字段为空 3.课题状态为 2
        List<MyTopic> myTopics = topicMapper.getTopicListForStudentsToChoose(eduId,tmakeTeacher);
        return myTopics;
    }

    @Override
    @Transactional
    public Integer studentChooseTopic(String id) {
        Integer index = 0;
        // shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //判断学生是否已经选题 1.若选题返回-1    2.未选题 题目表填入学生id
        QueryWrapper qwCheck = new QueryWrapper();
        qwCheck.eq("t_chooseStudent",user.getUserId());
        qwCheck.in("t_status",2,3);
        // 1.已经选题
        if(topicMapper.selectOne(qwCheck)!=null){
            return  -1;
        }
        //2.未选题
        //查询需要更新的题目信息
        QueryWrapper qwChoose = new QueryWrapper();
        qwChoose.eq("t_no",id);
        Topic topic = topicMapper.selectOne(qwChoose);
        //判断题目是否有人选
        if(topic.gettChoosestudent()!=null){
            return -2;
        }
        //若是学生有自拟题目，且未审核通过，需要清空该题目的选题学生字段
        QueryWrapper qwC = new QueryWrapper();
        qwC.eq("t_chooseStudent",user.getUserId());
        qwC.eq("t_source","学生自拟");
        Topic t= topicMapper.selectOne(qwC);
        if(t!=null){
            UpdateWrapper uw = new UpdateWrapper();
            uw.set("t_chooseStudent",null);
            uw.eq("t_no",t.gettNo());
            index += topicMapper.update(t,uw);
        }
        //学生选题字段添加学生学号
        topic.settChoosestudent(user.getUserId());

        //更新题目
       index+= topicMapper.update(topic,qwChoose);

       return index;
    }
}

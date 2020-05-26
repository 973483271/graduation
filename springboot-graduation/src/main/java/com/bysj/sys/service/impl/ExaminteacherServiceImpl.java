package com.bysj.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.sys.entity.*;
import com.bysj.sys.mapper.EducationMapper;
import com.bysj.sys.mapper.ExaminteacherMapper;
import com.bysj.sys.mapper.TeacherMapper;
import com.bysj.sys.mapper.TopicMapper;
import com.bysj.sys.service.IExaminteacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jack
 * @since 2020-02-06
 */
@Service
public class ExaminteacherServiceImpl extends ServiceImpl<ExaminteacherMapper, Examinteacher> implements IExaminteacherService {
    @Autowired
    private ExaminteacherMapper examinteacherMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private EducationMapper educationMapper;

    @Override
    public Integer getExamincationIdByExaminId(String id) {
        return examinteacherMapper.getExamincationIdByExaminId(id);
    }

    @Override
    public List<MyTopic> getExaminTopicsList() {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //查询审核老师所负责的专业id
        Integer eduId = examinteacherMapper.getExamincationIdByExaminId(user.getUserId());
        //获取与此审核老师负责同专业的审核老师id列表，升序排序
        List<String> examTeas= examinteacherMapper.getExaminteachersIdByEducationId(eduId);
        //获取本审核老师id 所在位置（用于判定该审核老师审核哪一部分题目）
        Integer examteaIndex = examTeas.indexOf(user.getUserId());
        //根据审核老师的编号位置查出属于他审核的题目列表
        //1.获取所有本专业且未审核 或者二审 的题目
        QueryWrapper qwTopicNo = new QueryWrapper();
        qwTopicNo.eq("t_eduId",eduId);
        qwTopicNo.eq("t_status",1);

        List<Topic> topics =  topicMapper.selectList(qwTopicNo);
        //2.一次获取题目编号求余，余数等于老师id所在位置 即为该老师要审核的题目
        List<MyTopic> topicList = new ArrayList<>();
        MyTopic mt =null;
        for(Topic t: topics){
            mt= new MyTopic();
            //获取题目编号求余结果
            String tno = t.gettNo();
            String code = tno;
            String codenew = code.substring(1, code.length());
            int i = Integer.parseInt(codenew);
            Integer k = i%examTeas.size(); //题目编号和负责该专业的审题老师总数求余
            if(k==examteaIndex){   //若相等 查询此题目信息
                QueryWrapper qwT = new QueryWrapper();
                qwT.eq("t_no",tno);
                Topic topic = topicMapper.selectOne(qwT);
                mt.settNo(topic.gettNo());
                mt.settTitle(topic.gettTitle());
                mt.settType(topic.gettType());
                mt.settSource(topic.gettSource());
                mt.settDifferent(topic.gettDifferent());
                mt.settMaketime(topic.gettMaketime());
                mt.settExamintime(topic.gettExamintime());
                //设置指导教师名称
                QueryWrapper qwTea = new QueryWrapper();
                qwTea.eq("tea_id",topic.gettMaketeacher());
                mt.settTeacherName(teacherMapper.selectOne(qwTea).getTeaName());
                //设置指导老师id
                mt.settMaketeacher(topic.gettMaketeacher());
                //设置课题所属组织名称
                QueryWrapper qwEdu = new QueryWrapper();
                qwEdu.eq("edu_id",topic.gettEduid());
                mt.settEducation(educationMapper.selectOne(qwEdu).getEduName());
                topicList.add(mt);
            }
        }
        return topicList;
    }

    @Override
    @Transactional
    public Integer examinTopicPass(String id) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据题目编号查询题目信息
        QueryWrapper qwTop = new QueryWrapper();
        qwTop.eq("t_no",id);
        Topic topic = topicMapper.selectOne(qwTop);
        //更新题目信息
        //1.添加审题老师ID
        topic.settExaminteacher(user.getUserId());
        //2.添加审题时间
        Date date = new Date();
        topic.settExamintime(date);
        //3.更改题目状态
            //a.判断题目是否为学生自拟，是则设为已选 状态为 3
            if(topic.gettChoosestudent()!=null&&topic.gettChoosestudent()!=""){
                topic.settStatus(3);
            }else {
            topic.settStatus(2);
        }
        //3.更新数据
        Integer index = topicMapper.update(topic,qwTop);
        return index;
    }

    @Override
    public Integer examinTopicNotPass(String id) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据题目编号查询题目信息
        QueryWrapper qwTop = new QueryWrapper();
        qwTop.eq("t_no",id);
        Topic topic = topicMapper.selectOne(qwTop);
        //更新题目信息
        //1.添加审题老师ID
        topic.settExaminteacher(user.getUserId());
        //2.添加审题时间
        Date date = new Date();
        topic.settExamintime(date);
        //3.更改题目状态
        topic.settStatus(-1);
        //3.更新数据
        Integer index = topicMapper.update(topic,qwTop);
        return index;
    }

    @Override
    public List<MyTopic> getExaminSituation(String inputData) {
        List<MyTopic> topicList = new ArrayList<>();
        MyTopic mt =null;
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //查询所有已审核题目信息  包括通过和不通过
         QueryWrapper qwTopic = new QueryWrapper();
         qwTopic.eq("t_examinTeacher",user.getUserId());
         if(inputData.equals("通过")){
             qwTopic.eq("t_status",2);
         }else if(inputData.equals("不通过")){
             qwTopic.eq("t_status",-1);
         }else{
             qwTopic.notIn("t_status",1);
         }
         List<Topic> topics = topicMapper.selectList(qwTopic);
         for(Topic topic : topics){
             mt = new MyTopic();
             mt.settNo(topic.gettNo());
             mt.settTitle(topic.gettTitle());
             mt.settType(topic.gettType());
             mt.settSource(topic.gettSource());
             mt.settDifferent(topic.gettDifferent());
             mt.settMaketime(topic.gettMaketime());
             mt.settExamintime(topic.gettExamintime());
             mt.settStatus(topic.gettStatus());
             //设置指导教师名称
             QueryWrapper qwTea = new QueryWrapper();
             qwTea.eq("tea_id",topic.gettMaketeacher());
             mt.settTeacherName(teacherMapper.selectOne(qwTea).getTeaName());
             //设置指导老师id
             mt.settMaketeacher(topic.gettMaketeacher());
             //设置课题所属组织名称
             QueryWrapper qwEdu = new QueryWrapper();
             qwEdu.eq("edu_id",topic.gettEduid());
             mt.settEducation(educationMapper.selectOne(qwEdu).getEduName());
             topicList.add(mt);
         }
        return topicList;
    }

    @Override
    public User_Examinteacher getExaminTeacherData() {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //查询身体教师个人信息
        User_Examinteacher ue = examinteacherMapper.getExaminTeacherData(user.getUserId());
        //查询审题老师审题数量
        QueryWrapper qwExm = new QueryWrapper();
        qwExm.eq("t_examinTeacher", user.getUserId());
        qwExm.in("t_status", -1, 2, 3);
        Integer totalCount = topicMapper.selectCount(qwExm);
        //1.设置审题数量
        ue.setExaminteaExamTopicNum(totalCount);
        //查询审核通过题目数量
        QueryWrapper qwNotPass = new QueryWrapper();
        qwNotPass.eq("t_examinTeacher", user.getUserId());
        qwNotPass.in("t_status", 2, 3);
        Integer passCount = topicMapper.selectCount(qwNotPass);
        //算出通过率
        double f1 = 0.00;
        if (totalCount != 0) {
            f1 = new BigDecimal((float) passCount / totalCount).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            //2.设置审核通过率
            ue.setExaminteaExamTopicPassRate(f1);
        }
        return ue;
    }
}

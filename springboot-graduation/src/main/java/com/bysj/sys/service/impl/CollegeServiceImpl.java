package com.bysj.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.sys.entity.College;
import com.bysj.sys.entity.College_Education;
import com.bysj.sys.entity.Education;
import com.bysj.sys.mapper.CollegeMapper;
import com.bysj.sys.mapper.EducationMapper;
import com.bysj.sys.service.ICollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jack
 * @since 2020-02-04
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements ICollegeService {

    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Override
    public List<College_Education> getCollegeAndEducation() {
        //创建存储学院信息和专业信息对象
        List<College_Education> ceList =new ArrayList<>();
        College_Education ce = null;
        //查询学院信息
        QueryWrapper qw = new QueryWrapper();
        List<College> colleges = collegeMapper.selectList(qw);
        for(College college:colleges){
            ce=new College_Education();
            //获取学院id
            Integer collegeId = college.getCollId();
            //查询学院对应的专业
            QueryWrapper qwEdu = new QueryWrapper();
            qwEdu.eq("coll_id",collegeId);
            List<Education> educations = educationMapper.selectList(qwEdu);
            //存放查出的学院信息+专业信息
            ce.setCollId(collegeId);
            ce.setCollName(college.getCollName());
            ce.setEducations(educations);
            ceList.add(ce);
        }
        System.out.println("xixi"+ceList);
        return ceList;
    }

    @Override
    public List<College> getCollege() {
        QueryWrapper qwCollege = new QueryWrapper();
        return collegeMapper.selectList(qwCollege);
    }
}

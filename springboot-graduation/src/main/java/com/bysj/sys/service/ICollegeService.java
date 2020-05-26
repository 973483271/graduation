package com.bysj.sys.service;

import com.bysj.sys.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bysj.sys.entity.College_Education;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jack
 * @since 2020-02-04
 */
public interface ICollegeService extends IService<College> {
    /**
     * 获取学院（部）和专业信息
     */
    List<College_Education> getCollegeAndEducation();
    /**
     * 获取学院（部）信息
     */
    List<College> getCollege();
}

package com.bysj.sys.mapper;

import com.bysj.sys.entity.AssignTopicSituation;
import com.bysj.sys.entity.Education;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2020-02-04
 */
public interface EducationMapper extends BaseMapper<Education> {
    //获取所有专业信息及其对应的学院名称
    List<AssignTopicSituation> getAssignTopicSituation(@Param("tTitle") String tTitle);
}

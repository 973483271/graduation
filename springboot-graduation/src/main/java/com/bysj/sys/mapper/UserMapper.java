package com.bysj.sys.mapper;

import com.bysj.sys.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2020-01-16
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据id查询用户权限名称的集合
     * @param id
     * @return
     */
    Set<String> selectUserPermissionNameSet(String id);
    /**
     * 根据用户id查询用户菜单
     * @param id
     * @return
     */
    List<Menu> selectMenuList(String id);
    /**
     * 获取各阶段定时任务信息
     */
    List<Quartz> getProcessControlData();
    /**
     * 设置出题 审题 选题各阶段定时任务信息
     */
    Integer setProcessControlData(Quartz quartz);

    /**
     * 设置访问权限 ----出题 审题 选题
     */
    Integer updateResource(@Param("id") Integer id, @Param("permission") String permission);

}

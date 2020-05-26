package com.bysj.sys.service;

import com.bysj.common.model.TreeNode;
import com.bysj.sys.entity.Admin;
import com.bysj.sys.entity.Quartz;
import com.bysj.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bysj.sys.entity.User_Role;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jack
 * @since 2020-01-16
 */
public interface IUserService extends IService<User> {
    /**
     * 根据用户名查询用户角色名称、个人信息 （rolename,userid）
     */
       User_Role selectUserRoleByUserType(String username);
    /**
     * 根据用户名获取用户的菜单树
     * @param id
     * @return
     */
    List<TreeNode> getMenuTreeByUserId(String id);
    /**
     * 更改用户个人信息（姓名，电话，电子邮箱）
     */
    Integer updateUserData(String adminName,String adminTelphone,String adminEmail);
    /**
     * 根据id查询个人信息（包括管理员，老师，学生）
     * @return
     */
    Object getUsersDataById(String id);
    /**
     * 根据id修改用户密码
     */
    Integer updateUserPassword(String id,String comfirmPassword);
    /**
     * 验证原始密码是否正确
     */
    User confirmOldPassword(String oldPassword);
    /**
     * 获取出题 审题 选题各阶段定时任务信息
     */
    List<Quartz> getProcessControlData();
    /**
     * 设置出题 审题 选题各阶段定时任务信息
     */
    Integer setProcessControlData(Quartz quartz);
    /**
     * 根据任务名称获取剩余时间
     */
    Integer getRemainingTime(String taskName);
}

package com.bysj.sys.entity;

/**
 * 2020/1/19.
 * 存放 角色名称、个人信息 （rolename,userid,username）
 * 用于系统主页面信息显示
 */
public class User_Role {

    //角色名称
    private String roleName;
    //用户id
    private String userId;
    //班级信息
    private Integer studentClass;
    //用户名字
    private String userName;

    public User_Role() {
    }

    public User_Role(String roleName, String userId, Integer studentClass, String userName) {
        this.roleName = roleName;
        this.userId = userId;
        this.studentClass = studentClass;
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User_Role{" +
                "roleName='" + roleName + '\'' +
                ", userId='" + userId + '\'' +
                ", studentClass=" + studentClass +
                ", userName='" + userName + '\'' +
                '}';
    }
}

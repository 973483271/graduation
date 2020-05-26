package com.bysj.sys.entity;

/**
 * 用于统一存放用户个人信息 包括（管理员，老师，学生）
 * 前台个人信息功能
 */
public class UsersData {
   private String usersId;
   private String usersName;
   private String usersEmail;
   private String usersTelphone;

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public String getUsersTelphone() {
        return usersTelphone;
    }

    public void setUsersTelphone(String usersTelphone) {
        this.usersTelphone = usersTelphone;
    }
}

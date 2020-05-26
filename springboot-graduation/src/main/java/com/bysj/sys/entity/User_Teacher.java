package com.bysj.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 用户表+教师表(包括审题老师)+教师学院关联表
 * 存放教师信息（工号，密码，姓名，学位，电话号码，电子邮箱，账号状态）
 */
public class User_Teacher extends Teacher{

    @Excel(name = "用户名", width = 25,orderNum = "0")
    private String teaId;

    @Excel(name = "姓名", width = 25,orderNum = "0")
    private String teaName;

    @Excel(name = "职称", width = 25,orderNum = "0")
    private String teaEducation;

    @Excel(name = "联系电话", width = 25,orderNum = "0")
    private String teaTelphone;

    @Excel(name = "邮箱", width = 25,orderNum = "0")
    private String teaEmail;

    @Excel(name = "学院(部)", width = 25,orderNum = "0")
    private String teaCollege;  //学院

    private Integer teaCollId; //学院id
    private Integer teaStudentNum;//指导老师学生人数
    private Integer usertype; //用户类型
    private Integer status;
    private String password;
    private String teaSchool;

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getTeaStudentNum() {
        return teaStudentNum;
    }

    public void setTeaStudentNum(Integer teaStudentNum) {
        this.teaStudentNum = teaStudentNum;
    }

    public Integer getTeaCollId() {
        return teaCollId;
    }

    public void setTeaCollId(Integer teaCollId) {
        this.teaCollId = teaCollId;
    }

    public String getTeaCollege() {
        return teaCollege;
    }

    public void setTeaCollege(String teaCollege) {
        this.teaCollege = teaCollege;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getTeaId() {
        return teaId;
    }

    @Override
    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    @Override
    public String getTeaName() {
        return teaName;
    }

    @Override
    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    @Override
    public String getTeaEducation() {
        return teaEducation;
    }

    @Override
    public void setTeaEducation(String teaEducation) {
        this.teaEducation = teaEducation;
    }

    @Override
    public String getTeaTelphone() {
        return teaTelphone;
    }

    @Override
    public void setTeaTelphone(String teaTelphone) {
        this.teaTelphone = teaTelphone;
    }

    @Override
    public String getTeaEmail() {
        return teaEmail;
    }

    @Override
    public void setTeaEmail(String teaEmail) {
        this.teaEmail = teaEmail;
    }

    @Override
    public String getTeaSchool() {
        return teaSchool;
    }

    @Override
    public void setTeaSchool(String teaSchool) {
        this.teaSchool = teaSchool;
    }

    @Override
    public String toString() {
        return "User_Teacher{" +
                "status=" + status +
                ", password='" + password + '\'' +
                ", teaId='" + teaId + '\'' +
                ", teaName='" + teaName + '\'' +
                ", teaEducation='" + teaEducation + '\'' +
                ", teaTelphone='" + teaTelphone + '\'' +
                ", teaEmail='" + teaEmail + '\'' +
                ", teaSchool='" + teaSchool + '\'' +
                ", teaCollege='" + teaCollege + '\'' +
                ", teaCollId=" + teaCollId +
                ", teaStudentNum=" + teaStudentNum +
                ", usertype=" + usertype +
                '}';
    }
}

package com.bysj.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 用户表+审题组教师表+教师学院关联表
 * 存放教师信息（工号，密码，姓名，学位，电话号码，电子邮箱，账号状态）
 */
public class User_Examinteacher extends Examinteacher {
    @Excel(name = "用户名", width = 25,orderNum = "0")
    private String examinteaId;

    @Excel(name = "姓名", width = 25,orderNum = "0")
    private String examinteaName;

    @Excel(name = "职称", width = 25,orderNum = "0")
    private String examinteaEducation;//职称

    @Excel(name = "联系电话", width = 25,orderNum = "0")
    private String examinteaTelphone;

    @Excel(name = "邮箱", width = 25,orderNum = "0")
    private String examinteaEmail;

    @Excel(name = "学院(部)", width = 25,orderNum = "0")
    private String teaCollege;  //学院

    @Excel(name = "负责专业", width = 25,orderNum = "0")
    private String examinteaResEducation; // 负责专业名称

    private Integer examinteaResEducationId;//负责专业Id
    private Integer examinteaExamTopicNum;//审题老师审题总数
    private double examinteaExamTopicPassRate;//审题老师审核题目通过率
    private Integer usertype; //用户类型
    private Integer status;
    private String password;
    private Integer teaCollId; //学院id
    private String examinteaSchool;
    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getExaminteaExamTopicNum() {
        return examinteaExamTopicNum;
    }

    public void setExaminteaExamTopicNum(Integer examinteaExamTopicNum) {
        this.examinteaExamTopicNum = examinteaExamTopicNum;
    }

    public double getExaminteaExamTopicPassRate() {
        return examinteaExamTopicPassRate;
    }

    public void setExaminteaExamTopicPassRate(double examinteaExamTopicPassRate) {
        this.examinteaExamTopicPassRate = examinteaExamTopicPassRate;
    }

    public Integer getExaminteaResEducationId() {
        return examinteaResEducationId;
    }

    public void setExaminteaResEducationId(Integer examinteaResEducationId) {
        this.examinteaResEducationId = examinteaResEducationId;
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
    public String getExaminteaId() {
        return examinteaId;
    }

    @Override
    public void setExaminteaId(String examinteaId) {
        this.examinteaId = examinteaId;
    }

    @Override
    public String getExaminteaName() {
        return examinteaName;
    }

    @Override
    public void setExaminteaName(String examinteaName) {
        this.examinteaName = examinteaName;
    }

    @Override
    public String getExaminteaEducation() {
        return examinteaEducation;
    }

    @Override
    public void setExaminteaEducation(String examinteaEducation) {
        this.examinteaEducation = examinteaEducation;
    }

    @Override
    public String getExaminteaTelphone() {
        return examinteaTelphone;
    }

    @Override
    public void setExaminteaTelphone(String examinteaTelphone) {
        this.examinteaTelphone = examinteaTelphone;
    }

    @Override
    public String getExaminteaEmail() {
        return examinteaEmail;
    }

    @Override
    public void setExaminteaEmail(String examinteaEmail) {
        this.examinteaEmail = examinteaEmail;
    }

    @Override
    public String getExaminteaSchool() {
        return examinteaSchool;
    }

    @Override
    public void setExaminteaSchool(String examinteaSchool) {
        this.examinteaSchool = examinteaSchool;
    }

    public String getTeaCollege() {
        return teaCollege;
    }

    public void setTeaCollege(String teaCollege) {
        this.teaCollege = teaCollege;
    }

    public Integer getTeaCollId() {
        return teaCollId;
    }

    public void setTeaCollId(Integer teaCollId) {
        this.teaCollId = teaCollId;
    }

    public String getExaminteaResEducation() {
        return examinteaResEducation;
    }

    public void setExaminteaResEducation(String examinteaResEducation) {
        this.examinteaResEducation = examinteaResEducation;
    }

    @Override
    public String toString() {
        return "User_Examinteacher{" +
                "status=" + status +
                ", password='" + password + '\'' +
                ", examinteaId='" + examinteaId + '\'' +
                ", examinteaName='" + examinteaName + '\'' +
                ", examinteaEducation='" + examinteaEducation + '\'' +
                ", examinteaTelphone='" + examinteaTelphone + '\'' +
                ", examinteaEmail='" + examinteaEmail + '\'' +
                ", examinteaSchool='" + examinteaSchool + '\'' +
                ", teaCollege='" + teaCollege + '\'' +
                ", teaCollId=" + teaCollId +
                ", examinteaResEducation='" + examinteaResEducation + '\'' +
                ", examinteaResEducationId=" + examinteaResEducationId +
                ", examinteaExamTopicNum=" + examinteaExamTopicNum +
                ", examinteaExamTopicPassRate=" + examinteaExamTopicPassRate +
                ", usertype=" + usertype +
                '}';
    }
}

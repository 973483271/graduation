package com.bysj.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import sun.security.util.Password;

/**
 * 用户表+学生表
 * 存放学生信息（学号，密码，姓名，专业，班级，电话号码，电子邮箱，账号状态）
 */
public class User_Student extends Student {
    @Excel(name = "用户名", width = 25,orderNum = "0")
    private String stuId;

    @Excel(name = "姓名", width = 25,orderNum = "0")
    private String stuName;

    @Excel(name = "联系电话", width = 25,orderNum = "0")
    private String stuTelphone;

    @Excel(name = "邮箱", width = 25,orderNum = "0")
    private String stuEmail;

    @Excel(name = "专业", width = 25,orderNum = "0")
    private String stuEducation; //专业名称

    @Excel(name = "学院(部)", width = 25,orderNum = "0")
    private String stuCollege;  //学院名称

    @Excel(name = "班级", width = 25,orderNum = "0")
    private Integer stuClassName; //班级名称


    private Integer stuEducationId; //专业id
    private Integer status;     //账号状态
    private String password;
    private Integer stuClass; //班级
    private String stuTopicTitle;//学生课题名称
    private Integer stuChooseTopicTotalNum; //选题学生总数
    private Integer stuHasChooseTopicNum;//已选题学生总数
    private Integer stuNotChooseTopicNum;//未选题学生总数
    private Integer usertype; //用户类型
    private String stuSchool;//学校

    @Override
    public String getStuSchool() {
        return stuSchool;
    }

    @Override
    public void setStuSchool(String stuSchool) {
        this.stuSchool = stuSchool;
    }

    public Integer getStuChooseTopicTotalNum() {
        return stuChooseTopicTotalNum;
    }

    public void setStuChooseTopicTotalNum(Integer stuChooseTopicTotalNum) {
        this.stuChooseTopicTotalNum = stuChooseTopicTotalNum;
    }

    public Integer getStuHasChooseTopicNum() {
        return stuHasChooseTopicNum;
    }

    public void setStuHasChooseTopicNum(Integer stuHasChooseTopicNum) {
        this.stuHasChooseTopicNum = stuHasChooseTopicNum;
    }

    public Integer getStuNotChooseTopicNum() {
        return stuNotChooseTopicNum;
    }

    public void setStuNotChooseTopicNum(Integer stuNotChooseTopicNum) {
        this.stuNotChooseTopicNum = stuNotChooseTopicNum;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getStuTopicTitle() {
        return stuTopicTitle;
    }

    public void setStuTopicTitle(String stuTopicTitle) {
        this.stuTopicTitle = stuTopicTitle;
    }

    public Integer getStuClassName() {
        return stuClassName;
    }

    public void setStuClassName(Integer stuClassName) {
        this.stuClassName = stuClassName;
    }

    public Integer getStuEducationId() {
        return stuEducationId;
    }

    public void setStuEducationId(Integer stuEducationId) {
        this.stuEducationId = stuEducationId;
    }

    public String getStuEducation() {
        return stuEducation;
    }

    public void setStuEducation(String stuEducation) {
        this.stuEducation = stuEducation;
    }

    public String getStuCollege() {
        return stuCollege;
    }

    public void setStuCollege(String stuCollege) {
        this.stuCollege = stuCollege;
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
    public String getStuId() {
        return stuId;
    }

    @Override
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Override
    public String getStuName() {
        return stuName;
    }

    @Override
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }


    @Override
    public Integer getStuClass() {
        return stuClass;
    }

    @Override
    public void setStuClass(Integer stuClass) {
        this.stuClass = stuClass;
    }

    @Override
    public String getStuTelphone() {
        return stuTelphone;
    }

    @Override
    public void setStuTelphone(String stuTelphone) {
        this.stuTelphone = stuTelphone;
    }

    @Override
    public String getStuEmail() {
        return stuEmail;
    }

    @Override
    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }

    @Override
    public String toString() {
        return "User_Student{" +
                "stuId='" + stuId + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuTelphone='" + stuTelphone + '\'' +
                ", stuEmail='" + stuEmail + '\'' +
                ", stuEducation='" + stuEducation + '\'' +
                ", stuCollege='" + stuCollege + '\'' +
                ", stuClassName=" + stuClassName +
                ", stuEducationId=" + stuEducationId +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", stuClass=" + stuClass +
                ", stuTopicTitle='" + stuTopicTitle + '\'' +
                ", stuChooseTopicTotalNum=" + stuChooseTopicTotalNum +
                ", stuHasChooseTopicNum=" + stuHasChooseTopicNum +
                ", stuNotChooseTopicNum=" + stuNotChooseTopicNum +
                ", usertype=" + usertype +
                ", stuSchool='" + stuSchool + '\'' +
                '}';
    }
}

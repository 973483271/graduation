package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  学生 实体
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
@TableName("sys_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stuId;

    private String stuName;

    private Integer stuClass;

    private String stuTelphone;

    private String stuEmail;

    private String stuSchool;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date stuUpdateTime;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
    public Integer getStuClass() {
        return stuClass;
    }

    public void setStuClass(Integer stuClass) {
        this.stuClass = stuClass;
    }
    public String getStuTelphone() {
        return stuTelphone;
    }

    public void setStuTelphone(String stuTelphone) {
        this.stuTelphone = stuTelphone;
    }
    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }
    public String getStuSchool() {
        return stuSchool;
    }

    public void setStuSchool(String stuSchool) {
        this.stuSchool = stuSchool;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getStuUpdateTime() {
        return stuUpdateTime;
    }

    public void setStuUpdateTime(Date stuUpdateTime) {
        this.stuUpdateTime = stuUpdateTime;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuClass=" + stuClass +
                ", stuTelphone='" + stuTelphone + '\'' +
                ", stuEmail='" + stuEmail + '\'' +
                ", stuSchool='" + stuSchool + '\'' +
                ", stuUpdateTime=" + stuUpdateTime +
                '}';
    }
}

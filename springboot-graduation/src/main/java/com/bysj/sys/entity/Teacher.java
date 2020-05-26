package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 指导老师 实体
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
@TableName("sys_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private String teaId;

    private String teaName;

    private String teaEducation;

    private String teaTelphone;

    private String teaEmail;

    private String teaSchool;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date teaUpdateTime;

    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }
    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }
    public String getTeaEducation() {
        return teaEducation;
    }

    public void setTeaEducation(String teaEducation) {
        this.teaEducation = teaEducation;
    }
    public String getTeaTelphone() {
        return teaTelphone;
    }

    public void setTeaTelphone(String teaTelphone) {
        this.teaTelphone = teaTelphone;
    }
    public String getTeaEmail() {
        return teaEmail;
    }

    public void setTeaEmail(String teaEmail) {
        this.teaEmail = teaEmail;
    }
    public String getTeaSchool() {
        return teaSchool;
    }

    public void setTeaSchool(String teaSchool) {
        this.teaSchool = teaSchool;
    }


    public Date getTeaUpdateTime() {
        return teaUpdateTime;
    }

    public void setTeaUpdateTime(Date teaUpdateTime) {
        this.teaUpdateTime = teaUpdateTime;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teaId='" + teaId + '\'' +
                ", teaName='" + teaName + '\'' +
                ", teaEducation='" + teaEducation + '\'' +
                ", teaTelphone='" + teaTelphone + '\'' +
                ", teaEmail='" + teaEmail + '\'' +
                ", teaSchool='" + teaSchool + '\'' +
                ", teaUpdateTime=" + teaUpdateTime +
                '}';
    }
}

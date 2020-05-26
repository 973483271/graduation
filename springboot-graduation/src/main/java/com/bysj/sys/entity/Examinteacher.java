package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 *   审核组老师 实体
 * </p>
 *
 * @author jack
 * @since 2020-02-06
 */
@TableName("sys_examinteacher")
public class Examinteacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private String examinteaId;

    private String examinteaName;

    private String examinteaEducation;

    private String examinteaTelphone;

    private String examinteaEmail;

    private String examinteaSchool;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date examinteaUpdateTime;

    public Date getExaminteaUpdateTime() {
        return examinteaUpdateTime;
    }

    public void setExaminteaUpdateTime(Date examinteaUpdateTime) {
        this.examinteaUpdateTime = examinteaUpdateTime;
    }

    public String getExaminteaId() {
        return examinteaId;
    }

    public void setExaminteaId(String examinteaId) {
        this.examinteaId = examinteaId;
    }
    public String getExaminteaName() {
        return examinteaName;
    }

    public void setExaminteaName(String examinteaName) {
        this.examinteaName = examinteaName;
    }
    public String getExaminteaEducation() {
        return examinteaEducation;
    }

    public void setExaminteaEducation(String examinteaEducation) {
        this.examinteaEducation = examinteaEducation;
    }
    public String getExaminteaTelphone() {
        return examinteaTelphone;
    }

    public void setExaminteaTelphone(String examinteaTelphone) {
        this.examinteaTelphone = examinteaTelphone;
    }
    public String getExaminteaEmail() {
        return examinteaEmail;
    }

    public void setExaminteaEmail(String examinteaEmail) {
        this.examinteaEmail = examinteaEmail;
    }
    public String getExaminteaSchool() {
        return examinteaSchool;
    }

    public void setExaminteaSchool(String examinteaSchool) {
        this.examinteaSchool = examinteaSchool;
    }

    @Override
    public String toString() {
        return "Examinteacher{" +
                "examinteaId='" + examinteaId + '\'' +
                ", examinteaName='" + examinteaName + '\'' +
                ", examinteaEducation='" + examinteaEducation + '\'' +
                ", examinteaTelphone='" + examinteaTelphone + '\'' +
                ", examinteaEmail='" + examinteaEmail + '\'' +
                ", examinteaSchool='" + examinteaSchool + '\'' +
                ", examinteaUpdateTime=" + examinteaUpdateTime +
                '}';
    }
}

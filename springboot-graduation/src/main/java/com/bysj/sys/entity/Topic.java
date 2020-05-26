package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 题目 实体
 * </p>
 *
 * @author jack
 * @since 2020-02-10
 */
@TableName("sys_topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tNo; //题目编号

    private String tTitle;//题目标题

    @TableField("t_eduId")
    private Integer tEduid; //题目所属专业id

    private String tIntroduce; //题目介绍说明

    @TableField("t_taskRequest")
    private String tTaskrequest;//题目任务和要求

    @TableField("t_makeTeacher")
    private String tMaketeacher; //出题老师工号

    @TableField("t_examinTeacher")
    private String tExaminteacher;//审题老师工号

    private String tType;//题目所属类型 工程设计类、理论研究类、实验研究类...

    private String tSource;//题目来源 学生自拟、老师自拟

    private String tDifferent;//题目难度指数

    private Integer tStatus;//题目状态

    @TableField("t_makeTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime tMaketime;

    @TableField("t_examinTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date tExamintime;

    private String tChoosestudent;//选题学生学号

    public String gettChoosestudent() {
        return tChoosestudent;
    }

    public void settChoosestudent(String tChoosestudent) {
        this.tChoosestudent = tChoosestudent;
    }

    public String gettNo() {
        return tNo;
    }

    public void settNo(String tNo) {
        this.tNo = tNo;
    }
    public String gettTitle() {
        return tTitle;
    }

    public void settTitle(String tTitle) {
        this.tTitle = tTitle;
    }
    public Integer gettEduid() {
        return tEduid;
    }

    public void settEduid(Integer tEduid) {
        this.tEduid = tEduid;
    }
    public String gettIntroduce() {
        return tIntroduce;
    }

    public void settIntroduce(String tIntroduce) {
        this.tIntroduce = tIntroduce;
    }
    public String gettTaskrequest() {
        return tTaskrequest;
    }

    public void settTaskrequest(String tTaskrequest) {
        this.tTaskrequest = tTaskrequest;
    }
    public String gettMaketeacher() {
        return tMaketeacher;
    }

    public void settMaketeacher(String tMaketeacher) {
        this.tMaketeacher = tMaketeacher;
    }
    public String gettExaminteacher() {
        return tExaminteacher;
    }

    public void settExaminteacher(String tExaminteacher) {
        this.tExaminteacher = tExaminteacher;
    }
    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }
    public String gettSource() {
        return tSource;
    }

    public void settSource(String tSource) {
        this.tSource = tSource;
    }
    public String gettDifferent() {
        return tDifferent;
    }

    public void settDifferent(String tDifferent) {
        this.tDifferent = tDifferent;
    }
    public Integer gettStatus() {
        return tStatus;
    }

    public void settStatus(Integer tStatus) {
        this.tStatus = tStatus;
    }
    public LocalDateTime gettMaketime() {
        return tMaketime;
    }

    public void settMaketime(LocalDateTime tMaketime) {
        this.tMaketime = tMaketime;
    }

    public Date gettExamintime() {
        return tExamintime;
    }

    public void settExamintime(Date tExamintime) {
        this.tExamintime = tExamintime;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "tNo='" + tNo + '\'' +
                ", tTitle='" + tTitle + '\'' +
                ", tEduid=" + tEduid +
                ", tIntroduce='" + tIntroduce + '\'' +
                ", tTaskrequest='" + tTaskrequest + '\'' +
                ", tMaketeacher='" + tMaketeacher + '\'' +
                ", tExaminteacher='" + tExaminteacher + '\'' +
                ", tType='" + tType + '\'' +
                ", tSource='" + tSource + '\'' +
                ", tDifferent='" + tDifferent + '\'' +
                ", tStatus=" + tStatus +
                ", tMaketime=" + tMaketime +
                ", tExamintime=" + tExamintime +
                ", tChoosestudent='" + tChoosestudent + '\'' +
                '}';
    }
}

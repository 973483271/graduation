package com.bysj.sys.entity;

/**
 * 继承topic实体类，新增自定义字段用于数据接收
 */
public class MyTopic extends Topic {
    //---额外加的字段---
    private String tEducation; //题目所属专业名称
    private String tTeacherName;//指导老师名称
    private String tChooseStudentName;//选题学生名称
    private String tExaminTeacherName;//审题老师名称

    public String gettExaminTeacherName() {
        return tExaminTeacherName;
    }

    public void settExaminTeacherName(String tExaminTeacherName) {
        this.tExaminTeacherName = tExaminTeacherName;
    }

    public String gettChooseStudentName() {
        return tChooseStudentName;
    }

    public void settChooseStudentName(String tChooseStudentName) {
        this.tChooseStudentName = tChooseStudentName;
    }

    public String gettEducation() {
        return tEducation;
    }

    public void settEducation(String tEducation) {
        this.tEducation = tEducation;
    }

    public String gettTeacherName() {
        return tTeacherName;
    }

    public void settTeacherName(String tTeacherName) {
        this.tTeacherName = tTeacherName;
    }

    @Override
    public String toString() {
        return "MyTopic{" +
                "tEducation='" + tEducation + '\'' +
                ", tTeacherName='" + tTeacherName + '\'' +
                ", tChooseStudentName='" + tChooseStudentName + '\'' +
                ", tExaminTeacherName='" + tExaminTeacherName + '\'' +
                '}';
    }
}

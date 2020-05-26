package com.bysj.sys.entity;

import java.util.List;

/**
 * 学院-专业  实体
 */
public class College_Education extends College {
    private Integer collId;  //学院 id
    private String collName; //学院名称
    private List<Education> educations;//专业

    @Override
    public Integer getCollId() {
        return collId;
    }

    @Override
    public void setCollId(Integer collId) {
        this.collId = collId;
    }

    @Override
    public String getCollName() {
        return collName;
    }

    @Override
    public void setCollName(String collName) {
        this.collName = collName;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    @Override
    public String toString() {
        return "College_Education{" +
                "collId=" + collId +
                ", collName='" + collName + '\'' +
                ", educations=" + educations +
                '}';
    }
}

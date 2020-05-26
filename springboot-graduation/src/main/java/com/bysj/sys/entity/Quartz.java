package com.bysj.sys.entity;

/**
 * 定时任务
 */
public class Quartz {
    private Integer qId ;
    private String qName;   //出题，审题，选题
    private String qCron;   //时间
    private String qStatus; //状态 ON OFF

    public Integer getqId() {
        return qId;
    }

    public void setqId(Integer qId) {
        this.qId = qId;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public String getqCron() {
        return qCron;
    }

    public void setqCron(String qCron) {
        this.qCron = qCron;
    }

    public String getqStatus() {
        return qStatus;
    }

    public void setqStatus(String qStatus) {
        this.qStatus = qStatus;
    }

    @Override
    public String toString() {
        return "Quartz{" +
                "qId=" + qId +
                ", qName='" + qName + '\'' +
                ", qCron='" + qCron + '\'' +
                ", qStatus='" + qStatus + '\'' +
                '}';
    }
}

package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jack
 * @since 2020-04-03
 */
@TableName("sys_notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titleName;        //标题名称

    private String textName;           //正文

    private String filePath;        //文件路径

    private Integer receiverollId;      //接收角色

    private String resourceId;       //来源

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime; //创建时间

    private Integer receivecollId;  //接收学院

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getReceiverollId() {
        return receiverollId;
    }

    public void setReceiverollId(Integer receiverollId) {
        this.receiverollId = receiverollId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getReceivecollId() {
        return receivecollId;
    }

    public void setReceivecollId(Integer receivecollId) {
        this.receivecollId = receivecollId;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "titleName='" + titleName + '\'' +
                ", textName='" + textName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", receiverollId=" + receiverollId +
                ", resourceId='" + resourceId + '\'' +
                ", createTime=" + createTime +
                ", receivecollId=" + receivecollId +
                '}';
    }
}

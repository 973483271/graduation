package com.bysj.sys.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 公告实体类的子类
 */
public class MyNotice  extends Notice{
    private Integer id;       //公告
    private String resourceName;  //发送方名称
    private String receiverollName ;//接收方名称
    private List<String> pathName;  //文件路径
    private String receivecollName; //部门名称
    private MultipartFile[] files; //存储文件
    private Boolean status;    //消息状态

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getReceiverollName() {
        return receiverollName;
    }

    public void setReceiverollName(String receiverollName) {
        this.receiverollName = receiverollName;
    }

    public List<String> getPathName() {
        return pathName;
    }

    public void setPathName(List<String> pathName) {
        this.pathName = pathName;
    }

    public String getReceivecollName() {
        return receivecollName;
    }

    public void setReceivecollName(String receivecollName) {
        this.receivecollName = receivecollName;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "MyNotice{" +
                "id=" + id +
                ", resourceName='" + resourceName + '\'' +
                ", receiverollName='" + receiverollName + '\'' +
                ", pathName=" + pathName +
                ", receivecollName='" + receivecollName + '\'' +
                ", files=" + Arrays.toString(files) +
                ", status=" + status +
                '}';
    }
}

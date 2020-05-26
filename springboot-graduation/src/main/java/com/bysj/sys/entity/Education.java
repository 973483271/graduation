package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 专业 实体
 * </p>
 *
 * @author jack
 * @since 2020-02-04
 */
@TableName("sys_education")
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "edu_id", type = IdType.AUTO)
    private Integer eduId;

    private String eduName;

    private Integer collId;

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }
    public String getEduName() {
        return eduName;
    }

    public void setEduName(String eduName) {
        this.eduName = eduName;
    }
    public Integer getCollId() {
        return collId;
    }

    public void setCollId(Integer collId) {
        this.collId = collId;
    }

    @Override
    public String toString() {
        return "Education{" +
                "eduId=" + eduId +
                ", eduName='" + eduName + '\'' +
                ", collId=" + collId +
                '}';
    }
}

package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 学院 实体
 * </p>
 *
 * @author jack
 * @since 2020-02-04
 */
@TableName("sys_college")
public class College implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "coll_id", type = IdType.AUTO)
    private Integer collId;  //学院 id

    private String collName; //学院名称



    public Integer getCollId() {
        return collId;
    }

    public void setCollId(Integer collId) {
        this.collId = collId;
    }
    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }



    @Override
    public String toString() {
        return "College{" +
                "collId=" + collId +
                ", collName='" + collName + '\'' +
                '}';
    }
}

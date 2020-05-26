package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 *  班级 实体
 * </p>
 *
 * @author jack
 * @since 2020-02-05
 */
@TableName("sys_class")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "stu_class", type = IdType.AUTO)
    private Integer stuClass;

    private Integer className;

    public Integer getStuClass() {
        return stuClass;
    }

    public void setStuClass(Integer stuClass) {
        this.stuClass = stuClass;
    }
    public Integer getClassName() {
        return className;
    }

    public void setClassName(Integer className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Class{" +
            "stuClass=" + stuClass +
            ", className=" + className +
        "}";
    }
}

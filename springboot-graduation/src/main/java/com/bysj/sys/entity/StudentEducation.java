package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 学生-专业 联合 实体
 * </p>
 *
 * @author jack
 * @since 2020-02-05
 */
@TableName("sys_student_education")
public class StudentEducation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stuId;

    private Integer eduId;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    @Override
    public String toString() {
        return "StudentEducation{" +
            "stuId=" + stuId +
            ", eduId=" + eduId +
        "}";
    }
}

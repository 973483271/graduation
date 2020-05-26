package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 老师-学院关联实体
 * </p>
 *
 * @author jack
 * @since 2020-02-05
 */
@TableName("sys_teacher_college")
public class TeacherCollege implements Serializable {

    private static final long serialVersionUID = 1L;

    private String teaId;

    private Integer collId;

    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }
    public Integer getCollId() {
        return collId;
    }

    public void setCollId(Integer collId) {
        this.collId = collId;
    }

    @Override
    public String toString() {
        return "TeacherCollege{" +
            "teaId=" + teaId +
            ", collId=" + collId +
        "}";
    }
}

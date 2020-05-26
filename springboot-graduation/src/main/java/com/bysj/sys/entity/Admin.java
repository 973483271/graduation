package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 *      管理员 实体
 * </p>
 *
 * @author jack
 * @since 2020-01-19
 */
@TableName("sys_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String adminId;

    private String adminName;

    private String adminTelphone;

    private String adminEmail;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public String getAdminTelphone() {
        return adminTelphone;
    }

    public void setAdminTelphone(String adminTelphone) {
        this.adminTelphone = adminTelphone;
    }
    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    @Override
    public String toString() {
        return "Admin{" +
            "adminId=" + adminId +
            ", adminName=" + adminName +
            ", adminTelphone=" + adminTelphone +
            ", adminEmail=" + adminEmail +
        "}";
    }
}

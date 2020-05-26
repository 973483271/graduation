package com.bysj.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jack
 * @since 2020-04-02
 */
@TableName("sys_user_message_read")
public class UserMessageRead implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uId;

    private String mRead;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
    public String getmRead() {
        return mRead;
    }

    public void setmRead(String mRead) {
        this.mRead = mRead;
    }

    @Override
    public String toString() {
        return "UserMessageRead{" +
            "uId=" + uId +
            ", mRead=" + mRead +
        "}";
    }
}

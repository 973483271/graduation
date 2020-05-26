package com.bysj.sys.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;


/**
 * 2020/1/16.
 */
public class UsernamePasswordUsertypeToken extends UsernamePasswordToken {

    private Integer usertype;

    public UsernamePasswordUsertypeToken() {
    }

    public UsernamePasswordUsertypeToken(String username, char[] password, Integer usertype) {
        super(username, password);
        this.usertype = usertype;
    }

    public UsernamePasswordUsertypeToken(String username, String password, Integer usertype) {
        super(username, password);
        this.usertype = usertype;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }
}

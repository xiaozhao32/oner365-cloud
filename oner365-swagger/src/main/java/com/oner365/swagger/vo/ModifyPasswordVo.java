package com.oner365.swagger.vo;

import java.io.Serializable;

/**
 * 修改密码对象
 * 
 * @author zhaoyong
 *
 */
public class ModifyPasswordVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 旧密码 oldPassword
     */
    private String oldPassword;
    
    /**
     * 新密码 password
     */
    private String password;
    
    /**
     * 构造方法
     */
    public ModifyPasswordVo() {
        super();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String ppassword) {
        this.password = ppassword;
    }

}

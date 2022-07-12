package com.oner365.sys.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

/**
 * 重置密码对象
 * 
 * @author zhaoyong
 *
 */
public class ResetPasswordVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号 userId
     */
    @NotBlank(message = "账号id不能为空")
    private String userId;
    
    /**
     * 密码 password
     */
    @NotBlank(message = "重置密码不能为空")
    private String password;
    
    /**
     * 构造方法
     */
    public ResetPasswordVo() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String ppassword) {
        this.password = ppassword;
    }

}

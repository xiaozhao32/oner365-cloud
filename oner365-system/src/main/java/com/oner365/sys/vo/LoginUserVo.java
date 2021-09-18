package com.oner365.sys.vo;

import java.io.Serializable;

/**
 * 登录对象
 * 
 * @author zhaoyong
 *
 */
public class LoginUserVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号 userName
     */
    private String userName;
    
    /**
     * 密码 password
     */
    private String password;
    
    /**
     * 图片验证码 uuid
     */
    private String uuid;
    
    /**
     * 验证码 code
     */
    private String code;
    
    /**
     * 构造方法
     */
    public LoginUserVo() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

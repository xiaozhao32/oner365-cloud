package com.oner365.swagger.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录对象
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "登录对象")
public class LoginUserVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号 userName
     */
    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "{system.vo.loginUser.userName.message}")
    private String userName;
    
    /**
     * 密码 password
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "{system.vo.loginUser.password.message}")
    private String password;
    
    /**
     * 图片验证码 uuid
     */
    @ApiModelProperty(value = "图片验证码")
    private String uuid;
    
    /**
     * 验证码 code
     */
    @ApiModelProperty(value = "验证码")
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

package com.oner365.swagger.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改密码对象
 *
 * @author zhaoyong
 *
 */
@ApiModel(value = "修改密码对象")
public class ModifyPasswordVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 旧密码 oldPassword
     */
    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank(message = "{system.vo.modify.oldPassword.message}")
    private String oldPassword;

    /**
     * 新密码 password
     */
    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "{system.vo.modify.newPassword.message}")
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

    public void setPassword(String password) {
        this.password = password;
    }

}

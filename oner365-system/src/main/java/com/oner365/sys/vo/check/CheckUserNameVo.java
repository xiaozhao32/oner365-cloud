package com.oner365.sys.vo.check;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

/**
 * 检测编码
 *
 * @author zhaoyong
 *
 */
public class CheckUserNameVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    private String id;

    /**
     * 用户名称
     */
    @NotBlank(message = "{system.vo.check.userName.message}")
    private String userName;

    /**
     * 构造方法
     */
    public CheckUserNameVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

package com.oner365.swagger.vo.check;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 检测用户编码
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "检测用户编码")
public class CheckUserNameVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键 id
     */
    @ApiModelProperty(value = "主键")
    private String id;
    
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
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

package com.oner365.swagger.vo.check;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 检测字典编码
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "检测字典编码")
public class CheckCodeVo implements Serializable {

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
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;
    
    /**
     * 构造方法
     */
    public CheckCodeVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

package com.oner365.sys.vo.check;

import java.io.Serializable;

/**
 * 检测编码
 * 
 * @author zhaoyong
 *
 */
public class CheckOrgCodeVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键 id
     */
    private String id;
    
    /**
     * 编码
     */
    private String code;
    
    /**
     * 类型
     */
    private String type;
    
    /**
     * 构造方法
     */
    public CheckOrgCodeVo() {
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
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

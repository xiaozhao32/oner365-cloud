package com.oner365.sys.vo.check;

import java.io.Serializable;

/**
 * 检测编码
 * 
 * @author zhaoyong
 *
 */
public class CheckCodeVo implements Serializable {

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

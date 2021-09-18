package com.oner365.sys.vo.check;

import java.io.Serializable;

/**
 * 检测编码
 * 
 * @author zhaoyong
 *
 */
public class CheckTypeCodeVo implements Serializable {

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
     * 类型id
     */
    private String typeId;
    
    /**
     * 构造方法
     */
    public CheckTypeCodeVo() {
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
    
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}

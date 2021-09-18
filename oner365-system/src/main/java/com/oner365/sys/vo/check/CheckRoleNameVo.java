package com.oner365.sys.vo.check;

import java.io.Serializable;

/**
 * 检测编码
 * 
 * @author zhaoyong
 *
 */
public class CheckRoleNameVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键 id
     */
    private String id;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 构造方法
     */
    public CheckRoleNameVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

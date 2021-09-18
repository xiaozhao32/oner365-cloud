package com.oner365.sys.vo;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.MoreObjects;
import com.oner365.sys.entity.SysMenuType;

/**
 * 菜单类型对象
 * @author zhaoyong
 */
public class SysMenuTypeVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 更新时间 update_time
     */
    private Date updateTime;

    /**
     * Constructor
     */
    public SysMenuTypeVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

    /**
     * 转换对象
     * 
     * @return SysMenuType
     */
    public SysMenuType toObject() {
        SysMenuType result = new SysMenuType();
        result.setId(this.getId());
        result.setCreateTime(this.getCreateTime());
        result.setStatus(this.getStatus());
        result.setTypeCode(this.getTypeCode());
        result.setTypeName(this.getTypeName());
        result.setUpdateTime(this.getUpdateTime());
        return result;
    }

}
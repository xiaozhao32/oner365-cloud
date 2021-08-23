package com.oner365.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 菜单类型对象
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_menu_type")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenuType implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    private String id;

    @Column(name = "type_name", nullable = false, length = 32)
    private String typeName;

    @Column(name = "type_code", nullable = false, length = 32)
    private String typeCode;

    @Column(name = "status", nullable = false, length = 8)
    private String status;

    /**
     * 创建时间 create_time
     */
    @Column(name = "create_time", updatable = false)
    private Date createTime;

    /**
     * 更新时间 update_time
     */
    @Column(name = "update_time", insertable = false)
    private Date updateTime;

    /**
     * Constructor
     */
    public SysMenuType() {
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

}

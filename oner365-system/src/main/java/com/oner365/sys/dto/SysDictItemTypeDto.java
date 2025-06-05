package com.oner365.sys.dto;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

/**
 * 字典类型 SysDictItemType
 *
 * @author zhaoyong
 */
public class SysDictItemTypeDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    private String id;

    /**
     * 类型名称 type_name
     */
    private String typeName;

    /**
     * 类型编码 type_code
     */
    private String typeCode;

    /**
     * 类型描述 type_des
     */
    private String typeDes;

    /**
     * 排序 type_order
     */
    private Integer typeOrder;

    /**
     * 状态 status
     */
    private StatusEnum status;

    /**
     * Constructor
     */
    public SysDictItemTypeDto() {
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

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
    }

    public Integer getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(Integer typeOrder) {
        this.typeOrder = typeOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}

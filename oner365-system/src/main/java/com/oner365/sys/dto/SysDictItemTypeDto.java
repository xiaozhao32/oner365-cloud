package com.oner365.sys.dto;

import java.io.Serializable;

import com.google.common.base.MoreObjects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字典类型 SysDictItemType
 * 
 * @author zhaoyong
 */
@ApiModel(value = "字典类型")
public class SysDictItemTypeDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 类型名称 type_name
     */
    @ApiModelProperty(value = "类型名称", required = true)
    private String typeName;

    /**
     * 类型编码 type_code
     */
    @ApiModelProperty(value = "类型编码", required = true)
    private String typeCode;

    /**
     * 类型描述 type_des
     */
    @ApiModelProperty(value = "类型描述")
    private String typeDes;

    /**
     * 排序 type_order
     */
    @ApiModelProperty(value = "排序")
    private Integer typeOrder;

    /**
     * 状态 status
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * Constructor
     */
    public SysDictItemTypeDto(){
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

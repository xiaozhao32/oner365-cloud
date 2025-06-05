package com.oner365.swagger.dto;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字典 SysDictItem
 *
 * @author zhaoyong
 */
@ApiModel(value = "字典")
public class SysDictItemDto implements Serializable {

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
     * 字典类型编码 type_id
     */
    @ApiModelProperty(value = "字典类型编码", required = true)
    private String typeId;

    /**
     * 字典编码 item_code
     */
    @ApiModelProperty(value = "字典编码", required = true)
    private String itemCode;

    /**
     * 字典名称 item_name
     */
    @ApiModelProperty(value = "字典名称", required = true)
    private String itemName;

    /**
     * 排序 item_order
     */
    @ApiModelProperty(value = "排序")
    private Integer itemOrder;

    /**
     * 状态 status
     */
    @ApiModelProperty(value = "状态")
    private StatusEnum status;

    /**
     * 上级id parent_id
     */
    @ApiModelProperty(value = "上级id")
    private String parentId;

    /**
     * Constructor
     */
    public SysDictItemDto() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}

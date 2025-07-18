package com.oner365.sys.dto;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

/**
 * 字典 SysDictItem
 *
 * @author zhaoyong
 */
public class SysDictItemDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    private String id;

    /**
     * 字典类型编码 type_id
     */
    private String typeId;

    /**
     * 字典编码 item_code
     */
    private String itemCode;

    /**
     * 字典名称 item_name
     */
    private String itemName;

    /**
     * 排序 item_order
     */
    private Integer itemOrder;

    /**
     * 状态 status
     */
    private StatusEnum status;

    /**
     * 上级id parent_id
     */
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

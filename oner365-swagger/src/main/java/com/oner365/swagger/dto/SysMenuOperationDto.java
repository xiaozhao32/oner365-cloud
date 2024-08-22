package com.oner365.swagger.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 操作对象
 * @author zhaoyong
 */
@ApiModel(value = "菜单操作")
public class SysMenuOperationDto implements Serializable {

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
     * 操作名称
     */
    @ApiModelProperty(value = "操作名称", required = true)
    private String operationName;

    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private String operationType;

    /**
     * 状态 status
     */
    @ApiModelProperty(value = "状态")
    private StatusEnum status;

    /**
     * 创建时间 create_time
     */
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    /**
     * 更新时间 update_time
     */
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    /**
     * Constructor
     */
    public SysMenuOperationDto() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}

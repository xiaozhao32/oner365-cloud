package com.oner365.swagger.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单类型对象
 *
 * @author zhaoyong
 */
@ApiModel(value = "菜单类型")
public class SysMenuTypeVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称", required = true)
    @NotBlank(message = "{system.vo.menuType.typeName.message}")
    private String typeName;

    /**
     * 类型编码
     */
    @ApiModelProperty(value = "类型编码", required = true)
    @NotBlank(message = "{system.vo.menuType.typeCode.message}")
    private String typeCode;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "{system.vo.menuType.status.message}")
    private StatusEnum status;

    /**
     * 创建时间 create_time
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间 update_time
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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

package com.oner365.swagger.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单树对象
 *
 * @author zhaoyong
 *
 */
@ApiModel(value = "菜单树信息")
public class SysMenuIconDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String title;

    /**
     * 菜单icon
     */
    @ApiModelProperty(value = "菜单icon")
    private String icon;

    public SysMenuIconDto() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}

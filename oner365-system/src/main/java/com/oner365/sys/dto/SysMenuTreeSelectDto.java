package com.oner365.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树权限
 *
 * @author zhaoyong
 *
 */
public class SysMenuTreeSelectDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 菜单列表
     */
    private List<TreeSelect> menus = new ArrayList<>();

    /**
     * 选中权限集合
     */
    private List<String> checkedKeys = new ArrayList<>();

    public SysMenuTreeSelectDto() {
        super();
    }

    public List<TreeSelect> getMenus() {
        return menus;
    }

    public void setMenus(List<TreeSelect> menus) {
        this.menus = menus;
    }

    public List<String> getCheckedKeys() {
        return checkedKeys;
    }

    public void setCheckedKeys(List<String> checkedKeys) {
        this.checkedKeys = checkedKeys;
    }

}

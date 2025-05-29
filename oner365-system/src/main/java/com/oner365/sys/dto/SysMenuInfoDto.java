package com.oner365.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单详情对象
 *
 * @author zhaoyong
 *
 */
public class SysMenuInfoDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 菜单对象
     */
    private SysMenuDto sysMenu;

    /**
     * 菜单列表
     */
    private List<String> menuOperList = new ArrayList<>();

    /**
     * 操作列表
     */
    private List<SysMenuOperationDto> operationList = new ArrayList<>();

    public SysMenuInfoDto() {
        super();
    }

    public SysMenuDto getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(SysMenuDto sysMenu) {
        this.sysMenu = sysMenu;
    }

    public List<String> getMenuOperList() {
        return menuOperList;
    }

    public void setMenuOperList(List<String> menuOperList) {
        this.menuOperList = menuOperList;
    }

    public List<SysMenuOperationDto> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<SysMenuOperationDto> list) {
        this.operationList = list;
    }

}

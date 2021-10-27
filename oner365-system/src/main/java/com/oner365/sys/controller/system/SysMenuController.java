package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysMenu;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.entity.TreeSelect;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.sys.vo.SysMenuVo;

/**
 * 系统菜单
 * @author zhaoyong
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseController {

    @Autowired
    public ISysMenuService menuService;

    @Autowired
    public ISysMenuOperationService operationService;

    /**
     * 保存菜单
     *
     * @param sysMenuVo 菜单对象
     * @return ResponseResult<SysMenu>
     */
    @PutMapping("/save")
    public ResponseResult<SysMenu> save(@RequestBody SysMenuVo sysMenuVo) {
        if (sysMenuVo != null) {
            SysMenu entity = menuService.save(sysMenuVo.toObject());
            return ResponseResult.success(entity);
        }
        return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
    }

    /**
     * 获取菜单
     *
     * @param id 编号
     * @return Map<String, Object>
     */
    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable String id) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("sysMenu", menuService.getById(id));
        List<String> menuOperList = operationService.selectByMenuId(id);
        result.put("menuOperList", menuOperList);
        List<SysMenuOperation> operationList = operationService.findList();
        result.put("operationList", operationList);
        return result;

    }

    /**
     * 菜单列表
     *
     * @param sysMenuVo 菜单对象
     * @return List<SysMenu>
     */
    @PostMapping("/list")
    public List<SysMenu> list(@RequestBody SysMenuVo sysMenuVo) {
        SysMenu sysMenu = sysMenuVo.toObject();
        if (sysMenu != null) {
            return menuService.selectList(sysMenu);
        }
        return new ArrayList<>();
    }

    /**
     * 删除
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = menuService.deleteById(id);
        }
        return code;
    }

    /**
     * 修改菜单状态
     *
     * @param id 主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/editStatusById/{id}")
    public Integer editStatusById(@PathVariable String id, @RequestParam("status") String status) {
        return menuService.editStatusById(id, status);
    }

    /**
     * 获取菜单下拉树列表
     *
     * @param authUser 登录对象
     * @param sysMenuVo 菜单对象
     * @return List<TreeSelect>
     */
    @PostMapping("/treeselect")
    public List<TreeSelect> treeselect(@RequestBody SysMenuVo sysMenuVo,
            @CurrentUser AuthUser authUser) {
        SysMenu menu = sysMenuVo.toObject();
        List<SysMenu> menus;
        if (SysConstants.DEFAULT_ROLE.equals(authUser.getIsAdmin())) {
            menus = menuService.selectList(menu);
        } else {
            menu.setUserId(authUser.getId());
            menus = menuService.selectListByUserId(menu);
        }
        return menuService.buildTreeSelect(menus);
    }

    /**
     * 加载对应角色菜单列表树
     *
     * @param authUser 登录对象
     * @param sysMenuVo 菜单对象
     * @param roleId String
     * @return Map<String, Object>
     */
    @PostMapping("/roleMenuTreeselect/{roleId}")
    public Map<String, Object> roleMenuTreeselect(@RequestBody SysMenuVo sysMenuVo,
            @PathVariable("roleId") String roleId, @CurrentUser AuthUser authUser) {
        SysMenu menu = sysMenuVo.toObject();
        List<SysMenu> menus;
        if (SysConstants.DEFAULT_ROLE.equals(authUser.getIsAdmin())) {
            menus = menuService.selectList(menu);
        } else {
            menu.setUserId(authUser.getId());
            menus = menuService.selectListByUserId(menu);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("checkedKeys", menuService.selectListByRoleId(roleId, menu.getMenuTypeId()));
        result.put("menus", menuService.buildTreeSelect(menus));
        return result;
    }

}

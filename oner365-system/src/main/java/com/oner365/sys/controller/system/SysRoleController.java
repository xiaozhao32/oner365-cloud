package com.oner365.sys.controller.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysRole;
import com.oner365.sys.service.ISysRoleService;

/**
 * 角色管理
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    /**
     * 获取信息
     * 
     * @param id 编号
     * @return SysRole
     */
    @GetMapping("/get/{id}")
    public SysRole get(@PathVariable String id) {
        return roleService.getById(id);
    }

    /**
     * 列表
     * 
     * @param data 查询参数
     * @return Page<SysRole>
     */
    @PostMapping("/list")
    public Page<SysRole> list(@RequestBody QueryCriteriaBean data) {
        return roleService.pageList(data);
    }

    /**
     * 删除
     * 
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = roleService.deleteById(id);
        }
        return code;
    }

    /**
     * 修改状态
     *
     * @param id     主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/editStatus/{id}")
    public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
        return roleService.editStatus(id, status);
    }

    /**
     * 判断是否存在
     * 
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkRoleName")
    public Map<String, Object> checkRoleName(@RequestBody JSONObject paramJson) {
        String id = paramJson.getString(SysConstants.ID);
        String roleName = paramJson.getString(SysConstants.ROLE_NAME);
        long code = roleService.checkRoleName(id, roleName);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 角色权限保存
     * 
     * @param paramJson 参数
     * @return ResponseData
     */
    @PutMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject paramJson) {
        // 保存角色
        SysRole sysRole = JSON.toJavaObject(paramJson, SysRole.class);
        SysRole entity = roleService.save(sysRole);

        // 保存权限
        JSONArray menuIds = paramJson.getJSONArray("menuIds");
        int code = roleService.saveAuthority(paramJson.getString(SysConstants.MENU_TYPE), menuIds, entity.getId());

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        result.put(PublicConstants.MSG, entity);
        return result;
    }

    /**
     * 导出Excel
     * 
     * @param data 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
        List<SysRole> list = roleService.findList(data);

        String[] titleKeys = new String[] { "编号", "角色标识", "角色名称", "角色描述", "状态", "创建时间", "更新时间" };
        String[] columnNames = { "id", "roleCode", "roleName", "roleDes", "status", "createTime", "updateTime" };

        String fileName = SysRole.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}

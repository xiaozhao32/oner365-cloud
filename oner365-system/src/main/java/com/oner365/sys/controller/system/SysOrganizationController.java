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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.entity.SysOrganization;
import com.oner365.sys.entity.TreeSelect;
import com.oner365.sys.service.ISysOrganizationService;

/**
 * 机构管理
 * @author zhaoyong
 */
@RestController
@RequestMapping("/org")
public class SysOrganizationController extends BaseController {

    @Autowired
    private ISysOrganizationService sysOrgService;

    /**
     * 机构信息保存
     *
     * @param paramJson 机构对象
     * @param authUser  登录对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject paramJson,
            @CurrentUser AuthUser authUser) {
        SysOrganization sysOrganization = JSON.toJavaObject(paramJson, SysOrganization.class);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysOrganization != null) {
            sysOrganization.setCreateUser(authUser.getId());
            SysOrganization entity = sysOrgService.save(sysOrganization);

            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * id查询
     *
     * @param id 编号
     * @return SysOrganization
     */
    @GetMapping("/get/{id}")
    public SysOrganization get(@PathVariable String id) {
        return sysOrgService.getById(id);
    }

    /**
     * 直接测试数据源是否连接
     *
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/isConnection")
    public Map<String, Object> isConnection(@RequestBody JSONObject paramJson) {
        return sysOrgService.isConnection(paramJson);
    }

    /**
     * 判断保存后数据源是否连接
     *
     * @param id 变
     * @return Map<String, Object>
     */
    @GetMapping("/checkConnection/{id}")
    public Map<String, Object> checkConnection(@PathVariable String id) {
        return sysOrgService.checkConnection(id);
    }

    /**
     * 查询列表
     *
     * @param paramJson 机构对象
     * @return List<SysOrganization>
     */
    @PostMapping("/list")
    public List<SysOrganization> findList(@RequestBody JSONObject paramJson) {
        SysOrganization sysOrganization = JSON.toJavaObject(paramJson, SysOrganization.class);
        if (sysOrganization != null) {
            return sysOrgService.selectList(sysOrganization);
        }
        return new ArrayList<>();
    }

    /**
     * 按父级菜单查询
     *
     * @param parentId 父级编号
     * @return List<SysOrganization>
     */
    @GetMapping("/findListByParentId")
    public List<SysOrganization> findListByParentId(@RequestParam("parentId") String parentId) {
        return sysOrgService.findListByParentId(parentId);
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
            code = sysOrgService.deleteById(id);
        }
        return code;
    }

    /**
     * 判断机构编号是否存在
     *
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkCode")
    public Map<String, Object> checkCode(@RequestBody JSONObject paramJson) {
        String type = paramJson.getString("type");
        String code = paramJson.getString("code");
        String orgId = paramJson.getString("orgId");
        long check = sysOrgService.checkCode(orgId, code, type);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, check);
        return result;
    }

    /**
     * 获取菜单下拉树列表
     *
     * @param paramJson 机构对象
     * @param authUser  登录对象
     * @return List<TreeSelect>
     */
    @PostMapping("/treeselect")
    public List<TreeSelect> treeselect(@RequestBody JSONObject paramJson, @CurrentUser AuthUser authUser) {
        List<SysOrganization> list = sysOrgService.selectList(JSON.toJavaObject(paramJson, SysOrganization.class));
        return sysOrgService.buildTreeSelect(list);
    }

    /**
     * 加载对应角色菜单列表树
     *
     * @param paramJson 机构对象
     * @param userId    用户id
     * @param authUser  登录对象
     * @return Map<String, Object>
     */
    @PostMapping("/userTreeselect/{userId}")
    public Map<String, Object> userTreeselect(@RequestBody JSONObject paramJson, @PathVariable("userId") String userId,
            @CurrentUser AuthUser authUser) {
        SysOrganization sysOrganization = JSON.toJavaObject(paramJson, SysOrganization.class);

        List<SysOrganization> list = sysOrgService.selectList(sysOrganization);
        Map<String, Object> result = Maps.newHashMap();
        result.put("checkedKeys", sysOrgService.selectListByUserId(userId));
        result.put("orgList", sysOrgService.buildTreeSelect(list));
        return result;
    }

    /**
     * 修改状态
     *
     * @param id     主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/changeStatus/{id}")
    public Integer changeStatus(@PathVariable String id, @RequestParam("status") String status) {
        return sysOrgService.changeStatus(id, status);
    }

}

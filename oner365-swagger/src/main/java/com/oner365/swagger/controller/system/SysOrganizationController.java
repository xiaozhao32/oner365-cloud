package com.oner365.swagger.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.system.ISystemOrgClient;
import com.oner365.swagger.dto.SysMenuTreeSelectDto;
import com.oner365.swagger.dto.SysOrganizationDto;
import com.oner365.swagger.dto.TreeSelect;
import com.oner365.swagger.vo.SysOrganizationVo;
import com.oner365.swagger.vo.check.CheckOrgCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 机构管理
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 机构")
@RequestMapping("/system/org")
public class SysOrganizationController {

    @Resource
    private ISystemOrgClient client;

    /**
     * 获取列表
     * @param sysOrganizationVo 机构对象
     * @return ResponseData<List<SysOrganizationDto>>
     */
    @ApiOperation("1.获取列表")
    @ApiOperationSupport(order = 1)
    @PostMapping("/list")
    public ResponseData<List<SysOrganizationDto>> findList(@RequestBody SysOrganizationVo sysOrganizationVo) {
        return client.findList(sysOrganizationVo);
    }

    /**
     * id查询
     * @param id 编号
     * @return ResponseData<SysOrganizationDto>
     */
    @ApiOperation("2.按id查询")
    @ApiOperationSupport(order = 2)
    @GetMapping("/get/{id}")
    public ResponseData<SysOrganizationDto> get(@PathVariable String id) {
        return client.getById(id);
    }

    /**
     * 判断保存后数据源是否连接
     * @param id 编号
     * @return ResponseData<Boolean>
     */
    @ApiOperation("4.测试连接数据源")
    @ApiOperationSupport(order = 4)
    @GetMapping("/connection/check/{id}")
    public ResponseData<Boolean> checkConnection(@PathVariable String id) {
        return client.checkConnection(id);
    }

    /**
     * 按父级菜单查询
     * @param parentId 父级编号
     * @return ResponseData<List<SysOrganizationDto>>
     */
    @ApiOperation("5.父级id查询")
    @ApiOperationSupport(order = 5)
    @GetMapping("/parent")
    public ResponseData<List<SysOrganizationDto>> parent(@RequestParam String parentId) {
        return client.parent(parentId);
    }

    /**
     * 判断机构编号是否存在
     * @param checkOrgCodeVo 查询参数
     * @return ResponseData<Boolean>
     */
    @ApiOperation("6.判断编码是否存在")
    @ApiOperationSupport(order = 6)
    @PostMapping("/check")
    public ResponseData<Boolean> checkCode(@RequestBody CheckOrgCodeVo checkOrgCodeVo) {
        return client.checkCode(checkOrgCodeVo);
    }

    /**
     * 获取菜单下拉树列表
     * @param sysOrganizationVo 机构对象
     * @return ResponseData<List<TreeSelect>>
     */
    @ApiOperation("7.获取树型列表")
    @ApiOperationSupport(order = 7)
    @PostMapping("/tree")
    public ResponseData<List<TreeSelect>> treeselect(@RequestBody SysOrganizationVo sysOrganizationVo) {
        return client.treeselect(sysOrganizationVo);
    }

    /**
     * 加载对应角色菜单列表树
     * @param sysOrganizationVo 机构对象
     * @param userId 用户id
     * @return ResponseData<SysMenuTreeSelectDto>
     */
    @ApiOperation("8.获取用户权限")
    @ApiOperationSupport(order = 8)
    @PostMapping("/user/{userId}")
    public ResponseData<SysMenuTreeSelectDto> userTreeselect(@RequestBody SysOrganizationVo sysOrganizationVo,
            @PathVariable String userId) {
        return client.userTreeselect(sysOrganizationVo, userId);
    }

    /**
     * 修改状态
     * @param id 主键
     * @param status 状态
     * @return ResponseData<Boolean>
     */
    @ApiOperation("9.修改状态")
    @ApiOperationSupport(order = 9)
    @PostMapping("/status/{id}")
    public ResponseData<Boolean> editStatus(@PathVariable String id, @RequestParam StatusEnum status) {
        return client.editStatus(id, status);
    }

    /**
     * 机构信息保存
     * @param sysOrganizationVo 机构对象
     * @return ResponseData<SysOrganizationDto>
     */
    @ApiOperation("10.保存")
    @ApiOperationSupport(order = 10)
    @PutMapping("/save")
    public ResponseData<SysOrganizationDto> save(@RequestBody SysOrganizationVo sysOrganizationVo) {
        return client.save(sysOrganizationVo);
    }

    /**
     * 删除
     * @param ids 编号
     * @return ResponseData<List<Boolean>>
     */
    @ApiOperation("11.删除")
    @ApiOperationSupport(order = 11)
    @DeleteMapping("/delete")
    public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
        return client.delete(ids);
    }

}

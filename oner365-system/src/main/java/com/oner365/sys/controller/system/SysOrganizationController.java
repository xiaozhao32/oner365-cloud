package com.oner365.sys.controller.system;

import java.util.Collections;
import java.util.HashMap;
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

import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysOrganizationDto;
import com.oner365.sys.dto.TreeSelect;
import com.oner365.sys.service.ISysOrganizationService;
import com.oner365.sys.vo.SysOrganizationVo;
import com.oner365.sys.vo.check.CheckOrgCodeVo;

/**
 * 机构管理
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/org")
public class SysOrganizationController extends BaseController {

  @Autowired
  private ISysOrganizationService sysOrgService;

  /**
   * 查询列表
   *
   * @param sysOrganizationVo 机构对象
   * @return List<SysOrganizationDto>
   */
  @PostMapping("/list")
  public List<SysOrganizationDto> findList(@RequestBody SysOrganizationVo sysOrganizationVo) {
    if (sysOrganizationVo != null) {
      return sysOrgService.selectList(sysOrganizationVo);
    }
    return Collections.emptyList();
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return SysOrganizationDto
   */
  @GetMapping("/get/{id}")
  public SysOrganizationDto get(@PathVariable String id) {
    return sysOrgService.getById(id);
  }

  /**
   * 直接测试数据源是否连接
   *
   * @param ds       数据源类型
   * @param ip       ip地址
   * @param port     端口
   * @param dbname   数据源名称
   * @param username 账号
   * @param password 密码
   * @return boolean
   */
  @PostMapping("/connection/{ds}")
  public boolean isConnection(@PathVariable String ds, @RequestParam String ip, @RequestParam int port,
      @RequestParam String dbname, @RequestParam String username, @RequestParam String password) {
    return sysOrgService.isConnection(ds, ip, port, dbname, username, password);
  }

  /**
   * 判断保存后数据源是否连接
   *
   * @param id 编号
   * @return boolean
   */
  @GetMapping("/check/{id}")
  public boolean checkConnection(@PathVariable String id) {
    return sysOrgService.checkConnection(id);
  }

  /**
   * 按父级菜单查询
   *
   * @param parentId 父级编号
   * @return List<SysOrganizationDto>
   */
  @GetMapping("/parent")
  public List<SysOrganizationDto> findListByParentId(@RequestParam("parentId") String parentId) {
    return sysOrgService.findListByParentId(parentId);
  }

  /**
   * 判断机构编号是否存在
   *
   * @param checkOrgCodeVo 查询参数
   * @return Long
   */
  @PostMapping("/check")
  public Long checkCode(@RequestBody CheckOrgCodeVo checkOrgCodeVo) {
    if (checkOrgCodeVo != null) {
      return sysOrgService.checkCode(checkOrgCodeVo.getId(), checkOrgCodeVo.getCode(), checkOrgCodeVo.getType());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 获取菜单下拉树列表
   *
   * @param sysOrganizationVo 机构对象
   * @param authUser          登录对象
   * @return List<TreeSelect>
   */
  @PostMapping("/tree")
  public List<TreeSelect> treeselect(@RequestBody SysOrganizationVo sysOrganizationVo, @CurrentUser AuthUser authUser) {
    List<SysOrganizationDto> list = sysOrgService.selectList(sysOrganizationVo);
    return sysOrgService.buildTreeSelect(list);
  }

  /**
   * 加载对应角色菜单列表树
   *
   * @param sysOrganizationVo 机构对象
   * @param userId            用户id
   * @param authUser          登录对象
   * @return Map<String, Object>
   */
  @PostMapping("/user/{userId}")
  public Map<String, Object> userTreeselect(@RequestBody SysOrganizationVo sysOrganizationVo,
      @PathVariable("userId") String userId, @CurrentUser AuthUser authUser) {
    List<SysOrganizationDto> list = sysOrgService.selectList(sysOrganizationVo);
    Map<String, Object> result = new HashMap<>();
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
  @PostMapping("/status/{id}")
  public Integer changeStatus(@PathVariable String id, @RequestParam("status") String status) {
    return sysOrgService.changeStatus(id, status);
  }

  /**
   * 机构信息保存
   *
   * @param sysOrganizationVo 机构对象
   * @param authUser          登录对象
   * @return ResponseResult<SysOrganizationDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysOrganizationDto> save(@RequestBody SysOrganizationVo sysOrganizationVo,
      @CurrentUser AuthUser authUser) {
    if (sysOrganizationVo != null) {
      sysOrganizationVo.setCreateUser(authUser.getId());
      SysOrganizationDto entity = sysOrgService.save(sysOrganizationVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
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

}

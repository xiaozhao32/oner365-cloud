package com.oner365.sys.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysRoleDto;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.vo.SysRoleVo;
import com.oner365.sys.vo.check.CheckRoleNameVo;

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
   * 列表
   * 
   * @param data 查询参数
   * @return PageInfo<SysRoleDto>
   */
  @PostMapping("/list")
  public PageInfo<SysRoleDto> pageList(@RequestBody QueryCriteriaBean data) {
    return roleService.pageList(data);
  }

  /**
   * 获取信息
   * 
   * @param id 编号
   * @return SysRoleDto
   */
  @GetMapping("/get/{id}")
  public SysRoleDto get(@PathVariable String id) {
    return roleService.getById(id);
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/status/{id}")
  public Integer editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return roleService.editStatus(id, status);
  }

  /**
   * 判断是否存在
   * 
   * @param checkRoleNameVo 查询参数
   * @return Long
   */
  @PostMapping("/check")
  public Long checkRoleName(@RequestBody CheckRoleNameVo checkRoleNameVo) {
    if (checkRoleNameVo != null) {
      return roleService.checkRoleName(checkRoleNameVo.getId(), checkRoleNameVo.getRoleName());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 角色权限保存
   * 
   * @param sysRoleVo 参数
   * @return ResponseResult<Integer>
   */
  @PutMapping("/save")
  public ResponseResult<Integer> save(@RequestBody SysRoleVo sysRoleVo) {
    if (sysRoleVo != null) {
      // 保存角色
      SysRoleDto entity = roleService.save(sysRoleVo);
      int code = StatusEnum.NO.ordinal();
      if (entity != null && sysRoleVo.getMenuType() != null) {
        // 保存权限
        code = roleService.saveAuthority(sysRoleVo.getMenuType(), sysRoleVo.getMenuIds(), entity.getId());
      }
      return ResponseResult.success(code);

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
      code = roleService.deleteById(id);
    }
    return code;
  }

  /**
   * 导出Excel
   * 
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysRoleDto> list = roleService.findList(data);

    String[] titleKeys = new String[] { "编号", "角色标识", "角色名称", "角色描述", "状态", "创建时间", "更新时间" };
    String[] columnNames = { "id", "roleCode", "roleName", "roleDes", "status", "createTime", "updateTime" };

    String fileName = SysRoleDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}

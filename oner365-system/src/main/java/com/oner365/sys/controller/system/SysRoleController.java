package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
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

  @Resource
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
   * @return Boolean
   */
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return roleService.editStatus(id, status);
  }

  /**
   * 判断是否存在
   * 
   * @param checkRoleNameVo 查询参数
   * @return Boolean
   */
  @PostMapping("/check")
  public Boolean checkRoleName(@Validated @RequestBody CheckRoleNameVo checkRoleNameVo) {
    if (checkRoleNameVo != null) {
      return roleService.checkRoleName(checkRoleNameVo.getId(), checkRoleNameVo.getRoleName());
    }
    return Boolean.FALSE;
  }

  /**
   * 角色权限保存
   * 
   * @param sysRoleVo 参数
   * @return ResponseResult<Boolean>
   */
  @PutMapping("/save")
  public ResponseResult<Boolean> save(@Validated @RequestBody SysRoleVo sysRoleVo) {
    if (sysRoleVo != null) {
      // 保存角色
      SysRoleDto entity = roleService.save(sysRoleVo);
      Boolean code = Boolean.FALSE;
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
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> roleService.deleteById(id)).collect(Collectors.toList());
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

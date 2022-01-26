package com.oner365.swagger.vo;

import java.io.Serializable;
import java.util.List;

import com.oner365.swagger.dto.SysJobDto;
import com.oner365.swagger.dto.SysRoleDto;
import com.oner365.swagger.dto.SysUserDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统用户
 *
 * @author zhaoyong
 */
@ApiModel(value = "用户信息")
public class SysUserInfoVo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户对象")
  private SysUserDto sysUser;

  @ApiModelProperty(value = "角色列表")
  private List<SysRoleDto> roleList;

  @ApiModelProperty(value = "职位列表")
  private List<SysJobDto> jobList;
  
  public SysUserInfoVo() {
    super();
  }

  /**
   * @return the sysUser
   */
  public SysUserDto getSysUser() {
    return sysUser;
  }

  /**
   * @param sysUser the sysUser to set
   */
  public void setSysUser(SysUserDto sysUser) {
    this.sysUser = sysUser;
  }

  /**
   * @return the roleList
   */
  public List<SysRoleDto> getRoleList() {
    return roleList;
  }

  /**
   * @param roleList the roleList to set
   */
  public void setRoleList(List<SysRoleDto> roleList) {
    this.roleList = roleList;
  }

  /**
   * @return the jobList
   */
  public List<SysJobDto> getJobList() {
    return jobList;
  }

  /**
   * @param jobList the jobList to set
   */
  public void setJobList(List<SysJobDto> jobList) {
    this.jobList = jobList;
  }

}

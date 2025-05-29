package com.oner365.sys.vo;

import java.io.Serializable;
import java.util.List;

import com.oner365.sys.dto.SysJobDto;
import com.oner365.sys.dto.SysRoleDto;
import com.oner365.sys.dto.SysUserDto;

/**
 * 系统用户
 *
 * @author zhaoyong
 */
public class SysUserInfoVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private SysUserDto sysUser;

    private List<SysRoleDto> roleList;

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

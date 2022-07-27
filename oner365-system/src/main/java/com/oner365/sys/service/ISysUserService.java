package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.dto.SysUserDto;
import com.oner365.sys.vo.SysUserVo;

/**
 * 用户接口
 *
 * @author zhaoyong
 */
public interface ISysUserService extends BaseService {

  /**
   * 登录
   *
   * @param userName 账号
   * @param password 密码
   * @param ip       ip
   * @return LoginUserDto
   */
  LoginUserDto login(String userName, String password, String ip);

  /**
   * 查询分页列表
   *
   * @param data 查询参数
   * @return PageInfo<SysUserDto>
   */
  PageInfo<SysUserDto> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return List<SysUserDto>
   */
  List<SysUserDto> findList(QueryCriteriaBean data);

  /**
   * 根据编号查询详情
   *
   * @param id 编号
   * @return SysUserDto
   */
  SysUserDto getById(String id);

  /**
   * 保存
   *
   * @param sysUser 用户对象
   * @return SysUserDto
   */
  SysUserDto saveUser(SysUserVo sysUser);

  /**
   * 删除
   *
   * @param id 编号
   * @return Boolean
   */
  Boolean deleteById(String id);

  /**
   * 检测用户名
   *
   * @param userId   账号id
   * @param userName 账号
   * @return Boolean
   */
  Boolean checkUserName(String userId, String userName);

  /**
   * 修改密码
   *
   * @param id       编号
   * @param password 密码
   * @return Boolean
   */
  Boolean editPassword(String id, String password);

  /**
   * 修改状态
   *
   * @param id     编号
   * @param status 状态
   * @return Boolean
   */
  Boolean editStatus(String id, StatusEnum status);

  /**
   * 更新头像信息
   *
   * @param id     编号
   * @param avatar 头像地址
   * @return SysUserDto
   */
  SysUserDto updateAvatar(String id, String avatar);

  /**
   * 更新个人信息
   *
   * @param sysUserVo 用户对象
   * @return SysUserDto
   */
  SysUserDto updateUserProfile(SysUserVo sysUserVo);
}

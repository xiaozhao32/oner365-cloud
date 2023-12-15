package com.oner365.ldap.service;

import java.util.List;

import com.oner365.ldap.entity.LdapUser;
import com.oner365.ldap.vo.LdapUserVo;

/**
 * Ldap Users 接口
 * 
 * @author zhaoyong
 */
public interface ILdapUsersService {

  /**
   * 所有用户
   * 
   * @return
   */
  List<LdapUser> findList();

  /**
   * 用户认证
   * 
   * @param userName 账号
   * @param password 密码
   * @return 是否成功
   */
  boolean authenticate(String userName, String password);

  /**
   * 根据userId查询用户信息
   *
   * @param userName 账号
   * @return LdapUser
   */
  LdapUser getUser(String userName);

  /**
   * 创建用户
   *
   * @param vo 用户对象
   * @return LdapUser
   */
  LdapUser createUser(LdapUserVo vo);

  /**
   * 修改用户
   *
   * @param vo 用户对象
   * @return LdapUser
   */
  LdapUser updateUser(LdapUserVo vo);

  /**
   * 删除用户
   * 
   * @param user 对象
   */
  void deleteUser(LdapUser user);

}

package com.oner365.swagger.controller.ldap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.ldap.ILdapUsersClient;
import com.oner365.swagger.dto.LdapUserDto;
import com.oner365.swagger.vo.LdapUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Ldap控制器
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "Ldap用户")
@RequestMapping("/ldap/users")
public class LdapUsersController {

  @Resource
  private ILdapUsersClient client;

  /**
   * 获取全部用户
   * 
   * @return 集合
   */
  @ApiOperation("1.获取全部用户")
  @ApiOperationSupport(order = 1)
  @GetMapping("/list")
  public ResponseData<List<LdapUserDto>> findList() {
    return client.findList();
  }

  /**
   * 认证用户
   * 
   * @param userName 用户名称
   * @param password 密码
   * @return 是否成功
   */
  @ApiOperation("2.认证用户")
  @ApiOperationSupport(order = 2)
  @PostMapping("/auth")
  public ResponseData<Boolean> authenticate(String userName, String password) {
    return client.auth(userName, password);
  }

  /**
   * 获取用户
   * 
   * @param id 账号
   * @return 用户对象
   */
  @ApiOperation("3.获取用户")
  @ApiOperationSupport(order = 3)
  @GetMapping("/get/{id}")
  public ResponseData<LdapUserDto> getUser(@PathVariable String id) {
    return client.getUser(id);
  }

  /**
   * 新增用户
   * 
   * @param vo 用户对象
   * @return 是否成功
   */
  @ApiOperation("4.新增用户")
  @ApiOperationSupport(order = 4)
  @PutMapping("/create")
  public ResponseData<Boolean> create(@RequestBody LdapUserVo vo) {
    return client.create(vo);
  }

  /**
   * 修改用户
   * 
   * @param vo 用户对象
   * @return 是否成功
   */
  @ApiOperation("5.修改用户")
  @ApiOperationSupport(order = 5)
  @PutMapping("/update")
  public ResponseData<Boolean> update(@RequestBody LdapUserVo vo) {
    return client.update(vo);
  }

  /**
   * 删除用户
   * 
   * @param id 账号
   * @return 是否成功
   */
  @ApiOperation("6.删除用户")
  @ApiOperationSupport(order = 6)
  @DeleteMapping("/delete/{id}")
  public ResponseData<Boolean> delete(@PathVariable String id) {
    return client.delete(id);
  }

}

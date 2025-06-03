package com.oner365.swagger.client.ldap;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.LdapUserDto;
import com.oner365.swagger.vo.LdapUserVo;

/**
 * Ldap服务 - 用户
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_LDAP, contextId = PathConstants.CONTEXT_LDAP_USERS_ID)
public interface ILdapUsersClient {

    /**
     * 认证用户
     * @param userName 用户名称
     * @param password 密码
     * @return 是否成功
     */
    @PostMapping(PathConstants.REQUEST_LDAP_USERS_AUTH)
    ResponseData<Boolean> auth(@RequestParam("userName") String userName, @RequestParam("password") String password);

    /**
     * 获取全部用户
     * @return 集合
     */
    @GetMapping(PathConstants.REQUEST_LDAP_USERS_LIST)
    ResponseData<List<LdapUserDto>> findList();

    /**
     * 获取用户
     * @param id 账号
     * @return 用户对象
     */
    @GetMapping(PathConstants.REQUEST_LDAP_USERS_GET_ID)
    ResponseData<LdapUserDto> getUser(@PathVariable("id") String id);

    /**
     * 新增用户
     * @param vo 用户对象
     * @return 是否成功
     */
    @PutMapping(PathConstants.REQUEST_LDAP_USERS_CREATE)
    ResponseData<Boolean> create(@RequestBody LdapUserVo vo);

    /**
     * 修改用户
     * @param vo 用户对象
     * @return 是否成功
     */
    @PutMapping(PathConstants.REQUEST_LDAP_USERS_UPDATE)
    ResponseData<Boolean> update(@RequestBody LdapUserVo vo);

    /**
     * 删除用户
     * @param id 账号
     * @return 是否成功
     */
    @DeleteMapping(PathConstants.REQUEST_LDAP_USERS_DELETE)
    ResponseData<Boolean> delete(@PathVariable("id") String id);

}

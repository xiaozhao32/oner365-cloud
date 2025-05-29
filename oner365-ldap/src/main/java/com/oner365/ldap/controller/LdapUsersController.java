package com.oner365.ldap.controller;

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

import com.oner365.data.web.controller.BaseController;
import com.oner365.ldap.dto.LdapUserDto;
import com.oner365.ldap.service.ILdapUsersService;
import com.oner365.ldap.vo.LdapUserVo;

/**
 * Ldap控制器
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/users")
public class LdapUsersController extends BaseController {

    @Resource
    private ILdapUsersService ldapService;

    /**
     * 获取全部用户
     * @return 集合
     */
    @GetMapping("/list")
    public List<LdapUserDto> findList() {
        return ldapService.findList();
    }

    /**
     * 认证用户
     * @param userName 用户名称
     * @param password 密码
     * @return 是否成功
     */
    @PostMapping("/auth")
    public boolean authenticate(String userName, String password) {
        return ldapService.authenticate(userName, password);
    }

    /**
     * 获取用户
     * @param userName 账号
     * @return 用户对象
     */
    @GetMapping("/get/{userName}")
    public LdapUserDto getUser(@PathVariable String userName) {
        return ldapService.getUser(userName);
    }

    /**
     * 新增用户
     * @param vo 用户对象
     * @return 是否成功
     */
    @PutMapping("/create")
    public Boolean create(@RequestBody LdapUserVo vo) {
        LdapUserDto user = getUser(vo.getCommonName());
        if (user != null) {
            logger.error("用户已存在!");
            return Boolean.FALSE;
        }

        LdapUserDto result = ldapService.createUser(vo);
        logger.info("create user:{}", result.getCommonName());
        return Boolean.TRUE;
    }

    /**
     * 修改用户
     * @param vo 用户对象
     * @return 是否成功
     */
    @PutMapping("/update")
    public Boolean update(@RequestBody LdapUserVo vo) {
        LdapUserDto user = getUser(vo.getCommonName());
        if (user == null) {
            logger.error("用户不存在!");
            return Boolean.FALSE;
        }

        LdapUserDto result = ldapService.updateUser(vo);
        logger.info("update user:{}", result.getCommonName());
        return Boolean.TRUE;
    }

    /**
     * 删除用户
     * @param userName 账号
     * @return 是否成功
     */
    @DeleteMapping("/delete/{userName}")
    public Boolean delete(@PathVariable String userName) {
        LdapUserDto user = getUser(userName);
        if (user == null) {
            return Boolean.FALSE;
        }
        ldapService.deleteUser(user);
        logger.info("delete user:{}", userName);
        return Boolean.TRUE;
    }

}

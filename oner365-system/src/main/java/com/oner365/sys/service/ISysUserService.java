package com.oner365.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.SysUser;

/**
 * 用户接口
 * @author zhaoyong
 */
public interface ISysUserService {

    /**
     * 登陆
     *
     * @param userName 账号
     * @param password 密码
     * @return Map
     */
    Map<String, Object> login(String userName, String password);

    /**
     * 查询分页列表
     *
     * @param paramJson 参数
     * @return Page
     */
    Page<SysUser> pageList(JSONObject paramJson);
    
    /**
     * 查询列表
     * @param paramJson 参数
     * @return List
     */
    List<SysUser> findList(JSONObject paramJson);

    /**
     * 根据编号查询详情
     *
     * @param id 编号
     * @return SysUser
     */
    SysUser getById(String id);

    /**
     * 保存
     *
     * @param sysUser 用户对象
     * @return SysUser
     */
    SysUser saveUser(SysUser sysUser);

    /**
     * 删除
     *
     * @param id 编号
     * @return Integer
     */
    Integer deleteById(String id);

    /**
     * 检测用户名
     *
     * @param userId   账号id
     * @param userName 账号
     * @return long
     */
    long checkUserName(String userId, String userName);

    /**
     * 修改密码
     *
     * @param id       编号
     * @param password 密码
     * @return Integer
     */
    Integer editPassword(String id, String password);

    /**
     * 修改状态
     *
     * @param id     编号
     * @param status 状态
     * @return Integer
     */
    Integer editStatus(String id, String status);

    /**
     * 更新
     *
     * @param sysUser 用户对象
     * @return SysUser
     */
    SysUser update(SysUser sysUser);

}

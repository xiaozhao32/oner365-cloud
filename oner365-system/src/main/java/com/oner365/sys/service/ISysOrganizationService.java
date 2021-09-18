package com.oner365.sys.service;

import java.util.List;

import com.oner365.sys.entity.SysOrganization;
import com.oner365.sys.entity.TreeSelect;

/**
 * 机构接口
 * @author zhaoyong
 */
public interface ISysOrganizationService {

    /**
     * 查询详情
     * 
     * @param id 编号
     * @return SysOrganization
     */
    SysOrganization getById(String id);

    /**
     * 保存
     * 
     * @param org 对象
     * @return SysOrganization
     */
    SysOrganization save(SysOrganization org);

    /**
     * 删除
     * 
     * @param id 编号
     * @return int
     */
    int deleteById(String id);

    /**
     * 检测代码
     * 
     * @param orgId 单位编号
     * @param code  代码
     * @param type  类型
     * @return long
     */
    long checkCode(String orgId, String code, String type);

    /**
     * 直接测试数据源是否连接
     * 
     * @param dstype   数据源类型
     * @param ip       ip地址
     * @param port     端口
     * @param dbname   数据源名称
     * @param username 账号
     * @param password 密码
     * @return boolean
     */
    boolean isConnection(String dstype, String ip, int port, String dbname, String username, String password);

    /**
     * 判断保存后数据源是否连接
     * 
     * @param id 编号
     * @return boolean
     */
    boolean checkConnection(String id);

    /**
     * 按父级id查询
     * 
     * @param parentId 父级编号
     * @return List
     */
    List<SysOrganization> findListByParentId(String parentId);

    /**
     * 按单位编码查询
     * 
     * @param orgCode 单位编码
     * @return SysOrganization
     */
    SysOrganization getByCode(String orgCode);

    /**
     * 下拉树结构
     * 
     * @param orgList 单位列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildTreeSelect(List<SysOrganization> orgList);

    /**
     * 下拉树
     * 
     * @param orgList 单位列表
     * @return List
     */
    List<SysOrganization> buildTree(List<SysOrganization> orgList);

    /**
     * 根据用户查询系统单位列表
     *
     * @param sysOrg 单位信息
     * @return 单位列表
     */
    List<SysOrganization> selectList(SysOrganization sysOrg);

    /**
     * 根据用户查询系统单位列表
     * 
     * @param userId 用户信息
     * @return 单位列表
     */
    List<String> selectListByUserId(String userId);

    /**
     * 修改状态
     * 
     * @param id     编号
     * @param status 状态
     * @return int
     */
    Integer changeStatus(String id, String status);

}

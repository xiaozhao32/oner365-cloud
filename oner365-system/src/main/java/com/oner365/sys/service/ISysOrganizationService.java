package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.enums.StatusEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysOrganizationDto;
import com.oner365.sys.dto.TreeSelect;
import com.oner365.sys.vo.SysOrganizationVo;

/**
 * 机构接口
 *
 * @author zhaoyong
 */
public interface ISysOrganizationService extends BaseService {

  /**
   * 查询详情
   *
   * @param id 编号
   * @return SysOrganizationDto
   */
  SysOrganizationDto getById(String id);

  /**
   * 保存
   *
   * @param org 对象
   * @return SysOrganizationDto
   */
  SysOrganizationDto save(SysOrganizationVo org);

  /**
   * 删除
   *
   * @param id 编号
   * @return Boolean
   */
  Boolean deleteById(String id);

  /**
   * 检测代码
   *
   * @param orgId 单位编号
   * @param code  代码
   * @param type  类型
   * @return Boolean
   */
  Boolean checkCode(String orgId, String code, String type);

  /**
   * 直接测试数据源是否连接
   *
   * @param ds       数据源类型
   * @param ip       ip地址
   * @param port     端口
   * @param dbname   数据源名称
   * @param username 账号
   * @param password 密码
   * @return Boolean
   */
  Boolean isConnection(String ds, String ip, int port, String dbname, String username, String password);

  /**
   * 判断保存后数据源是否连接
   *
   * @param id 编号
   * @return Boolean
   */
  Boolean checkConnection(String id);

  /**
   * 按父级id查询
   *
   * @param parentId 父级编号
   * @return List
   */
  List<SysOrganizationDto> findListByParentId(String parentId);

  /**
   * 按单位编码查询
   *
   * @param orgCode 单位编码
   * @return SysOrganizationDto
   */
  SysOrganizationDto getByCode(String orgCode);

  /**
   * 下拉树结构
   *
   * @param orgList 单位列表
   * @return 下拉树结构列表
   */
  List<TreeSelect> buildTreeSelect(List<SysOrganizationDto> orgList);

  /**
   * 下拉树
   *
   * @param orgList 单位列表
   * @return List
   */
  List<SysOrganizationDto> buildTree(List<SysOrganizationDto> orgList);

  /**
   * 查询单位列表
   *
   * @param data 查询参数
   * @return 单位列表
   */
  List<SysOrganizationDto> findList(QueryCriteriaBean data);
  
  /**
   * 查询单位列表
   *
   * @param sysOrganizationVo 查询对象
   * @return 单位列表
   */
  List<SysOrganizationDto> selectList(SysOrganizationVo sysOrganizationVo);


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
   * @return Boolean
   */
  Boolean editStatus(String id, StatusEnum status);

}

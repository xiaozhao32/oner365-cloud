package com.oner365.sys.service;

import java.util.List;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.sys.dto.SysMenuOperationDto;
import com.oner365.sys.vo.SysMenuOperationVo;

/**
 * 菜单操作权限接口
 * 
 * @author zhaoyong
 */
public interface ISysMenuOperationService extends BaseService {

  /**
   * 查询列表
   * 
   * @param data 查询参数
   * @return PageInfo
   */
  PageInfo<SysMenuOperationDto> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   * 
   * @param data 查询参数
   * @return List
   */
  List<SysMenuOperationDto> findList(QueryCriteriaBean data);

  /**
   * 查询菜单
   * 
   * @param menuId 主键
   * @return List
   */
  List<String> selectByMenuId(String menuId);

  /**
   * 查询详情
   * 
   * @param id 主键
   * @return SysMenuOperationDto
   */
  SysMenuOperationDto getById(String id);
  
  /**
   * 修改状态
   *
   * @param id     编号
   * @param status 状态
   * @return Boolean
   */
  Boolean editStatus(String id, StatusEnum status);

  /**
   * 保存
   * 
   * @param vo 对象
   * @return SysMenuOperation
   */
  SysMenuOperationDto save(SysMenuOperationVo vo);

  /**
   * 删除
   * 
   * @param id 主键
   * @return Boolean
   */
  Boolean deleteById(String id);

  /**
   * 检测是否存在
   * 
   * @param id   主键
   * @param code 类型
   * @return Boolean
   */
  Boolean checkCode(String id, String code);

}

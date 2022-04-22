package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysMenuTypeDto;
import com.oner365.sys.vo.SysMenuTypeVo;

/**
 * 菜单类型接口
 * 
 * @author zhaoyong
 */
public interface ISysMenuTypeService extends BaseService {

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return PageInfo
   */
  PageInfo<SysMenuTypeDto> pageList(QueryCriteriaBean data);

  /**
   * 查询全部
   *
   * @param data 查询参数
   * @return List
   */
  List<SysMenuTypeDto> findList(QueryCriteriaBean data);

  /**
   * 查询详情
   *
   * @param id 编号
   * @return SysMenuTypeDto
   */
  SysMenuTypeDto getById(String id);

  /**
   * 保存
   *
   * @param menuType 菜单类型对象
   * @return SysMenuTypeDto
   */
  SysMenuTypeDto save(SysMenuTypeVo menuType);

  /**
   * 修改状态
   *
   * @param id     编号
   * @param status 状态
   * @return int
   */
  int editStatus(String id, StatusEnum status);

  /**
   * 检测code
   *
   * @param id   编号
   * @param code 编号
   * @return long
   */
  long checkCode(String id, String code);

  /**
   * 按菜单类型查询
   *
   * @param menuType 菜单类型
   * @return SysMenuTypeDto
   */
  SysMenuTypeDto getMenuTypeByTypeCode(String menuType);

  /**
   * 删除
   *
   * @param id 编号
   * @return int
   */
  int deleteById(String id);

}

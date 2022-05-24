package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysDictItemDto;
import com.oner365.sys.vo.SysDictItemVo;

/**
 * 字典接口
 * 
 * @author zhaoyong
 */
public interface ISysDictItemService extends BaseService {

  /**
   * 添加
   * 
   * @param item 字典对象
   * @return SysDictItemDto
   */
  SysDictItemDto save(SysDictItemVo item);

  /**
   * 按编号查询详情
   * 
   * @param id 主键
   * @return SysDictItemDto
   */
  SysDictItemDto getById(String id);

  /**
   * 查询分页列表
   * 
   * @param data 查询参数
   * @return PageInfo
   */
  PageInfo<SysDictItemDto> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   * 
   * @param data 查询参数
   * @return List
   */
  List<SysDictItemDto> findList(QueryCriteriaBean data);

  /**
   * 检测code是否存在
   * 
   * @param id     主键
   * @param typeId 字典类别id
   * @param code   编号
   * @return Boolean
   */
  Boolean checkCode(String id, String typeId, String code);

  /**
   * 删除
   * 
   * @param id 主键
   * @return Boolean
   */
  Boolean deleteById(String id);

  /**
   * 修改状态
   * 
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  Boolean editStatus(String id, StatusEnum status);

}

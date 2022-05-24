package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.vo.SysMessageVo;

/**
 * 消息接口
 * 
 * @author zhaoyong
 */
public interface ISysMessageService extends BaseService {

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return PageInfo
   */
  PageInfo<SysMessageDto> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return List
   */
  List<SysMessageDto> findList(QueryCriteriaBean data);

  /**
   * 按编号查询详情
   *
   * @param id 主键
   * @return SysMessageDto
   */
  SysMessageDto getById(String id);

  /**
   * 添加
   *
   * @param sysMessage 对象
   * @return SysMessageDto
   */
  SysMessageDto save(SysMessageVo sysMessage);

  /**
   * 修改状态
   *
   * @param id     编号
   * @param status 状态
   * @return Boolean
   */
  Boolean editStatus(String id, MessageStatusEnum status);

  /**
   * 删除
   *
   * @param id 主键
   * @return Boolean
   */
  Boolean deleteById(String id);

}

package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.vo.SysMessageVo;

/**
 * 消息接口
 * 
 * @author zhaoyong
 */
public interface ISysMessageService extends BaseService {

  /**
   * 添加
   *
   * @param sysMessage 对象
   * @return SysMessageDto
   */
  SysMessageDto save(SysMessageVo sysMessage);

  /**
   * 按编号查询详情
   *
   * @param id 主键
   * @return SysMessageDto
   */
  SysMessageDto getById(String id);

  /**
   * 按分类查询详情
   *
   * @param messageType 类型
   * @return List
   */
  List<SysMessageDto> findList(String messageType);

  /**
   * 删除
   *
   * @param id 主键
   * @return int
   */
  int deleteById(String id);

}

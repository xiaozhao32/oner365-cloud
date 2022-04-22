package com.oner365.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 系统单位接口
 * 
 * @author zhaoyong
 */
@Repository
public interface SysOrganizationMapper {

  /**
   * 根据用户查询系统单位列表
   *
   * @param userId 用户信息
   * @return 单位列表
   */
  List<String> selectListByUserId(String userId);

}

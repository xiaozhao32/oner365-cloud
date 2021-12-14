package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysJobDto;
import com.oner365.sys.vo.SysJobVo;

/**
 * 职位接口
 * 
 * @author zhaoyong
 */
public interface ISysJobService extends BaseService {

  /**
   * 查询职位列表
   *
   * @param data 查询参数
   * @return PageInfo
   */
  PageInfo<SysJobDto> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   * 
   * @param data 查询参数
   * @return List
   */
  List<SysJobDto> findList(QueryCriteriaBean data);

  /**
   * 查询职位详情
   *
   * @param id 编号
   * @return SysJobDto
   */
  SysJobDto getById(String id);

  /**
   * 保存职位
   *
   * @param job 职位对象
   * @return SysJobDto
   */
  SysJobDto save(SysJobVo job);

  /**
   * 删除职位
   *
   * @param id 编号
   * @return int
   */
  int deleteById(String id);

  /**
   * 更新状态
   *
   * @param id     编号
   * @param status 状态
   * @return Integer
   */
  Integer editStatus(String id, String status);

}

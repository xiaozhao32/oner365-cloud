package com.oner365.elasticsearch.service;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.elasticsearch.dto.ApplicationLogDto;

/**
 * Elasticsearch 接口
 * 
 * @author zhaoyong
 *
 */
public interface IApplicationLogElasticsearchService extends BaseService {

  /**
   * 查询列表
   * 
   * @param data 查询条件参数
   * @return PageInfo<ApplicationLogDto>
   */
  PageInfo<ApplicationLogDto> pageList(QueryCriteriaBean data);

  /**
   * 查询对象
   * 
   * @param id 主键
   * @return ApplicationLogDto
   */
  ApplicationLogDto findById(String id);

  /**
   * 删除对象
   * 
   * @param id 主键
   * @return Boolean
   */
  Boolean deleteById(String id);
}

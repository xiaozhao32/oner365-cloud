package com.oner365.elasticsearch.service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.elasticsearch.dto.SampleLocationDto;
import com.oner365.elasticsearch.vo.SampleLocationVo;

/**
 * 坐标信息 - 接口
 * 
 * @author zhaoyong
 *
 */
public interface ISampleLocationElasticsearchService extends BaseService {

  /**
   * 查询列表
   * 
   * @param data 查询条件参数
   * @return PageInfo<SampleLocationDto>
   */
  PageInfo<SampleLocationDto> pageList(QueryCriteriaBean data);
  
  /**
   * 保存对象
   * 
   * @param entity 对象
   * @return SampleLocationDto
   */
  SampleLocationDto save(SampleLocationVo entity);

  /**
   * 查询对象
   * 
   * @param id 主键
   * @return SampleLocationDto
   */
  SampleLocationDto findById(String id);

  /**
   * 删除对象
   * 
   * @param id 主键
   * @return Boolean
   */
  Boolean deleteById(String id);
}

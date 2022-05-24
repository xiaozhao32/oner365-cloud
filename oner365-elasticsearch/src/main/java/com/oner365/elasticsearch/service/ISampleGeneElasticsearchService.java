package com.oner365.elasticsearch.service;

import java.util.List;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.vo.SampleGeneVo;

/**
 * Elasticsearch 接口
 * 
 * @author zhaoyong
 *
 */
public interface ISampleGeneElasticsearchService extends BaseService {

  /**
   * 查询列表
   * 
   * @param data 查询条件参数
   * @return PageInfo<SampleGeneDto>
   */
  PageInfo<SampleGeneDto> findList(QueryCriteriaBean data);

  /**
   * 保存集合
   * 
   * @param list 集合
   * @return Iterable<SampleGeneDto>
   */
  Iterable<SampleGeneDto> saveAll(List<SampleGeneVo> list);

  /**
   * 保存对象
   * 
   * @param entity 对象
   * @return SampleGeneDto
   */
  SampleGeneDto save(SampleGeneVo entity);

  /**
   * 查询对象
   * 
   * @param id 主键
   * @return SampleGeneDto
   */
  SampleGeneDto findById(String id);

  /**
   * 删除对象
   * 
   * @param id 主键
   * @return Boolean
   */
  Boolean deleteById(String id);
}

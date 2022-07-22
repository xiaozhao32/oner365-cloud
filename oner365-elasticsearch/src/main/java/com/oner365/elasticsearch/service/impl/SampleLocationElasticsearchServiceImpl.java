package com.oner365.elasticsearch.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dao.ISampleLocationElasticsearchDao;
import com.oner365.elasticsearch.dto.SampleLocationDto;
import com.oner365.elasticsearch.entity.SampleLocation;
import com.oner365.elasticsearch.service.ISampleLocationElasticsearchService;
import com.oner365.elasticsearch.vo.SampleLocationVo;
import com.oner365.util.DateUtil;

/**
 * 坐标信息 - 实现
 * 
 * @author zhaoyong
 *
 */
@Service
public class SampleLocationElasticsearchServiceImpl extends AbstractElasticsearchService
    implements ISampleLocationElasticsearchService {

  private final ISampleLocationElasticsearchDao dao;

  public SampleLocationElasticsearchServiceImpl(ISampleLocationElasticsearchDao dao) {
    this.dao = dao;
  }

  @Override
  public PageInfo<SampleLocationDto> pageList(QueryCriteriaBean data) {
    Page<SampleLocation> pageList = pageList(data, SampleLocation.class);
    return convert(pageList, SampleLocationDto.class);
  }

  @Override
  public SampleLocationDto save(SampleLocationVo vo) {
    vo.setCreateTime(DateUtil.getDate());
    SampleLocation entity = dao.save(convert(vo, SampleLocation.class));
    return convert(entity, SampleLocationDto.class);
  }

  @Override
  public SampleLocationDto findById(String id) {
    SampleLocation entity = dao.findById(id).orElse(null);
    return convert(entity, SampleLocationDto.class);
  }

  @Override
  public Boolean deleteById(String id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

}

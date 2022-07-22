package com.oner365.elasticsearch.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dao.IApplicationLogDao;
import com.oner365.elasticsearch.dto.ApplicationLogDto;
import com.oner365.elasticsearch.entity.ApplicationLog;
import com.oner365.elasticsearch.service.IApplicationLogElasticsearchService;

/**
 * SampleGeneElasticsearch实现类
 * 
 * @author zhaoyong
 */
@Service
public class ApplicationLogElasticsearchServiceImpl extends AbstractElasticsearchService
    implements IApplicationLogElasticsearchService {

  private final IApplicationLogDao dao;

  public ApplicationLogElasticsearchServiceImpl(IApplicationLogDao dao) {
    this.dao = dao;
  }

  @Override
  public PageInfo<ApplicationLogDto> pageList(QueryCriteriaBean data) {
    Page<ApplicationLog> pageList = pageList(data, ApplicationLog.class);
    return convert(pageList, ApplicationLogDto.class);
  }

  @Override
  public ApplicationLogDto findById(String id) {
    Optional<ApplicationLog> optional = dao.findById(id);
    return convert(optional.orElse(null), ApplicationLogDto.class);
  }

  @Override
  public Boolean deleteById(String id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

}

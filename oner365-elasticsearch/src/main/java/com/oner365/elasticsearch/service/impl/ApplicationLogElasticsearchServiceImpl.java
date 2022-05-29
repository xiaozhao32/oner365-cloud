package com.oner365.elasticsearch.service.impl;

import java.util.Optional;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository.OperationsCallback;
import org.springframework.stereotype.Service;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.elasticsearch.dao.IApplicationLogDao;
import com.oner365.elasticsearch.dto.ApplicationLogDto;
import com.oner365.elasticsearch.entity.ApplicationLog;
import com.oner365.elasticsearch.service.IApplicationLogElasticsearchService;
import com.oner365.util.DataUtils;

/**
 * SampleGeneElasticsearch实现类
 * 
 * @author zhaoyong
 */
@Service
public class ApplicationLogElasticsearchServiceImpl implements IApplicationLogElasticsearchService {

  private final ElasticsearchOperations operations;

  private final IApplicationLogDao dao;

  public ApplicationLogElasticsearchServiceImpl(IApplicationLogDao dao, ElasticsearchOperations operations) {
    this.dao = dao;
    this.operations = operations;
  }

  @Override
  @SuppressWarnings("unchecked")
  public PageInfo<ApplicationLogDto> pageList(QueryCriteriaBean data) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        queryBuilder.filter(QueryBuilders.termQuery(entity.getKey(), entity.getVal()));
      }
    });

    // 版本更新
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
        .withPageable(QueryUtils.buildPageRequest(data)).build();
    SearchHits<ApplicationLog> searchHits = execute(search -> search.search(searchQuery, ApplicationLog.class));
    SearchPage<ApplicationLog> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    Page<ApplicationLog> pageList = (Page<ApplicationLog>) SearchHitSupport.unwrapSearchHits(page);
    return convert(pageList, ApplicationLogDto.class);
  }

  public <R> R execute(OperationsCallback<R> callback) {
    return callback.doWithOperations(operations);
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

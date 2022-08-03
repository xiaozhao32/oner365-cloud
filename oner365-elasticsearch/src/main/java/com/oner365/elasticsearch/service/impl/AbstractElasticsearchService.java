package com.oner365.elasticsearch.service.impl;

import javax.annotation.Resource;

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

import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.util.DataUtils;

/**
 * Abstract ElasticsearchService 抽象类
 * 
 * @author zhaoyong
 */
public class AbstractElasticsearchService {
  
  @Resource
  private ElasticsearchOperations operations;
  
  @SuppressWarnings("unchecked")
  public <T> Page<T> pageList(QueryCriteriaBean data, Class<T> clazz) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        queryBuilder.filter(QueryBuilders.termQuery(entity.getKey(), entity.getVal()));
      }
    });

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(queryBuilder)
        .withPageable(QueryUtils.buildPageRequest(data))
        .withSort(QueryUtils.buildSortRequest(data.getOrder()))
        .build();
    SearchHits<T> searchHits = execute(search -> search.search(searchQuery, clazz));
    if (searchQuery != null && searchHits != null) {
      SearchPage<T> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
      return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }
    return null;
  }
  
  private <R> R execute(OperationsCallback<R> callback) {
    return callback.doWithOperations(operations);
  }

}

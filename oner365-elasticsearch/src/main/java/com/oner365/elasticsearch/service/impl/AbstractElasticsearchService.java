package com.oner365.elasticsearch.service.impl;

import javax.annotation.Resource;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;

/**
 * Abstract ElasticsearchService 抽象类
 * 
 * @author zhaoyong
 */
public class AbstractElasticsearchService {
  
  @Resource
  private ElasticsearchRestTemplate elasticsearchRestTemplate;
  
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
    SearchHits<T> searchHits = elasticsearchRestTemplate.search(searchQuery, clazz);
    SearchPage<T> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
  }
  
}

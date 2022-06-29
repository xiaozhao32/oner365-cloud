package com.oner365.elasticsearch.service.impl;

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
import com.oner365.elasticsearch.dao.ISampleLocationElasticsearchDao;
import com.oner365.elasticsearch.dto.SampleLocationDto;
import com.oner365.elasticsearch.entity.SampleLocation;
import com.oner365.elasticsearch.service.ISampleLocationElasticsearchService;
import com.oner365.elasticsearch.vo.SampleLocationVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * 坐标信息 - 实现
 * 
 * @author zhaoyong
 *
 */
@Service
public class SampleLocationElasticsearchServiceImpl implements ISampleLocationElasticsearchService {

  private final ElasticsearchOperations operations;

  private final ISampleLocationElasticsearchDao dao;

  public SampleLocationElasticsearchServiceImpl(ISampleLocationElasticsearchDao dao,
      ElasticsearchOperations operations) {
    this.dao = dao;
    this.operations = operations;
  }

  public <R> R execute(OperationsCallback<R> callback) {
    return callback.doWithOperations(operations);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PageInfo<SampleLocationDto> pageList(QueryCriteriaBean data) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        queryBuilder.filter(QueryBuilders.termQuery(entity.getKey(), entity.getVal()));
      }
    });

    // 版本更新
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
        .withPageable(QueryUtils.buildPageRequest(data)).build();
    SearchHits<SampleLocation> searchHits = execute(search -> search.search(searchQuery, SampleLocation.class));
    SearchPage<SampleLocation> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    Page<SampleLocation> pageList = (Page<SampleLocation>) SearchHitSupport.unwrapSearchHits(page);
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

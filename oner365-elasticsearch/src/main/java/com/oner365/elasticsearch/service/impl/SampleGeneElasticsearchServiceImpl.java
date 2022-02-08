package com.oner365.elasticsearch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import com.oner365.elasticsearch.dao.ISampleGeneElasticsearchDao;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.entity.SampleGene;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.elasticsearch.vo.SampleGeneVo;
import com.oner365.util.DataUtils;

/**
 * SampleGeneElasticsearchService实现类
 * 
 * @author zhaoyong
 */
@Service
public class SampleGeneElasticsearchServiceImpl implements ISampleGeneElasticsearchService {

  private final ElasticsearchOperations operations;

  private final ISampleGeneElasticsearchDao dao;

  public SampleGeneElasticsearchServiceImpl(ISampleGeneElasticsearchDao dao, ElasticsearchOperations operations) {
    this.dao = dao;
    this.operations = operations;
  }

  @Override
  @SuppressWarnings("unchecked")
  public PageInfo<SampleGeneDto> findList(QueryCriteriaBean data) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        queryBuilder.filter(QueryBuilders.termQuery(entity.getKey(), entity.getVal()));
      }
    });

    // 版本更新
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(QueryUtils.buildPageRequest(data))
        .build();
    SearchHits<SampleGene> searchHits = execute(search -> search.search(searchQuery, SampleGene.class));
    SearchPage<SampleGene> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    Page<SampleGene> pageList = (Page<SampleGene>) SearchHitSupport.unwrapSearchHits(page);
    return convert(pageList, SampleGeneDto.class);
  }

  public <R> R execute(OperationsCallback<R> callback) {
    return callback.doWithOperations(operations);
  }

  @Override
  public Iterable<SampleGeneDto> saveAll(List<SampleGeneVo> voList) {
    Iterable<SampleGene> iterable = dao.saveAll(convert(voList, SampleGene.class));
    List<SampleGene> list = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    return convert(list, SampleGeneDto.class);
  }

  @Override
  public SampleGeneDto save(SampleGeneVo vo) {
    SampleGene entity = dao.save(convert(vo, SampleGene.class));
    return convert(entity, SampleGeneDto.class);
  }

  @Override
  public SampleGeneDto findById(String id) {
    Optional<SampleGene> optional = dao.findById(id);
    return convert(optional.orElse(null), SampleGeneDto.class);
  }

  @Override
  public void deleteById(String id) {
    dao.deleteById(id);
  }

}

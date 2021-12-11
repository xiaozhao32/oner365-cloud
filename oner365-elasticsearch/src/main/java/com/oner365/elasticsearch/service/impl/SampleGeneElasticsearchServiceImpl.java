package com.oner365.elasticsearch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository.OperationsCallback;
import org.springframework.stereotype.Service;

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
  public Page<SampleGeneDto> findList(QueryCriteriaBean data) {
    Pageable pageable = QueryUtils.buildPageRequest(data);

    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        queryBuilder.filter(QueryBuilders.termQuery(entity.getKey(), entity.getVal()));
      }
    });

    // 版本更新
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable)
        .build();
    SearchHits<SampleGene> searchHits = execute(search -> search.search(searchQuery, SampleGene.class));
    SearchPage<SampleGene> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    Page<SampleGene> pageList = (Page<SampleGene>) SearchHitSupport.unwrapSearchHits(page);
    return convertDto(pageList);
  }

  public <R> R execute(OperationsCallback<R> callback) {
    return callback.doWithOperations(operations);
  }

  @Override
  public Iterable<SampleGeneDto> saveAll(List<SampleGeneVo> voList) {
    List<SampleGene> list = voList.stream().map(e -> toPojo(e)).collect(Collectors.toList());
    return convertDto(dao.saveAll(list));
  }

  @Override
  public SampleGeneDto save(SampleGeneVo vo) {
    SampleGene entity = toPojo(vo);
    return convertDto(dao.save(entity));
  }

  /**
   * 转换对象
   * 
   * @return SampleGeneDto
   */
  private SampleGene toPojo(SampleGeneVo vo) {
    SampleGene result = new SampleGene();
    result.setId(vo.getId());
    result.setGeneInfo(vo.getGeneInfo());
    result.setGeneList(vo.getGeneList());
    result.setGeneType(vo.getGeneType());
    result.setInitServerNo(vo.getInitServerNo());
    result.setMatchJson(vo.getMatchJson());
    result.setPersonCode(vo.getPersonCode());
    return result;
  }

  @Override
  public SampleGeneDto findById(String id) {
    Optional<SampleGene> optional = dao.findById(id);
    return convertDto(optional.orElse(null));
  }

  @Override
  public void deleteById(String id) {
    dao.deleteById(id);
  }

}

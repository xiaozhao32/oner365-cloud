package com.oner365.elasticsearch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dao.ISampleGeneElasticsearchDao;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.entity.SampleGene;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.elasticsearch.vo.SampleGeneVo;

/**
 * SampleGeneElasticsearch实现类
 * 
 * @author zhaoyong
 */
@Service
public class SampleGeneElasticsearchServiceImpl extends AbstractElasticsearchService
    implements ISampleGeneElasticsearchService {

  private final ISampleGeneElasticsearchDao dao;

  public SampleGeneElasticsearchServiceImpl(ISampleGeneElasticsearchDao dao) {
    this.dao = dao;
  }

  @Override
  public PageInfo<SampleGeneDto> pageList(QueryCriteriaBean data) {
    Page<SampleGene> pageList = pageList(data, SampleGene.class);
    return convert(pageList, SampleGeneDto.class);
  }

  @Override
  public List<SampleGeneDto> saveAll(List<SampleGeneVo> voList) {
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
  public Boolean deleteById(String id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

}

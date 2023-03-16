package com.oner365.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.oner365.elasticsearch.entity.SampleGene;

/**
 * Elasticsearch Repository
 * @author zhaoyong
 *
 */
public interface ISampleGeneElasticsearchDao extends ElasticsearchRepository<SampleGene, String> {


}

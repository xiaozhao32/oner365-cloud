package com.oner365.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.oner365.elasticsearch.entity.SampleLocation;

/**
 * SampleLocation Repository
 * 
 * @author zhaoyong
 *
 */
@Repository
public interface ISampleLocationElasticsearchDao extends ElasticsearchRepository<SampleLocation, String> {

}

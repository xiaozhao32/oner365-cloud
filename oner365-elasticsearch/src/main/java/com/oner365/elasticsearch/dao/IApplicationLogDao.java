package com.oner365.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.oner365.elasticsearch.entity.ApplicationLog;

/**
 * Elasticsearch Repository
 * 
 * @author zhaoyong
 *
 */
public interface IApplicationLogDao extends ElasticsearchRepository<ApplicationLog, String> {

}

package com.oner365.elasticsearch.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.oner365.elasticsearch.entity.SampleGene;

/**
 * Elasticsearch 接口
 * 
 * @author zhaoyong
 *
 */
public interface ISampleGeneElasticsearchService {

    /**
     * 查询列表
     * 
     * @param paramJson 查询条件参数
     * @return Page<SampleGene>
     */
    Page<SampleGene> findList(JSONObject paramJson);

    /**
     * 保存集合
     * 
     * @param list 集合
     * @return Iterable
     */
    Iterable<SampleGene> saveAll(List<SampleGene> list);

    /**
     * 保存对象
     * 
     * @param entity 对象
     * @return SampleGene
     */
    SampleGene save(SampleGene entity);

    /**
     * 查询对象
     * 
     * @param id 主键
     * @return SampleGene
     */
    SampleGene findById(String id);

    /**
     * 删除对象
     * 
     * @param id 主键
     */
    void deleteById(String id);
}

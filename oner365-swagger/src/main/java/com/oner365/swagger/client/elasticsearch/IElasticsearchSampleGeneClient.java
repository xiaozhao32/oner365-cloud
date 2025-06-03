package com.oner365.swagger.client.elasticsearch;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SampleGeneDto;
import com.oner365.swagger.vo.SampleGeneVo;

/**
 * Elasticsearch服务 - 信息
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_ELASTICSEARCH,
        contextId = PathConstants.CONTEXT_ELASTICSEARCH_SAMPLE_GENE_ID)
public interface IElasticsearchSampleGeneClient {

    /**
     * 列表
     * @param data 查询条件
     * @return ResponseData<PageInfo<SampleGeneDto>>
     */
    @PostMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_LIST)
    ResponseData<PageInfo<SampleGeneDto>> list(@RequestBody QueryCriteriaBean data);

    /**
     * id查询
     * @param id 主键
     * @return ResponseData<SampleGeneDto>
     */
    @GetMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_GET_ID)
    ResponseData<SampleGeneDto> get(@PathVariable("id") String id);

    /**
     * 保存
     * @param sampleGeneVo 基因对象
     * @return ResponseData<SampleGeneDto>
     */
    @PutMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_SAVE)
    ResponseData<SampleGeneDto> save(@RequestBody SampleGeneVo sampleGeneVo);

    /**
     * 删除
     * @param ids 主键
     * @return ResponseData<List<Boolean>>
     */
    @DeleteMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_DELETE)
    ResponseData<List<Boolean>> delete(@RequestBody String... ids);

}

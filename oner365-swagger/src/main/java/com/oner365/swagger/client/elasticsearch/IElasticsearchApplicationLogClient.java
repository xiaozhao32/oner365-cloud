package com.oner365.swagger.client.elasticsearch;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.ApplicationLogDto;

/**
 * Elasticsearch服务 - 应用日志
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_ELASTICSEARCH,
        contextId = PathConstants.CONTEXT_ELASTICSEARCH_APPLICATION_LOG_ID)
public interface IElasticsearchApplicationLogClient {

    /**
     * 列表
     * @param data 查询条件
     * @return ResponseData<PageInfo<ApplicationLogDto>>
     */
    @PostMapping(PathConstants.REQUEST_ELASTICSEARCH_APPLICATION_LOG_LIST)
    ResponseData<PageInfo<ApplicationLogDto>> list(@RequestBody QueryCriteriaBean data);

    /**
     * id查询
     * @param id 主键
     * @return ResponseData<ApplicationLogDto>
     */
    @GetMapping(PathConstants.REQUEST_ELASTICSEARCH_APPLICATION_LOG_GET_ID)
    ResponseData<ApplicationLogDto> get(@PathVariable("id") String id);

    /**
     * 删除
     * @param ids 主键
     * @return ResponseData<List<Boolean>>
     */
    @DeleteMapping(PathConstants.REQUEST_ELASTICSEARCH_APPLICATION_LOG_DELETE)
    ResponseData<List<Boolean>> delete(@RequestBody String... ids);

}

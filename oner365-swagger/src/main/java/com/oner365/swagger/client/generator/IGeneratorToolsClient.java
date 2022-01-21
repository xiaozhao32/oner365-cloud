package com.oner365.swagger.client.generator;

import org.springframework.cloud.openfeign.FeignClient;

import com.oner365.swagger.constants.PathConstants;

/**
 * 生成代码服务 - 生成管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_GENERATOR, contextId = PathConstants.CONTEXT_GENERATOR_TOOLS_ID)
public interface IGeneratorToolsClient {

}

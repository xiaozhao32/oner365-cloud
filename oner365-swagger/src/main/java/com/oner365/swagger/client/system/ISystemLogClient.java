package com.oner365.swagger.client.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.oner365.swagger.constants.PathConstants;

/**
 * 系统服务 - 日志管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_LOG_ID)
public interface ISystemLogClient {

}

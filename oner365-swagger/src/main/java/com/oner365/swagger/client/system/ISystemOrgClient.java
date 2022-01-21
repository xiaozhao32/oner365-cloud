package com.oner365.swagger.client.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.oner365.swagger.constants.PathConstants;

/**
 * 系统服务 - 机构管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_ORG_ID)
public interface ISystemOrgClient {

}

package com.oner365.swagger.client.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.oner365.swagger.constants.PathConstants;

/**
 * 系统服务 - 菜单类型管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_MENU_TYPE_ID)
public interface ISystemMenuTypeClient {

}

package com.oner365.gateway.rabbitmq.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oner365.gateway.rabbitmq.ISyncRouteService;
import com.oner365.gateway.service.DynamicRouteService;

/**
 * ISyncRouteService实现类
 *
 * @author zhaoyong
 */
@Service
public class SyncRoutServiceImpl implements ISyncRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncRoutServiceImpl.class);

    @Resource
    private DynamicRouteService dynamicRouteService;

    @Override
    public void syncRoute(String gatewayIp) {
        LOGGER.info("MQ pull: {}", gatewayIp);
        dynamicRouteService.refreshRoute();
    }

}

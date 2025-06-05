package com.oner365.swagger.client.dubbo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.swagger.constants.PathConstants;

/**
 * Dubbo服务 - 消费
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_DUBBO_CONSUMER, contextId = PathConstants.CONTEXT_DUBBO_CONSUMER_ID)
public interface IDubboConsumerClient {

    /**
     * 发送消息
     * @param message 消息
     * @return String
     */
    @GetMapping(PathConstants.REQUEST_DUBBO_CONSUMER_MESSAGE_SEND)
    String send(@RequestParam("message") String message);

}

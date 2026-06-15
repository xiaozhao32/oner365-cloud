package com.oner365.swagger.client.pulsar;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;

/**
 * Pulsar服务 - 消费
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_PULSAR, contextId = PathConstants.CONTEXT_PULSAR_CONSUMER_ID)
public interface IPulsarConsumerClient {

    /**
     * 发送消息
     * @param message 消息
     * @return ResponseData<String>
     */
    @GetMapping(PathConstants.REQUEST_PULSAR_CONSUMER_MESSAGE_SEND)
    ResponseData<String> send(@RequestParam String message);

}

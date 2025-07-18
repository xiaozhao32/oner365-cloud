package com.oner365.swagger.client.mqtt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;

/**
 * Mqtt服务 - 消费
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_MQTT, contextId = PathConstants.CONTEXT_MQTT_CONSUMER_ID)
public interface IMqttConsumerClient {

    /**
     * 发送消息
     * @param message 消息
     * @return ResponseData<JSONObject>
     */
    @GetMapping(PathConstants.REQUEST_MQTT_CONSUMER_MESSAGE_SEND)
    ResponseData<JSONObject> send(@RequestParam("message") String message);

}

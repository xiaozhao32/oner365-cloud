package com.oner365.rocketmq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 消息接收
 *
 * @author zhaoyong
 */
@Service
@RocketMQMessageListener(topic = "${rocketmq.consumer.topic}", selectorExpression = "*",
        consumerGroup = "${rocketmq.producer.group}")
public class RocketmqConsumerListener implements RocketMQListener<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketmqConsumerListener.class);

    @Override
    public void onMessage(String message) {
        // 处理返回数据
        LOGGER.info("Rocketmq Message received: {}", message);
    }

}

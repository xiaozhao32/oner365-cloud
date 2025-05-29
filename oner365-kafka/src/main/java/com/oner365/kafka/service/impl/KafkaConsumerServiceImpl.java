package com.oner365.kafka.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.kafka.config.properties.KafkaProperties;
import com.oner365.kafka.constants.KafkaConstants;
import com.oner365.kafka.service.IKafkaConsumerService;

/**
 * IKafkaConsumerService实现类
 *
 * @author zhaoyong
 */
@Service
public class KafkaConsumerServiceImpl implements IKafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    @Resource
    private KafkaProperties kafkaProperties;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public ResultEnum convertAndSend(Object message) {
        boolean isContains = StringUtils.contains(kafkaProperties.getTopics(), KafkaConstants.TOPIC_1);
        if (!isContains) {
            LOGGER.error("spring.kafka.topics= " + KafkaConstants.TOPIC_1 + " is not config.");
            return ResultEnum.ERROR;
        }

        try {
            // 发送到 Topic1
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(KafkaConstants.TOPIC_1, message);
            future.addCallback(new ListenableFutureCallback<Object>() {

                @Override
                public void onSuccess(Object result) {
                    LOGGER.info("Kafka callback success:{}", result);
                }

                @Override
                public void onFailure(Throwable e) {
                    LOGGER.error("Kafka callback error:", e);
                }

            });
            return ResultEnum.SUCCESS;
        }
        catch (Exception e) {
            LOGGER.error("convertAndSend error:", e);
        }
        return ResultEnum.ERROR;
    }

}

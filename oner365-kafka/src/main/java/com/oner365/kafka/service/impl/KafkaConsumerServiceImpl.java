package com.oner365.kafka.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.oner365.common.enums.ResultEnum;
import com.oner365.kafka.config.properties.KafkaProperties;
import com.oner365.kafka.service.IKafkaConsumerService;

/**
 * IKafkaConsumerService实现类
 *
 * @author zhaoyong
 */
@Service
public class KafkaConsumerServiceImpl implements IKafkaConsumerService {

    private final Logger logger = LoggerFactory.getLogger(IKafkaConsumerService.class);

    @Resource
    private KafkaProperties kafkaProperties;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public ResultEnum convertAndSend(Object message) {
        try {
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(kafkaProperties.getTopic(),
                    message);
            future.addCallback(new ListenableFutureCallback<Object>() {

                @Override
                public void onSuccess(Object result) {
                    logger.info("Kafka callback success:{}", result);
                }

                @Override
                public void onFailure(Throwable e) {
                    logger.error("Kafka callback error:", e);
                }

            });
            return ResultEnum.SUCCESS;
        } catch (Exception e) {
            logger.error("convertAndSend error:", e);
        }
        return ResultEnum.ERROR;
    }

}

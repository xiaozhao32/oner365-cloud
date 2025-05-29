package com.oner365.monitor.rabbitmq.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.oner365.api.constants.ScheduleTaskConstants;
import com.oner365.api.rabbitmq.dto.InvokeParamDto;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.redis.RedisCache;
import com.oner365.monitor.rabbitmq.IScheduleSendTaskService;

/**
 * IScheduleTaskMQService实现类
 *
 * @author zhaoyong
 */
@Service
public class ScheduleSendTaskServiceImpl implements IScheduleSendTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleSendTaskServiceImpl.class);

    @Resource
    private RedisCache redisCache;

    @Resource
    private AmqpTemplate rabbitTemplate;

    @Override
    public void pullTask(InvokeParamDto invokeParamDto) {
        if (redisCache.lock(ScheduleTaskConstants.SCHEDULE_TASK_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            LOGGER.info("MQ push: {}", invokeParamDto);
            rabbitTemplate.convertAndSend(ScheduleTaskConstants.SCHEDULE_TASK_QUEUE_TYPE,
                    ScheduleTaskConstants.SCHEDULE_TASK_QUEUE_KEY, invokeParamDto);
        }
    }

}

package com.oner365.api.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oner365.api.constants.ScheduleTaskConstants;
import com.oner365.api.rabbitmq.IScheduleExecuteService;
import com.oner365.api.rabbitmq.dto.SysTaskLogDto;
import com.oner365.api.rabbitmq.dto.UpdateTaskExecuteSatusDto;

/**
 * ScheduleExecuteMQService实现类
 * 
 * @author zhaoyong
 */
@Service
public class ScheduleExecuteServiceImpl implements IScheduleExecuteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleExecuteServiceImpl.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Override
    public void updateTaskExecuteStatus(UpdateTaskExecuteSatusDto updateTaskExecuteSatusDto) {
        LOGGER.info("MQ updateTaskExecuteStatus push: {}", updateTaskExecuteSatusDto);
        rabbitTemplate.convertAndSend(ScheduleTaskConstants.TASK_UPDATE_STATUS_QUEUE_TYPE,
                ScheduleTaskConstants.TASK_UPDATE_STATUS_QUEUE_KEY, updateTaskExecuteSatusDto);
    }

    @Override
    public void saveExecuteTaskLog(SysTaskLogDto sysTaskLogDto) {
        LOGGER.info("MQ updateTaskExecuteStatus push: {}", sysTaskLogDto);
        rabbitTemplate.convertAndSend(ScheduleTaskConstants.SAVE_TASK_LOG_QUEUE_TYPE,
                ScheduleTaskConstants.SAVE_TASK_LOG_QUEUE_KEY, sysTaskLogDto);
    }

}

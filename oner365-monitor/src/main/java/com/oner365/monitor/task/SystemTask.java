package com.oner365.monitor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.oner365.api.rabbitmq.dto.InvokeParamDto;
import com.oner365.monitor.entity.InvokeParam;
import com.oner365.monitor.rabbitmq.IScheduleSendTaskService;

/**
 * 定时任务调度测试
 * 
 * @author liutao
 */
@Component("systemTask")
public class SystemTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTask.class);

    @Autowired
    private IScheduleSendTaskService service;

    public void taskRun(InvokeParam param) {
        LOGGER.info("执行【{}】定时任务: {}", param.getTaskServerName(), param);
        service.pullTask(JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(param)), InvokeParamDto.class));
    }
}

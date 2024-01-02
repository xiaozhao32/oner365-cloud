package com.oner365.monitor.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource
    private IScheduleSendTaskService service;
    
    public void taskParams(String params) {
      LOGGER.info("执行有参方法：{}", params);
    }

    public void taskNoParams() {
      LOGGER.info("执行无参方法");
    }

    public void taskRun(InvokeParam param) {
        LOGGER.info("执行【{}】定时任务: {}", param.getTaskServerName(), param);
        service.pullTask(JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(param)), InvokeParamDto.class));
    }
}

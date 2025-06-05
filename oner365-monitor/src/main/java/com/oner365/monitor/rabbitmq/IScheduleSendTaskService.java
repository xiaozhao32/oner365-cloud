package com.oner365.monitor.rabbitmq;

import com.oner365.api.rabbitmq.dto.InvokeParamDto;

/**
 * 定时任务队列
 *
 * @author liutao
 *
 */
public interface IScheduleSendTaskService {

    /**
     * 发送定时任务到各服务
     * @param invokeParamDto 对象
     */
    void pullTask(InvokeParamDto invokeParamDto);

}

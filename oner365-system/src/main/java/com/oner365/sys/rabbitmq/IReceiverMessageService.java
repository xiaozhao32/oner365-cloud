package com.oner365.sys.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.oner365.sys.constants.SysMessageConstants;

/**
 * 消息接收
 *
 * QueueBinding 监听绑定队列
 * value: @Queue(value = ""): 绑定队列类型名称
 * exchange: @Exchange(value = "", type = "")交换器名称
 *         type: 交换器类型
 *             ExchangeTypes.DIRECT  指定
 *             ExchangeTypes.TOPIC   群发
 *             ExchangeTypes.FANOUT  广播
 * key:路由键值
 *
 * @author zhaoyong
 */
@Component
public interface IReceiverMessageService {

    /**
     * 消息接口
     * @param message 消息
     */
    @RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = SysMessageConstants.QUEUE_NAME, autoDelete = "false"),
                exchange = @Exchange(value = SysMessageConstants.QUEUE_TYPE),
                key = SysMessageConstants.QUEUE_KEY
        )
    )
    void receiver(String message);

}

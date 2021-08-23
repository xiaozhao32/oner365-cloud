package com.oner365.gateway.rabbitmq;

import com.oner365.gateway.constants.GatewayConstants;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 同步路由数据
 *
 * @author liutao
 */
@Component
public interface ISyncRouteService {

    /**
     * 同步路由数据
     *
     * @param gatewayIp 网关ip
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = GatewayConstants.QUEUE_NAME, autoDelete = "false"),
                    exchange = @Exchange(value = GatewayConstants.QUEUE_TYPE, type = ExchangeTypes.FANOUT),
                    key = GatewayConstants.QUEUE_KEY
            )
    )
    void syncRoute(String gatewayIp);
}

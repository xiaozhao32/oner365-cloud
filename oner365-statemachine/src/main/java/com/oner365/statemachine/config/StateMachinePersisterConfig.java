package com.oner365.statemachine.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;

import com.oner365.statemachine.enums.OrderEventEnum;
import com.oner365.statemachine.enums.OrderStateEnum;

/**
 * 状态机配置
 *
 * @author zhaoyong
 *
 */
@Configuration
public class StateMachinePersisterConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Redis持久化配置
     */
    @Bean
    RedisStateMachinePersister<OrderStateEnum, OrderEventEnum> persister() {
        RedisStateMachineContextRepository<OrderStateEnum, OrderEventEnum> repository = new RedisStateMachineContextRepository<>(
                redisConnectionFactory);
        return new RedisStateMachinePersister<>(new RepositoryStateMachinePersist<>(repository));
    }

}

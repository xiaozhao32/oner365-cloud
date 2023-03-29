package com.oner365.statemachine.config;

import java.util.EnumSet;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.oner365.statemachine.action.OrderPayAction;
import com.oner365.statemachine.enums.OrderEventEnum;
import com.oner365.statemachine.enums.OrderStateEnum;
import com.oner365.statemachine.guard.OrderCheckGuard;

/**
 * 状态机配置
 * 
 * @author zhaoyong
 *
 */
@Configuration
@EnableStateMachine
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStateEnum, OrderEventEnum> {
  
  @Resource
  private OrderCheckGuard orderCheckGuard;
  
  @Resource
  private OrderPayAction orderPayAction;

  /**
   * 初始化状态机状态
   */
  @Override
  public void configure(StateMachineStateConfigurer<OrderStateEnum, OrderEventEnum> states) throws Exception {
    // 初始状态待支付
    states.withStates().initial(OrderStateEnum.UNPAY)
        // 添加支付状态
        .states(EnumSet.allOf(OrderStateEnum.class));
  }

  /**
   * 状态机变更 来源状态source，目标状态target，触发事件event
   */
  @Override
  public void configure(StateMachineTransitionConfigurer<OrderStateEnum, OrderEventEnum> transitions) throws Exception {
    transitions.withExternal()
        // 支付事件：待支付 -> 待收货
        .source(OrderStateEnum.UNPAY).target(OrderStateEnum.WAIT_RECEIVE).event(OrderEventEnum.PAY)
        .guard(orderCheckGuard)
        .action(orderPayAction)
        .and().withExternal()
        // 收货事件：待收货 -> 完成
        .source(OrderStateEnum.WAIT_RECEIVE).target(OrderStateEnum.FINISHED).event(OrderEventEnum.RECEIVE);
  }
}

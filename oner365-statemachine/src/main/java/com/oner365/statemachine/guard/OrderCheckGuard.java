package com.oner365.statemachine.guard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Service;

import com.oner365.statemachine.constants.StatemachineConstants;
import com.oner365.statemachine.entity.Order;
import com.oner365.statemachine.enums.OrderEventEnum;
import com.oner365.statemachine.enums.OrderStateEnum;

/**
 * 状态机 - Guard
 * 
 * @author zhaoyong
 *
 */
@Service
public class OrderCheckGuard implements Guard<OrderStateEnum, OrderEventEnum> {

  private final Logger logger = LoggerFactory.getLogger(OrderCheckGuard.class);

  @Override
  public boolean evaluate(StateContext<OrderStateEnum, OrderEventEnum> context) {
    // 检查订单
    Order order = (Order) context.getMessage().getHeaders().get(StatemachineConstants.HEADER_NAME);
    logger.info("检查订单: {}", order.toString());
    return true;
  }

}

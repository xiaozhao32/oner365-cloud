package com.oner365.statemachine.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import com.oner365.statemachine.constants.StatemachineConstants;
import com.oner365.statemachine.entity.Order;
import com.oner365.statemachine.enums.OrderEventEnum;
import com.oner365.statemachine.enums.OrderStateEnum;

/**
 * 状态机 - Action
 *
 * @author zhaoyong
 *
 */
@Service
public class OrderPayAction implements Action<OrderStateEnum, OrderEventEnum> {

  private final Logger logger = LoggerFactory.getLogger(OrderPayAction.class);

  @Override
  public void execute(StateContext<OrderStateEnum, OrderEventEnum> context) {
    RuntimeException exception = (RuntimeException) context.getException();
    if (exception != null) {
      // 输出状态机异常
      context.getStateMachine().getExtendedState().getVariables().put(RuntimeException.class, exception);
      return;
    }
    // 处理订单
    Order order = (Order) context.getMessage().getHeaders().get(StatemachineConstants.HEADER_NAME);
    if (order == null) {
      logger.error("处理失败 订单不存在: {}", context.getMessage());
      return;
    }
    logger.info("处理订单 id: {}, state: {}", order.getId(), order.getOrderState());
  }

}

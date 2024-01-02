package com.oner365.statemachine.controller;

import javax.annotation.Resource;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.web.controller.BaseController;
import com.oner365.statemachine.constants.StatemachineConstants;
import com.oner365.statemachine.entity.Order;
import com.oner365.statemachine.enums.OrderEventEnum;
import com.oner365.statemachine.enums.OrderStateEnum;
import com.oner365.statemachine.vo.OrderVo;

/**
 * 订单状态机
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

  @Resource
  private StateMachine<OrderStateEnum, OrderEventEnum> stateMachine;

  @Resource
  private StateMachinePersister<OrderStateEnum, OrderEventEnum, String> stateMachinePersister;

  /**
   * 订单状态机测试
   *
   * @return String
   */
  @GetMapping("/test")
  public ResponseResult<OrderVo> index(Integer orderId) {
    if (orderId == null) {
      return ResponseResult.error("订单号不能为空");
    }

    Order order = getOrder(orderId);
    return ResponseResult.success(getOrderVo(order));
  }

  /**
   * 发送事件返回订单结果
   *
   * @param order 订单对象
   * @return OrderVo
   */
  private OrderVo getOrderVo(Order order) {
    OrderVo result = new OrderVo();
    result.setId(order.getId());
    try {
      // 流程开始
      stateMachine.start();
      // 已支付
      boolean payResult = sendEvent(order, OrderEventEnum.PAY);
      // 收货
      boolean receiveResult = sendEvent(order, OrderEventEnum.RECEIVE);

      result.setPayResult(payResult);
      result.setPayState(OrderEventEnum.PAY);
      result.setReceiveResult(receiveResult);
      result.setReceiveState(OrderEventEnum.RECEIVE);
      return result;
    } catch (Exception e) {
      logger.error("getOrderVo error", e);
    } finally {
      // 流程结束
      stateMachine.stop();
    }
    return result;
  }

  /**
   * 创建订单对象
   *
   * @param orderId 订单id
   * @return Order
   */
  private Order getOrder(Integer orderId) {
    logger.info("--- 开始订单流程 订单:{} ---", orderId);
    Order order = new Order();
    order.setOrderState(OrderStateEnum.UNPAY);
    order.setId(orderId);
    return order;
  }

  /**
   * 发送事件
   *
   * @param order 订单对象
   * @param enums 事件类型
   */
  private synchronized boolean sendEvent(Order order, OrderEventEnum enums) {
    logger.info("--- 发送事件 {} 订单:{} ---", enums, order.getId());
    // 持久化订单id
    final String persistId = StatemachineConstants.HEADER_NAME + PublicConstants.COLON + order.getId();

    try {
      Message<OrderEventEnum> message = MessageBuilder.withPayload(enums)
          .setHeader(StatemachineConstants.HEADER_NAME, order).build();
      // 获取状态机状态
      StateMachine<OrderStateEnum, OrderEventEnum> persistState = stateMachinePersister.restore(stateMachine,
          persistId);

      boolean result = persistState.sendEvent(message);
      if (result) {
        // 持久化状态机状态
        stateMachinePersister.persist(stateMachine, persistId);
      }
      return result;
    } catch (Exception e) {
      logger.error("发送事件异常: {}", enums);
      logger.error("sendEvent error", e);
    }
    return false;
  }
}

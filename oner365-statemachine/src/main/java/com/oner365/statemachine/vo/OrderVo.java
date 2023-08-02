package com.oner365.statemachine.vo;

import java.io.Serializable;

import com.oner365.statemachine.enums.OrderEventEnum;

/**
 * 状态机订单对象
 * 
 * @author zhaoyong
 */
public class OrderVo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * 订单号
   */
  private int id;

  /**
   * 支付状态
   */
  private OrderEventEnum payState;
  
  /**
   * 接收状态
   */
  private OrderEventEnum receiveState;
  
  /**
   * 支付结果
   */
  private boolean payResult;
  
  /**
   * 接收结果
   */
  private boolean receiveResult;
  
  public OrderVo() {
    super();
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the payState
   */
  public OrderEventEnum getPayState() {
    return payState;
  }

  /**
   * @param payState the payState to set
   */
  public void setPayState(OrderEventEnum payState) {
    this.payState = payState;
  }

  /**
   * @return the receiveState
   */
  public OrderEventEnum getReceiveState() {
    return receiveState;
  }

  /**
   * @param receiveState the receiveState to set
   */
  public void setReceiveState(OrderEventEnum receiveState) {
    this.receiveState = receiveState;
  }

  /**
   * @return the payResult
   */
  public boolean isPayResult() {
    return payResult;
  }

  /**
   * @param payResult the payResult to set
   */
  public void setPayResult(boolean payResult) {
    this.payResult = payResult;
  }

  /**
   * @return the receiveResult
   */
  public boolean isReceiveResult() {
    return receiveResult;
  }

  /**
   * @param receiveResult the receiveResult to set
   */
  public void setReceiveResult(boolean receiveResult) {
    this.receiveResult = receiveResult;
  }

}

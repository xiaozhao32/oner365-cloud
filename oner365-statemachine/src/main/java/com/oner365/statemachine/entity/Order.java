package com.oner365.statemachine.entity;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.oner365.statemachine.enums.OrderStateEnum;

/**
 * 订单对象
 *
 * @author zhaoyong
 *
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private int id;

    /**
     * 订单状态
     */
    private OrderStateEnum orderState;

    /**
     * Constructor
     */
    public Order() {
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
     * @return the orderState
     */
    public OrderStateEnum getOrderState() {
        return orderState;
    }

    /**
     * @param orderState the orderState to set
     */
    public void setOrderState(OrderStateEnum orderState) {
        this.orderState = orderState;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("orderState", orderState).toString();
    }

}

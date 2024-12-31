package com.oner365.clickhouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.clickhouse.entity.Order;

/**
 * 订单接口
 * 
 * @author zhaoyong
 *
 */
public interface IOrderDao extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

}

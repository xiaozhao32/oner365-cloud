package com.oner365.datasource.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.datasource.entity.Order;

/**
 * 订单接口
 * 
 * @author zhaoyong
 *
 */
@Repository
public interface IOrderDao extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

}

package com.oner365.datasource.controller;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.datasource.util.DataSourceUtil;
import com.oner365.common.enums.StatusEnum;
import com.oner365.controller.BaseController;
import com.oner365.util.DateUtil;
import com.oner365.util.SnowFlakeUtils;

/**
 * 接口测试
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/sharding")
public class ShardingDatasourceController extends BaseController {

  @Resource(name = "dataSource")
  private DataSource shardingDataSource;

  /**
   * 测试分库分表
   * 
   * @param orderId 订单id
   * @param userId  用户id
   * @return List<Map<String, String>>
   */
  @GetMapping("/test")
  public List<Map<String, String>> testDataSource(Integer orderId, Integer userId) {
    String sql = "insert into t_order(id, order_id, user_id, status, create_time) " + "values('"
        + new SnowFlakeUtils(1L, 1L).nextId() + "'," + orderId + "," + userId + ",'"+StatusEnum.YES.ordinal()+"','" + DateUtil.getCurrentTime()
        + "')";
    try (Connection con = shardingDataSource.getConnection()) {
      return DataSourceUtil.execute(con, sql);
    } catch (Exception e) {
      logger.error("testDataSource error:", e);
    }
    return Collections.emptyList();
  }

}

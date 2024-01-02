package com.oner365.hadoop.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.web.controller.BaseController;

/**
 * Hive 控制器
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/hive")
public class HiveController extends BaseController {

  @Resource
  @Qualifier("hiveDruidTemplate")
  private JdbcTemplate jdbcTemplate;

  @GetMapping("/test")
  public List<Map<String, Object>> test() {
    String sql = "show tables";
    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
    logger.info("result:{}", result.size());
    return result;
  }
}

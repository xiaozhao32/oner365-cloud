package com.oner365.datasource.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseData;
import com.oner365.common.datasource.util.DataSourceUtil;
import com.oner365.controller.BaseController;
import com.oner365.datasource.dynamic.DataSourceHolder;
import com.oner365.datasource.dynamic.DynamicDataSource;

/**
 * 接口测试
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicDatasourceController extends BaseController {
  
  @Autowired
  private DynamicDataSource dataSource;

  /**
   * 测试切换数据源
   * 
   * @return ResponseData
   */
  @GetMapping("/test")
  public ResponseData<HashMap<String, Object>> testDataSource() {
    HashMap<String, Object> result = new HashMap<String, Object>(10);

    // 切换数据源1
    try {
      DataSourceHolder.setDataSource("nacos");
      String sql = "select * from config_info limit 1";
      List<Map<String, String>> list = DataSourceUtil.execute(dataSource.getConnection(), sql);
      result.put("result1", list);
    } catch (Exception e) {
      logger.error("ds1 connection error", e);
    } finally {
      DataSourceHolder.clearDataSource();
    }

    // 切换数据源2
    try {
      DataSourceHolder.setDataSource("oner365");
      String sql = "select * from hibernate_sequence limit 1";
      List<Map<String, String>> list = DataSourceUtil.execute(dataSource.getConnection(), sql);
      result.put("result2", list);
    } catch (Exception e) {
      logger.error("ds2 connection error", e);
    } finally {
      DataSourceHolder.clearDataSource();
    }
    return ResponseData.success(result);
  }

}

package com.oner365.datasource.controller;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.commons.util.SnowFlakeUtils;
import com.oner365.data.datasource.util.DataSourceUtil;
import com.oner365.data.web.controller.BaseController;

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
     * @param orderId 订单id
     * @param userId 用户id
     * @return List<Map<String, Object>>
     */
    @PostMapping("/save")
    public List<Map<String, Object>> testDataSource(Integer orderId, Integer userId) {
        String sql = "insert into t_order(id, order_id, user_id, status, create_time) " + "values('"
                + new SnowFlakeUtils(1L, 1L).nextId() + "'," + orderId + "," + userId + ",'" + StatusEnum.YES.ordinal()
                + "','" + DateUtil.getCurrentTime() + "')";
        try (Connection con = shardingDataSource.getConnection()) {
            return DataSourceUtil.execute(con, sql);
        }
        catch (Exception e) {
            logger.error("shardingDataSource save error:", e);
        }
        return Collections.emptyList();
    }

    /**
     * 测试分库查询
     * @return List<Map<String, Object>>
     */
    @GetMapping("/list")
    public List<Map<String, Object>> findList() {
        String sql = "select * from t_order";
        try (Connection con = shardingDataSource.getConnection()) {
            return DataSourceUtil.execute(con, sql);
        }
        catch (Exception e) {
            logger.error("shardingDataSource list error:", e);
        }
        return Collections.emptyList();
    }

}

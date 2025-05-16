package com.oner365.swagger.controller.monitor.rabbitmq;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.monitor.IMonitorRabbitmqClient;
import com.oner365.swagger.enums.RabbitmqTypeEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Rabbit MQ监控
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "监控 - Rabbitmq")
@RequestMapping("/monitor/rabbitmq")
public class MonitorRabbitmqController {

  @Resource
  private IMonitorRabbitmqClient client;

  /**
   * 首页
   *
   * @return ResponseData<Serializable>
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public ResponseData<Serializable> index() {
    return client.index();
  }

  /**
   * 获取队列列表
   *
   * @param type      类型
   * @param pageIndex 分页页码
   * @param pageSize  分页长度
   * @param name      名称
   * @return ResponseData<Serializable>
   */
  @ApiOperation("2.获取队列列表")
  @ApiOperationSupport(order = 2)
  @GetMapping("/list/{type}")
  public ResponseData<Serializable> list(@PathVariable RabbitmqTypeEnum type,
      @RequestParam int pageIndex, @RequestParam int pageSize, String name) {
    return client.list(type, pageIndex, pageSize, name);
  }

  /**
   * 删除
   *
   * @param type 删除类型
   * @param name 名称
   * @return ResponseData<Serializable>
   */
  @ApiOperation("3.删除不同类型的队列")
  @ApiOperationSupport(order = 3)
  @DeleteMapping("/delete/{type}/{name}")
  public ResponseData<Serializable> delete(@PathVariable String type, @PathVariable String name) {
    return client.delete(type, name);
  }
}

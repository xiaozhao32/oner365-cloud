package com.oner365.influx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oner365.influx.config.InfluxConfig;
import com.oner365.influx.config.properties.InfluxProperties;
import com.oner365.influx.entity.Mem;
import com.oner365.influx.service.InfluxService;

/**
 * influx 接口实现类
 * 
 * @author zhaoyong
 */
@Service
public class InfluxServiceImpl implements InfluxService {

  @Resource
  private InfluxConfig config;
  
  @Resource
  private InfluxProperties influxProperties;
  
  @Override
  public List<Mem> findList(String data) {
    String query = String.format("from(bucket: \"%s\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"%s\")", influxProperties.getBucket(), data);
    return config.getInfluxClient().getQueryApi().query(query, influxProperties.getOrg(), Mem.class);
  }
}

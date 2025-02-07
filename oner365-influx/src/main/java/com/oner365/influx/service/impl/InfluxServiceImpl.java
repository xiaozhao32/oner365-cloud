package com.oner365.influx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
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

  private final Logger logger = LoggerFactory.getLogger(InfluxServiceImpl.class);

  @Resource
  private InfluxConfig config;

  @Resource
  private InfluxProperties influxProperties;

  @Override
  public Boolean save(Mem vo) {
    try (WriteApi writeApi = config.getInfluxClient().makeWriteApi()) {
      Map<String, String> tagMap = new HashMap<>();
      tagMap.put("host", vo.getHost());
      Map<String, Object> fieldMap = new HashMap<>();
      fieldMap.put("_value", vo.getUsedPercent());

      Point point = Point.measurement("mem").addTags(tagMap).addFields(fieldMap);
      writeApi.writePoint(influxProperties.getBucket(), influxProperties.getOrg(), point);
      writeApi.flush();

      return Boolean.TRUE;
    } catch (Exception e) {
      logger.error("Influx save error", e);
    }
    return Boolean.FALSE;
  }

  @Override
  public List<Mem> findList(String data) {
    String query = String.format(
        "from(bucket: \"%s\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"%s\")",
        influxProperties.getBucket(), data);
    return config.getInfluxClient().getQueryApi().query(query, influxProperties.getOrg(), Mem.class);
  }
}

package com.oner365.influx.test;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.oner365.influx.entity.Mem;

/**
 * influxdb test
 * 
 * @author zhaoyong
 *
 */
public class InfluxdbTest {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(InfluxdbTest.class);

  public static void main(final String[] args) {

    // You can generate an API token from the "API Tokens Tab" in the UI
    String url = "http://localhost:8086";
    String token = "KQQQL_LpMrdpoopKu5_QvYPNVlL-KOoc1zY3dBTfu4xZGnicgAPhbSsryghUH1A21fXzHxPZ-W5hrNVG6tgIbQ==";
    String bucket = "oner365";
    String org = "oner365";

    try (InfluxDBClient client = InfluxDBClientFactory.create(url, token.toCharArray())) {
      // 保存
//      writeData(client, bucket, org);
      
      // 查询
      queryData(client, bucket, org);
    } catch (Exception e) {
      LOGGER.error("influxdb error:", e);
    }
  }
  
  /**
   * 查询
   */
  public static void queryData(InfluxDBClient client, String bucket, String org) {
    String query = String.format("from(bucket: \"%s\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"%s\")", bucket, "mem");
    List<FluxTable> tables = client.getQueryApi().query(query, org);

    for (FluxTable table : tables) {
      for (FluxRecord record : table.getRecords()) {
        LOGGER.info("Table:{}, Measurement:{}, Field:{}, Value:{}", record.getTable(), record.getMeasurement(), record.getField(), record.getValue());
      }
    }
  }

  /**
   * 保存
   */
  public static void writeData(InfluxDBClient client, String bucket, String org) {
    // 1.Use InfluxDB Line Protocol to write data
    String data = "mem,host=host1 used_percent=23.43234543";
    WriteApiBlocking writeApi = client.getWriteApiBlocking();
    writeApi.writeRecord(bucket, org, WritePrecision.NS, data);

    // 2.Use a Data Point to write data
    Point point = Point.measurement("mem").addTag("host", "host2").addField("used_percent", 23.43234543)
        .time(Instant.now(), WritePrecision.NS);
    WriteApiBlocking writeApi2 = client.getWriteApiBlocking();
    writeApi2.writePoint(bucket, org, point);

    // 3. Use POJO and corresponding class to write data
    Mem mem = new Mem();
    mem.setHost("host3");
    mem.setUsedPercent(23.43234543);
    mem.setTime(Instant.now());
    WriteApiBlocking writeApi3 = client.getWriteApiBlocking();
    writeApi3.writeMeasurement(bucket, org, WritePrecision.NS, mem);
  }

}

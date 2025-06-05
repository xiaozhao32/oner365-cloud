package com.oner365.influx.config;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.oner365.influx.config.properties.InfluxProperties;

/**
 * influx config
 *
 * @author zhaoyong
 *
 */
@Configuration
@EnableConfigurationProperties({ InfluxProperties.class })
public class InfluxConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfluxConfig.class);

    @Resource
    private InfluxProperties influxProperties;

    /**
     * 获取连接
     * @return InfluxDBClient
     */
    public InfluxDBClient getInfluxClient() {
        return InfluxDBClientFactory.create(influxProperties.getUrl(), influxProperties.getToken().toCharArray());
    }

    @PreDestroy
    public void destroy() {
        LOGGER.info("influx close..");
        getInfluxClient().close();
    }

}

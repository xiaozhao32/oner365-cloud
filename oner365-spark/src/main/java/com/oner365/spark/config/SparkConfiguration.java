package com.oner365.spark.config;

import javax.annotation.Resource;

import org.apache.spark.SparkConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.oner365.spark.config.properties.SparkConfigProperties;

/**
 * Spark 配置类
 * 
 * @author zhaoyong
 */
@Configuration
@EnableConfigurationProperties({ SparkConfigProperties.class })
public class SparkConfiguration {
  
  @Resource
  private SparkConfigProperties properties;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SparkConfiguration.class);
  
  @Bean
  SparkConf getSparkConfig() {
    LOGGER.info("Spark Properties: {}", JSON.toJSONString(properties));
    
    SparkConf sparkConf = new SparkConf();
    sparkConf.setMaster(properties.getMaster());
    sparkConf.setAppName(properties.getAppName());
    sparkConf.setSparkHome(properties.getHome());
    
    sparkConf.set("spark.dynamicAllocation.enabled", "false");
    sparkConf.set("spark.driver.allowMultipleContexts", "true");
    sparkConf.set("spark.eventLog.enabled", "false");
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
    sparkConf.set("spark.hadoop.validateOutputSpecs", "false");
    sparkConf.set("hive.mapred.supports.subdirectories", "true");
    sparkConf.set("mapreduce.input.fileinputformat.input.dir.recursive", "true");
    return sparkConf;
  }
  
}

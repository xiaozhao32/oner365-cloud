package com.oner365.spark.config;

import javax.annotation.Resource;

import org.apache.spark.SparkConf;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    SparkConf getSparkConfig() {

        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster(properties.getMaster());
        sparkConf.setAppName(properties.getAppName());
        sparkConf.setSparkHome(properties.getHome());

        sparkConf.set("spark.eventLog.enabled", Boolean.FALSE.toString());
        sparkConf.set("spark.dynamicAllocation.enabled", Boolean.FALSE.toString());
        sparkConf.set("spark.hadoop.validateOutputSpecs", Boolean.FALSE.toString());
        sparkConf.set("hive.mapred.supports.subdirectories", Boolean.TRUE.toString());
        sparkConf.set("spark.driver.allowMultipleContexts", Boolean.TRUE.toString());
        sparkConf.set("mapreduce.input.fileinputformat.input.dir.recursive", Boolean.TRUE.toString());
        sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        return sparkConf;
    }

}

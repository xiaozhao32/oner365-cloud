package com.oner365.spark.service.impl;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.springframework.stereotype.Service;

import com.oner365.spark.service.SparkService;

/**
 * Spark 接口
 * 
 * @author zhaoyong
 */
@Service
public class SparkServiceImpl implements SparkService {

  private static final long serialVersionUID = 1L;

  @Resource
  private SparkConf sparkConf;

  @Override
  public String getSparkHome() {
    try (JavaSparkContext sparkContext = new JavaSparkContext(sparkConf)) {
      Optional<String> options = sparkContext.getSparkHome();
      return options.orNull();
    }
  }

  @Override
  public int reduce() {
    try (JavaSparkContext sparkContext = new JavaSparkContext(sparkConf)) {
      Integer[] list = new Integer[] { 1, 2, 3, 4, 5 };
      JavaRDD<Integer> rdd = sparkContext.parallelize(Arrays.asList(list));
      // 转换RDD
      JavaRDD<Integer> mappedRDD = rdd.map(new Function<Integer, Integer>() {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer call(Integer v) {
          return v * 100;
        }
      });

      // 计算结果 {100, 200, 300, 400, 500} = 1500
      return mappedRDD.reduce(new Function2<Integer, Integer, Integer>() {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer call(Integer v1, Integer v2) {
          return v1 + v2;
        }
      });
    }
  }
}

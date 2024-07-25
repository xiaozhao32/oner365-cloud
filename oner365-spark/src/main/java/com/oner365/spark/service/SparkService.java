package com.oner365.spark.service;

import java.io.Serializable;

/**
 * Spark 接口
 * 
 * @author zhaoyong
 */
public interface SparkService extends Serializable {

  /**
   * 获取地址
   * 
   * @return 返回结果
   */
  String getSparkHome();

  /**
   * 计算
   * 
   * @return 返回结果
   */
  int reduce();
}

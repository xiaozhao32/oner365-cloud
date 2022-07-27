package com.oner365.influx.service;

import java.util.List;

import com.oner365.influx.entity.Mem;

/**
 * influx 接口
 *
 * @author zhaoyong
 */
public interface InfluxService {

  /**
   * 查询对象
   *
   * @param data 查询参数
   * @return 对象集合
   */
  List<Mem> findList(String data);

}

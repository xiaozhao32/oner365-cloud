package com.oner365.common.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.page.PageInfo;

/**
 * Service 父类
 *
 * @author liutao
 */
public interface BaseService {

  /**
   * 转换对象
   *
   * @param source 转换对象
   * @param clazz  目标类
   * @return T
   */
  default <T extends Serializable, S> T convert(S source, Class<T> clazz) {
    if (source == null) {
      return null;
    }
    return JSON.parseObject(JSON.toJSONString(source), clazz);
  }

  /**
   * 转换集合
   *
   * @param sourceList 转换集合
   * @param clazz      目标类
   * @return List<T>
   */
  default <T extends Serializable, S> List<T> convert(List<S> sourceList, Class<T> clazz) {
    if (sourceList.isEmpty()) {
      return Collections.emptyList();
    }
    return sourceList.stream().map(e -> convert(e, clazz)).collect(Collectors.toList());
  }

  /**
   * 转换分页对象
   *
   * @param page  分页对象
   * @param clazz 目标类
   * @return PageInfo<T>
   */
  default <T extends Serializable, S> PageInfo<T> convert(Page<S> page, Class<T> clazz) {
    if (page == null) {
      return null;
    }
    return new PageInfo<>(convert(page.getContent(), clazz), page.getNumber() + 1, page.getSize(),
            page.getTotalElements());
  }

}

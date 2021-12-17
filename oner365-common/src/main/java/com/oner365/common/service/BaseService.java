package com.oner365.common.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.page.PageInfo;
import com.oner365.util.ClassesUtil;

/**
 * Service 父类
 *
 * @author liutao
 */
public interface BaseService {

  /**
   * 转换po对象为dto
   *
   * @param po 对象
   * @return T
   */
  default <T extends Serializable, E> T convertDto(E po) {
    if (po == null) {
      return null;
    }
    return ClassesUtil.invokeMethod(po, PublicConstants.INVOKE_METHOD_DTO_NAME);
  }

  /**
   * 转换po对象为dto
   *
   * @param list po集合
   * @return List<T>
   */
  default <T extends Serializable, E> List<T> convertDto(List<E> list) {
    if (list.isEmpty()) {
      return Collections.emptyList();
    }
    return list.stream().<T>map(this::convertDto).collect(Collectors.toList());
  }

  /**
   * 转换分页对象
   *
   * @param page 分页po对象
   * @return PageInfo<T>
   */
  default <T extends Serializable, E> PageInfo<T> convertDto(Page<E> page) {
    if (page == null) {
      return null;
    }
    return new PageInfo<>(convertDto(page.getContent()), page.getNumber() + 1, page.getSize(), page.getTotalElements());
  }

}

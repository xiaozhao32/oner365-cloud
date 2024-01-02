package com.oner365.datasource.aspect;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.datasource.annotation.DataSource;
import com.oner365.datasource.dynamic.DataSourceHolder;

/**
 * 数据源拦截器
 * 
 * @author zhaoayong
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {

  @Pointcut("@annotation(com.oner365.datasource.annotation.DataSource)")
  public void annotationPoint() {

  }

  @Around("annotationPoint()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    DataSource dataSource = getDataSource(point);

    if (!DataUtils.isEmpty(dataSource)) {
      DataSourceHolder.setDataSource(dataSource.value());
    }

    try {
      return point.proceed();
    } finally {
      DataSourceHolder.clearDataSource();
    }
  }

  /**
   * 切换数据源
   */
  public DataSource getDataSource(ProceedingJoinPoint point) {
    MethodSignature signature = (MethodSignature) point.getSignature();
    DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
    if (Objects.nonNull(dataSource)) {
      return dataSource;
    }

    return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
  }
}

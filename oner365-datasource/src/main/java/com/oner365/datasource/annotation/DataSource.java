package com.oner365.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.oner365.common.datasource.constants.DataSourceConstants;

/**
 * 数据源注解
 * 
 * @author zhaoyong
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

  DataSourceConstants.DataSourceType value() default DataSourceConstants.DataSourceType.PRIMARY;

}

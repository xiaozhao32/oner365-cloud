package com.oner365.data.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * Cache Annotation
 *
 * @author zhaoyong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisCachePut {

    String value() default PublicConstants.EMPTY;

    String key() default PublicConstants.EMPTY;

}

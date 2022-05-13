package com.oner365.common.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import com.oner365.common.constants.PublicConstants;

/**
 * Generator Cache
 * 
 * @author zhaoyong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Cacheable
public @interface GeneratorCache {

  @AliasFor("cacheNames")
  String[] value() default {};

  @AliasFor("value")
  String[] cacheNames() default {};

  String key() default "";

  String keyGenerator() default PublicConstants.KEY_GENERATOR;

}

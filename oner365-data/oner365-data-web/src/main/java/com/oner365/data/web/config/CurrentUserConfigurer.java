package com.oner365.data.web.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oner365.data.web.resolver.CurrentUserMethodArgumentResolver;

/**
 * 注册用户信息
 * 
 * @author zhaoyong
 */
@Configuration
public class CurrentUserConfigurer implements WebMvcConfigurer {

  private final CurrentUserMethodArgumentResolver resolver;

  /**
   * 构造方法
   * 
   * @param resolver CurrentUserMethodArgumentResolver
   */
  public CurrentUserConfigurer(CurrentUserMethodArgumentResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(resolver);
  }

}

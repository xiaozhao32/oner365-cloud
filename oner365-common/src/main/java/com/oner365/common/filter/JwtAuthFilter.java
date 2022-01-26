/*
 *    Copyright 2016 10gen Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.oner365.common.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.config.properties.AccessTokenProperties;
import com.oner365.common.constants.PublicConstants;
import com.oner365.util.DataUtils;
import com.oner365.util.JwtUtils;
import com.oner365.util.RequestUtils;

/**
 * 用于对客户端调用请求时验证token的合法性
 * 
 * @author zhaoyong
 */
@Component
public class JwtAuthFilter implements Filter {

  @Autowired
  private AccessTokenProperties tokenProperties;

  @Autowired
  private RedisCache redisCache;

  private static final String CACHE_NAME = "Auth:token:";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    // 获取Token
    String authToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
    if (!DataUtils.isEmpty(authToken)) {
      String tokenInfo = redisCache.getCacheObject(CACHE_NAME + authToken.hashCode());
      if (tokenInfo == null) {
        tokenInfo = JwtUtils.getUsernameFromToken(authToken, tokenProperties.getSecret());
        if (tokenInfo != null) {
          redisCache.setCacheObject(CACHE_NAME + authToken.hashCode(), tokenInfo, PublicConstants.EXPIRE_TIME,
              TimeUnit.MINUTES);
        }
      }
      httpRequest.setAttribute(RequestUtils.AUTH_USER, new AuthUser(JSON.parseObject(tokenInfo)));
      httpRequest.setAttribute(RequestUtils.ACCESS_TOKEN, authToken);
    }
    RequestUtils.setHttpRequest(httpRequest);
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) {
    // init
  }

  @Override
  public void destroy() {
    // destroy
  }

}

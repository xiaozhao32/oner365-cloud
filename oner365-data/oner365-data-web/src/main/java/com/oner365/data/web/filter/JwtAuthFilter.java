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
package com.oner365.data.web.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.auth.AuthUser;
import com.oner365.data.commons.config.properties.AccessTokenProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.JwtUtils;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.redis.constants.CacheConstants;
import com.oner365.data.web.utils.RequestUtils;

/**
 * 用于对客户端调用请求时验证token的合法性
 *
 * @author zhaoyong
 */
@Component
public class JwtAuthFilter implements Filter {

    @Resource
    private AccessTokenProperties tokenProperties;

    @Resource
    private RedisCache redisCache;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 获取Token
        String authToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!DataUtils.isEmpty(authToken)) {
            // 获取缓存
            JSONObject authUser = redisCache.getCacheObject(CacheConstants.CACHE_TOKEN_NAME + authToken.hashCode());
            if (authUser == null) {
                String tokenInfo = JwtUtils.getUsernameFromToken(authToken, tokenProperties.getSecret());
                if (tokenInfo != null) {
                    authUser = JSON.parseObject(tokenInfo);
                    redisCache.setCacheObject(CacheConstants.CACHE_TOKEN_NAME + authToken.hashCode(), authUser,
                            PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
                    setHttpRequest(httpRequest, new AuthUser(authUser), authToken);
                }
            }
            else {
                setHttpRequest(httpRequest, new AuthUser(authUser), authToken);
            }
        }
        RequestUtils.setHttpRequest(httpRequest);
        chain.doFilter(request, response);
    }

    private void setHttpRequest(HttpServletRequest httpRequest, AuthUser authUser, String authToken) {
        httpRequest.setAttribute(RequestUtils.AUTH_USER, authUser);
        if (authToken != null) {
            httpRequest.setAttribute(RequestUtils.ACCESS_TOKEN, authToken);
        }

        // token 过期处理
        validateToken(authUser, authToken);
    }

    private void validateToken(AuthUser authUser, String authToken) {
        boolean isExpired = JwtUtils.validateToken(authToken, tokenProperties.getSecret());
        if (!isExpired) {
            String key = CacheConstants.CACHE_LOGIN_NAME + authUser.getUserName();
            redisCache.deleteObject(key);
        }
    }

}

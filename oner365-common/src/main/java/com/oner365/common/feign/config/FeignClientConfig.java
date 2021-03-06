package com.oner365.common.feign.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.oner365.util.RequestUtils;

import feign.RequestInterceptor;

/**
 * 请求拦截器
 * @author zhaoyong
 */
@Component
public class FeignClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientConfig.class);

    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            try {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, RequestUtils.getToken());
            } catch (Exception e) {
                LOGGER.error("Interceptor:", e);
            }
        };
    }

}

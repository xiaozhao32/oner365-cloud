
package com.oner365.gateway.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;
import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.enums.ErrorInfoEnum;

import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 *
 * @author zhaoyong
 */
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        LOGGER.error("[网关异常] 请求路径:{}, 异常信息:{}", request.getPath(), ex.getMessage());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        
        Map<String, Object> errorAttributes = setErrorAttribute(
                request.getMethod(), request.getPath().value(), ex);
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(errorAttributes));
        }));
    }
    
    /**
     * 异常信息
     * @param method 方法名称
     * @param path 地址
     * @param ex 异常
     * @return Map<String, Object>
     */
    private Map<String, Object> setErrorAttribute(HttpMethod method, String path, Throwable ex) {
        Map<String, Object> result = new HashMap<>(8);
        if (method != null) {
            result.put(GatewayConstants.METHOD, method.name());
        }
        result.put(GatewayConstants.PATH, path);
        result.put(GatewayConstants.RESULT, ex.getMessage());

        if (ex instanceof NotFoundException) {
            result.put(GatewayConstants.CODE, ErrorInfoEnum.CODE_503.getCode());
            result.put(GatewayConstants.MESSAGE, ErrorInfoEnum.CODE_503.getName());
        } else if (ex instanceof ResponseStatusException) {
            result.put(GatewayConstants.CODE, ErrorInfoEnum.CODE_404.getCode());
            result.put(GatewayConstants.MESSAGE, ErrorInfoEnum.CODE_404.getName());
        } else {
            result.put(GatewayConstants.CODE, ErrorInfoEnum.CODE_500.getCode());
            result.put(GatewayConstants.MESSAGE, ErrorInfoEnum.CODE_500.getName());
        }
        return result;
    }
}
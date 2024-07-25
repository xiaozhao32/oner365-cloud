package com.oner365.gateway.config;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;
import com.oner365.data.commons.config.properties.AccessTokenProperties;
import com.oner365.data.commons.config.properties.IgnoreWhiteProperties;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.util.JwtUtils;
import com.oner365.gateway.log.event.SysLogEvent;
import com.oner365.gateway.util.DataUtils;
import com.oner365.gateway.vo.SysLogVo;

import reactor.core.publisher.Mono;

/**
 * token过滤器
 *
 * @author zhaoyong
 */
@Configuration
public class TokenFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    @Resource
    private ApplicationEventPublisher publisher;

    @Resource
    private IgnoreWhiteProperties ignoreWhiteProperties;

    @Resource
    private AccessTokenProperties accessTokenProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 记录请求日志
        requestLog(request);

        // 验证白名单
        if (validateIgnoreWhites(request)) {
            return chain.filter(exchange);
        }

        // 验证token
        if (validateToken(request)) {
            return chain.filter(exchange.mutate().request(request).build());
        }

        // 返回错误信息
        return setUnauthorizedResponse(request, exchange.getResponse());
    }

    /**
     * 验证白名单
     *
     * @param request ServerHttpRequest
     * @return boolean
     */
    private boolean validateIgnoreWhites(ServerHttpRequest request) {
        List<String> paths = ignoreWhiteProperties.getWhites();
        return paths.stream().anyMatch(request.getPath().value()::contains);
    }

    /**
     * 验证token
     *
     * @param request ServerHttpRequest
     * @return boolean
     */
    private boolean validateToken(ServerHttpRequest request) {
        List<String> authList = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authList != null && !authList.isEmpty()) {
            try {
                return JwtUtils.validateToken(authList.get(0), accessTokenProperties.getSecret());
            } catch (Exception e) {
                LOGGER.error("TokenInterceptor validateToken error: {}", request.getURI().getRawPath(), e);
            }
        }
        return false;
    }

    /**
     * 返回错误信息
     *
     * @param request ServerHttpRequest
     * @param response ServerHttpResponse
     * @return Mono<Void>
     */
    private Mono<Void> setUnauthorizedResponse(ServerHttpRequest request, ServerHttpResponse response) {
        // 返回错误消息
        LOGGER.error("[{}] Client Unauthorized error. Request uri: {}", HttpStatus.UNAUTHORIZED.value(),
                request.getURI());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON
                    .toJSONBytes(ResponseData.error(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name())));
        }));
    }

    /**
     * 记录日志
     *
     * @param request 请求
     */
    private void requestLog(ServerHttpRequest request) {
        // 请求ip
        String ip = DataUtils.getIpAddress(request);
        // 请求地址
        String uri = request.getURI().getRawPath();
        // 请求方法
        HttpMethod method = request.getMethod();
        if (method == null) {
            return;
        }

        String methodName = method.name();
        // 除get请求一律保存日志
        if (!HttpMethod.GET.matches(methodName)) {
            SysLogVo sysLog = new SysLogVo();
            sysLog.setCreateTime(LocalDateTime.now());
            sysLog.setMethodName(methodName);
            sysLog.setOperationIp(ip);
            sysLog.setOperationPath(uri);
            sysLog.setOperationName(StringUtils.substringBefore(uri.substring(1), "/"));
            this.publisher.publishEvent(new SysLogEvent(sysLog));
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }

}

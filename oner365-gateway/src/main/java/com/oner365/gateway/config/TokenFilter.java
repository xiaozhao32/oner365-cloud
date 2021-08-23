package com.oner365.gateway.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.oner365.gateway.config.properties.IgnoreWhiteProperties;
import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.constants.ResponseData;
import com.oner365.gateway.entity.SysLog;
import com.oner365.gateway.jwt.JwtUtils;
import com.oner365.gateway.log.event.SysLogEvent;

import reactor.core.publisher.Mono;

/**
 * token过滤器
 *
 * @author zhaoyong
 */
@Configuration
public class TokenFilter implements GlobalFilter, Ordered {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

  private static final String HEADER_UNKNOWN = "unknown";

  private static final String POINT = ".";

  private static final int IP_LENGTH = 15;
  private static final String IP_LOCALHOST = "0:0:0:0:0:0:0:1";

  /**
   * 项目密钥
   */
  @Value("${ACCESS_TOKEN_SECRET}")
  private String secret;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private IgnoreWhiteProperties ignoreWhiteProperties;

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
    return setUnauthorizedResponse(exchange.getResponse());
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
      // 判断是否有效
      return JwtUtils.validateToken(authList.get(0), secret);
    }
    return false;
  }

  /**
   * 返回错误信息
   *
   * @param response ServerHttpResponse
   * @return Mono<Void>
   */
  private Mono<Void> setUnauthorizedResponse(ServerHttpResponse response) {
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    return response.writeWith(Mono.fromSupplier(() -> {
      DataBufferFactory bufferFactory = response.bufferFactory();
      return bufferFactory.wrap(JSON.toJSONBytes(ResponseData.error(HttpStatus.UNAUTHORIZED.value(), GatewayConstants.ERROR_MESSAGE_401)));
    }));
  }

  /**
   * 记录日志
   *
   * @param request 请求
   */
  private void requestLog(ServerHttpRequest request) {
    // 请求ip
    String ip = getIpAddress(request);
    // 请求地址
    String uri = request.getURI().getRawPath();
    // 请求方法
    HttpMethod method = request.getMethod();
    String methodName = null;
    if (method != null) {
      methodName = method.name();
    }
    // 除get请求一律保存日志
    if (!HttpMethod.GET.matches(methodName)) {
      SysLog sysLog = new SysLog();
      sysLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
      sysLog.setMethodName(methodName);
      sysLog.setOperationIp(ip);
      sysLog.setOperationPath(uri);
      sysLog.setOperationName(StringUtils.substringBefore(uri.substring(1), "/"));
      sysLog.setOperationContext(JSON.toJSONString(request.getBody()));
      this.publisher.publishEvent(new SysLogEvent(sysLog));
    }
  }

  /**
   * 获取ip
   *
   * @param request ServerHttpRequest
   * @return String
   */
  private String getIpAddress(ServerHttpRequest request) {
    String ip = request.getHeaders().getFirst("X-Real-IP");
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeaders().getFirst("X-Forwarded-For");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeaders().getFirst("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddress().getAddress().getHostAddress();
    }
    if (IP_LOCALHOST.equals(ip)) {
      ip = getLocalhost();
    }
    if (ip != null && ip.length() > IP_LENGTH && ip.contains(POINT)) {
      ip = StringUtils.substringAfterLast(ip, ",");
    }
    return ip;
  }

  /**
   * 获取本机ip
   *
   * @return String
   */
  private static String getLocalhost() {
    try {
      InetAddress inet = InetAddress.getLocalHost();
      return inet.getHostAddress();
    } catch (UnknownHostException e) {
      LOGGER.error("Error getLocalhost:", e);
    }
    return null;
  }

  @Override
  public int getOrder() {
    return 1;
  }

}

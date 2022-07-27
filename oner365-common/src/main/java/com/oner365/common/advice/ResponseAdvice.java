package com.oner365.common.advice;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oner365.common.ResponseData;

import javax.annotation.Resource;

/**
 * Controller Advice
 *
 * @author zhaoyong
 *
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAdvice.class);

  @Resource
  private ObjectMapper objectMapper;

  @Override
  public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(@Nullable Object body, @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
    if (body instanceof String) {
      try {
        return objectMapper.writeValueAsString(ResponseData.success(String.valueOf(body)));
      } catch (JsonProcessingException e) {
        LOGGER.error("beforeBodyWrite error:", e);
      }
    }
    if (body instanceof byte[] || body instanceof ResponseData
        || body.getClass().getName().contains("org.springframework")) {
      return body;
    }
    return ResponseData.success((Serializable)body);
  }

}

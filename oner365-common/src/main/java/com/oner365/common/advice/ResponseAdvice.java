package com.oner365.common.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.oner365.common.ResponseData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller Advice
 * 
 * @author zhaoyong
 *
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAdvice.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(ResponseData.success(body));
            } catch (JsonProcessingException e) {
                LOGGER.error("beforeBodyWrite error:", e);
            }
        }
        if (body instanceof byte[] || body instanceof ResponseData) {
            return body;
        }
        return ResponseData.success(body);
    }

}

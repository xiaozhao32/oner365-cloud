package com.oner365.common.advice;

import java.util.Collection;
import java.util.Iterator;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oner365.common.ResponseData;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;

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
        return !returnType.getDeclaringClass().equals(Docket.class);
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
        if (body instanceof byte[] || body instanceof ResponseData || isSwaggerResource(body)) {
            return body;
        }
        return ResponseData.success(body);
    }

    private boolean isSwaggerResource(Object body) {
        if (body instanceof springfox.documentation.spring.web.json.Json) {
            return true;
        }
        if (body instanceof Collection) {
            Collection<?> collection = (Collection<?>) body;
            Iterator<?> iterator = collection.iterator();
            return iterator.hasNext() && iterator.next() instanceof SwaggerResource;
        }
        return false;
    }

}

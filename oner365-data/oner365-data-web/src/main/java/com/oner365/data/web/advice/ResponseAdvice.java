package com.oner365.data.web.advice;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;

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

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oner365.data.commons.config.properties.ClientWhiteProperties;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.util.Cipher;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.RsaUtils;
import com.oner365.data.web.utils.RequestUtils;

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
    private ClientWhiteProperties clientWhiteProperties;
    
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {
        
        if (RequestUtils.validateClientWhites(request.getURI().getPath(), clientWhiteProperties.getWhites())) {
            String sign = null;
            List<String> headers = request.getHeaders().get("sign");
            if (headers != null) {
                sign = headers.stream().findFirst().orElse(null);
            }
            if (DataUtils.isEmpty(sign)) {
                return null;
            }
            String key = RsaUtils.buildRsaDecryptByPrivateKey(sign, clientWhiteProperties.getPrivateKey());
            if (body instanceof ResponseData) {
                return ResponseData.success(Base64.getEncoder()
                        .encodeToString(Cipher.encodeSms4(JSON.toJSONString(body), key.substring(0, 16).getBytes())));
            }
            if (body instanceof byte[]) {
                return Base64.getEncoder()
                        .encodeToString(Cipher.encodeSms4((byte[]) body, key.substring(0, 16).getBytes())).getBytes();
            }
            if (body != null) {
                return ResponseData.success(Base64.getEncoder()
                        .encodeToString(Cipher.encodeSms4(body.toString(), key.substring(0, 16).getBytes())));
            }
        }
        
        // swagger
        if (request.getURI().getPath().contains("/swagger") || request.getURI().getPath().contains("/webjars")
                || request.getURI().getPath().contains("/v3")) {
            return body;
        }

        if (body == null) {
            return ResponseData.error("服务异常, 请联系管理员查看日志!");
        }
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(ResponseData.success(String.valueOf(body)));
            }
            catch (JsonProcessingException e) {
                LOGGER.error("beforeBodyWrite error:", e);
            }
        }
        if (body instanceof byte[] || body instanceof ResponseData
                || body.getClass().getName().contains("org.springframework")) {
            return body;
        }
        return ResponseData.success((Serializable) body);
    }

}

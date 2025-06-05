package com.oner365.data.web.advice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.oner365.data.commons.config.properties.ClientWhiteProperties;
import com.oner365.data.commons.util.Cipher;
import com.oner365.data.commons.util.RsaUtils;
import com.oner365.data.web.utils.RequestUtils;


/**
 * Controller Advice
 *
 * @author liutao
 */
@ControllerAdvice
public class RequestAdvice extends RequestBodyAdviceAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAdvice.class);

    @Resource
    private ClientWhiteProperties clientWhiteProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        HttpServletRequest request = RequestUtils.getHttpRequest();
        String body = RequestUtils.getRequestBody(inputMessage.getBody());
        String sign = null;
        try {
            if (RequestUtils.validateClientWhites(request.getServletPath(), clientWhiteProperties.getWhites())
                    && body instanceof String) {
                String content = body;
                List<String> headers = inputMessage.getHeaders().get("sign");
                if (headers != null) {
                    sign = headers.stream().findFirst().orElse(null);
                }
                String key = RsaUtils.buildRsaDecryptByPrivateKey(sign, clientWhiteProperties.getPrivateKey());
                String b = Cipher.decodeSms4toString(Base64.getDecoder().decode(content),
                        key.substring(0, 16).getBytes());
                return new MappingJacksonInputMessage(new ByteArrayInputStream(b.getBytes()),
                        inputMessage.getHeaders());
            }
        } catch (Exception e) {
            LOGGER.error("sign:{} body:{}", sign, body);
            LOGGER.error("beforeBodyRead ERROR:", e);
        }
        return new MappingJacksonInputMessage(new ByteArrayInputStream(body.getBytes()), inputMessage.getHeaders());
    }

}

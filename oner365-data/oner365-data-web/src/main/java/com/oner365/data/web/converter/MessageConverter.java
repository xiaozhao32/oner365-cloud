package com.oner365.data.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 消息转换器
 *
 * @author zhaoyong
 */
@Configuration
public class MessageConverter {

    @Bean
    HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fasHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        return new HttpMessageConverters(fasHttpMessageConverter);
    }
}

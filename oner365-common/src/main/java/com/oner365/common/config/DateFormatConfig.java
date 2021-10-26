package com.oner365.common.config;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oner365.common.jackson.JavaTimeModule;
import com.oner365.util.DateUtil;

/**
 * 日期格式化
 * 
 * @author zhaoyong
 *
 */
@Configuration
@ConditionalOnClass({ ObjectMapper.class })
@AutoConfigureBefore({ JacksonAutoConfiguration.class })
public class DateFormatConfig {
    
    /**
     * customizer
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            builder.simpleDateFormat(DateUtil.FULL_TIME_FORMAT);
            builder.modules(new JavaTimeModule());
        };
    }
    
}

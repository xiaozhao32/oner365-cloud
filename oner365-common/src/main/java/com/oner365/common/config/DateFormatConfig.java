package com.oner365.common.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * 日期格式化
 * @author zhaoyong
 *
 */
@Configuration
public class DateFormatConfig {

    /**
     * DateTime格式化字符串
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Customizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {
        return builder -> {

            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);

            builder.failOnEmptyBeans(false).failOnUnknownProperties(false)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).dateFormat(dateFormat);
        };
    }

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
    }

}

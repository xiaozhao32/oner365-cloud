package com.oner365.common.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.oner365.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * JavaTimeModule
 * 
 * @author zhaoyong
 *
 */
public class JavaTimeModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public JavaTimeModule() {
        super(PackageVersion.VERSION);
        addSerializer(LocalDateTime.class, (JsonSerializer<LocalDateTime>) new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));
        addSerializer(LocalDate.class, (JsonSerializer<LocalDate>) new LocalDateSerializer(
                DateTimeFormatter.ofPattern(DateUtil.FULL_DATE_FORMAT)));
        addSerializer(LocalTime.class, (JsonSerializer<LocalTime>) new LocalTimeSerializer(
                DateTimeFormatter.ofPattern(DateUtil.LONG_TIME_FORMAT)));
        addDeserializer(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));
        addDeserializer(LocalDate.class, (JsonDeserializer<LocalDate>) new LocalDateDeserializer(
                DateTimeFormatter.ofPattern(DateUtil.FULL_DATE_FORMAT)));
        addDeserializer(LocalTime.class, (JsonDeserializer<LocalTime>) new LocalTimeDeserializer(
                DateTimeFormatter.ofPattern(DateUtil.LONG_TIME_FORMAT)));
    }
}

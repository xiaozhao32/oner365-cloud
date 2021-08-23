package com.oner365.common.serializer;

import org.apache.commons.lang3.SerializationException;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * StringJackson2JsonSerializer
 * @author zhaoyong
 *
 * @param <T>
 */
public class StringJackson2JsonSerializer<T> extends Jackson2JsonRedisSerializer<T> {

    private ObjectMapper objectMapper = new ObjectMapper();

    public StringJackson2JsonSerializer(Class<T> type) {
        super(type);
    }

    @Override
    public byte[] serialize(Object t) {

        if (t == null) {
            return new byte[0];
        }
        try {
            return this.objectMapper.writeValueAsBytes(JSON.toJSON(t));
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

}

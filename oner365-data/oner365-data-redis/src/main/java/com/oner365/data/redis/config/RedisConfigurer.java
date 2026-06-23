package com.oner365.data.redis.config;

import java.util.Arrays;

import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.ClassesUtil;
import com.oner365.data.commons.util.DataUtils;

/**
 * RedisConfigurer
 * 
 * @author zhaoyong
 */
@Configuration
public class RedisConfigurer implements CachingConfigurer {

    @Bean(name = PublicConstants.KEY_GENERATOR)
    KeyGenerator getKeyGenerator() {
        return keyGenerator();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            String sp = "::";
            StringBuilder strBuilder = new StringBuilder(30);
            // 类名
            strBuilder.append(target.getClass().getSimpleName());
            strBuilder.append(sp);
            // 方法名
            strBuilder.append(method.getName());
            strBuilder.append(sp);
            if (params.length > 0) {
                // 参数值
                Arrays.stream(params).forEach(object -> {
                    if (DataUtils.isEmpty(object)) {
                        strBuilder.append(Strings.EMPTY);
                    } else if (ClassesUtil.isPrimitive(object.getClass())) {
                        strBuilder.append(object);
                    } else {
                        strBuilder.append(JSON.toJSONString(object).hashCode());
                    }
                });
            } else {
                strBuilder.append(sp);
            }
            return strBuilder.toString();
        };
    }
}

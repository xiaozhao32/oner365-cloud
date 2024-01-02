package com.oner365.data.web.sequence;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.web.sequence.builder.SnowflakeSeqBuilder;
import com.oner365.data.web.sequence.properties.SequenceSnowflakeProperties;
import com.oner365.data.web.sequence.range.impl.name.DateBizName;
import com.oner365.data.web.sequence.range.impl.redis.RedisSeqRangeMgr;
import com.oner365.data.web.sequence.sequence.RangeSequence;
import com.oner365.data.web.sequence.sequence.Sequence;
import com.oner365.data.web.sequence.sequence.SnowflakeSequence;
import com.oner365.data.web.sequence.sequence.impl.DefaultRangeSequence;

/**
 * sequence config
 *
 * @author zhaoyong
 *
 */
@Configuration
@ComponentScan({ "com.oner365.data.web.sequence" })
@ConditionalOnMissingBean({ Sequence.class })
public class SequenceAutoConfiguration {

    @Bean
    @ConditionalOnBean({ SequenceSnowflakeProperties.class })
    SnowflakeSequence snowflakeSequence(SequenceSnowflakeProperties properties) {
        return SnowflakeSeqBuilder.create().datacenterId(properties.getDatacenterId())
                .workerId(properties.getWorkerId()).build();
    }

    @Bean
    RangeSequence defaultRangeSequence(RedisProperties properties) {
        DefaultRangeSequence defaultRangeSequence = new DefaultRangeSequence();
        RedisSeqRangeMgr redisSeqRangeMgr = new RedisSeqRangeMgr();
        redisSeqRangeMgr.setProperties(properties);
        redisSeqRangeMgr.init();
        defaultRangeSequence.setSeqRangeMgr(redisSeqRangeMgr);
        defaultRangeSequence.setName(new DateBizName("id"));
        return defaultRangeSequence;
    }

}

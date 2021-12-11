package com.oner365.common.sequence;

import com.oner365.common.sequence.builder.SnowflakeSeqBuilder;
import com.oner365.common.sequence.properties.SequenceRedisProperties;
import com.oner365.common.sequence.properties.SequenceSnowflakeProperties;
import com.oner365.common.sequence.range.impl.name.DateBizName;
import com.oner365.common.sequence.range.impl.redis.RedisSeqRangeMgr;
import com.oner365.common.sequence.sequence.RangeSequence;
import com.oner365.common.sequence.sequence.Sequence;
import com.oner365.common.sequence.sequence.SnowflakeSequence;
import com.oner365.common.sequence.sequence.impl.DefaultRangeSequence;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * sequence config
 *
 * @author zhaoyong
 *
 */
@Configuration
@ComponentScan({ "com.oner365.common.sequence" })
@ConditionalOnMissingBean({ Sequence.class })
public class SequenceAutoConfiguration {

    @Bean
    @ConditionalOnBean({ SequenceSnowflakeProperties.class })
    public SnowflakeSequence snowflakeSequence(SequenceSnowflakeProperties properties) {
        return SnowflakeSeqBuilder.create().datacenterId(properties.getDatacenterId())
                .workerId(properties.getWorkerId()).build();
    }

    @Bean
    @ConditionalOnBean({ SequenceRedisProperties.class })
    public RangeSequence defaultRangeSequence(SequenceRedisProperties properties) {
        DefaultRangeSequence defaultRangeSequence = new DefaultRangeSequence();
        RedisSeqRangeMgr redisSeqRangeMgr = new RedisSeqRangeMgr();
        redisSeqRangeMgr.setIp(properties.getHost());
        redisSeqRangeMgr.setPort(properties.getPort());
        redisSeqRangeMgr.setAuth(properties.getPassword());
        redisSeqRangeMgr.init();
        defaultRangeSequence.setSeqRangeMgr(redisSeqRangeMgr);
        defaultRangeSequence.setName(new DateBizName("id"));
        return defaultRangeSequence;
    }

}

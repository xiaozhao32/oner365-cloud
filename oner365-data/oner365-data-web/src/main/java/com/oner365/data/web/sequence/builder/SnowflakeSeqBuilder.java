package com.oner365.data.web.sequence.builder;

import com.oner365.data.web.sequence.sequence.impl.DefaultSnowflakeSequence;

/**
 * sequence snowflake builder
 *
 * @author zhaoyong
 */
public class SnowflakeSeqBuilder implements SeqBuilder {

    private long datacenterId;

    private long workerId;

    public static SnowflakeSeqBuilder create() {
        return new SnowflakeSeqBuilder();
    }

    @Override
    public DefaultSnowflakeSequence build() {
        DefaultSnowflakeSequence sequence = new DefaultSnowflakeSequence();
        sequence.setDatacenterId(this.datacenterId);
        sequence.setWorkerId(this.workerId);
        return sequence;
    }

    public SnowflakeSeqBuilder datacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
        return this;
    }

    public SnowflakeSeqBuilder workerId(long workerId) {
        this.workerId = workerId;
        return this;
    }

}

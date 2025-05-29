package com.oner365.data.web.sequence.sequence.impl;

import com.oner365.data.web.sequence.exception.SeqException;
import com.oner365.data.web.sequence.sequence.SnowflakeSequence;

/**
 * 雪花算法 分布式生成UUID
 *
 * @author zhaoyong
 *
 */
public class DefaultSnowflakeSequence implements SnowflakeSequence {

    private long workerId;

    private long datacenterId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    @Override
    public synchronized long nextValue() {
        long timestamp = timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new SeqException("[DefaultSnowflakeSequence-nextValue] 当前时间小于上次生成序列号时间");
        }
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 0xFFFL;
            if (this.sequence == 0L) {
                timestamp = tilNextMillis(this.lastTimestamp);
            }
        }
        else {
            this.sequence = 0L;
        }
        this.lastTimestamp = timestamp;
        long epoch = 1480166465631L;
        long workerIdShift = 12L;
        long datacenterIdShift = 17L;
        long timestampLeftShift = 22L;
        return timestamp - epoch << timestampLeftShift | this.datacenterId << datacenterIdShift
                | this.workerId << workerIdShift | this.sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public void setWorkerId(long workerId) {
        long maxWorkerId = 31L;
        if (workerId > maxWorkerId) {
            throw new SeqException("[DefaultSnowflakeSequence-setWorkerId] workerId 不能大于31.");
        }
        this.workerId = workerId;
    }

    public void setDatacenterId(long datacenterId) {
        long maxDatacenterId = 31L;
        if (datacenterId > maxDatacenterId) {
            throw new SeqException("[DefaultSnowflakeSequence-setDatacenterId] datacenterId 不能大于31.");
        }
        this.datacenterId = datacenterId;
    }

    @Override
    public String nextNo() {
        return String.valueOf(nextValue());
    }

    @Override
    public int nextId() {
        return 0;
    }

}

package com.oner365.common.sequence.builder;

import com.oner365.common.sequence.range.BizName;
import com.oner365.common.sequence.range.impl.redis.RedisSeqRangeMgr;
import com.oner365.common.sequence.sequence.Sequence;
import com.oner365.common.sequence.sequence.impl.DefaultRangeSequence;

/**
 * sequence redis builder
 *
 * @author zhaoyong
 */
public class RedisSeqBuilder implements SeqBuilder {

    private String ip;

    private int port;

    private BizName bizName;

    private String auth;

    private int step = 1000;

    private long stepStart = 0L;

    public static RedisSeqBuilder create() {
        return new RedisSeqBuilder();
    }

    @Override
    public Sequence build() {
        RedisSeqRangeMgr redisSeqRangeMgr = new RedisSeqRangeMgr();
        redisSeqRangeMgr.setIp(this.ip);
        redisSeqRangeMgr.setPort(this.port);
        redisSeqRangeMgr.setAuth(this.auth);
        redisSeqRangeMgr.setStep(this.step);
        redisSeqRangeMgr.setStepStart(this.stepStart);
        redisSeqRangeMgr.init();
        DefaultRangeSequence sequence = new DefaultRangeSequence();
        sequence.setName(this.bizName);
        sequence.setSeqRangeMgr(redisSeqRangeMgr);
        return sequence;
    }

    public RedisSeqBuilder ip(String ip) {
        this.ip = ip;
        return this;
    }

    public RedisSeqBuilder port(int port) {
        this.port = port;
        return this;
    }

    public RedisSeqBuilder auth(String auth) {
        this.auth = auth;
        return this;
    }

    public RedisSeqBuilder step(int step) {
        this.step = step;
        return this;
    }

    public RedisSeqBuilder bizName(BizName bizName) {
        this.bizName = bizName;
        return this;
    }

    public RedisSeqBuilder stepStart(long stepStart) {
        this.stepStart = stepStart;
        return this;
    }
}

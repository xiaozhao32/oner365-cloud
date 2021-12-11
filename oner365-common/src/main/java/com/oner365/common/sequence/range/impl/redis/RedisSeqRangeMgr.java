package com.oner365.common.sequence.range.impl.redis;

import com.oner365.common.sequence.range.SeqRange;
import com.oner365.common.sequence.range.SeqRangeMgr;
import com.oner365.util.DataUtils;

import redis.clients.jedis.Jedis;

/**
 * sequence redis range
 *
 * @author zhaoyong
 */
public class RedisSeqRangeMgr implements SeqRangeMgr {

    private static final String KEY_PREFIX = "sequence_";

    private Jedis jedis;

    private String ip;

    private Integer port;

    private String auth;

    private int step = 1000;

    private long stepStart = 0L;

    private volatile boolean keyAlreadyExist;

    @Override
    public SeqRange nextRange(String name) {
        if (!this.keyAlreadyExist) {
            Boolean isExists = this.jedis.exists(getRealKey(name));
            if (Boolean.FALSE.equals(isExists)) {
                this.jedis.setnx(getRealKey(name), String.valueOf(this.stepStart));
            }
            this.keyAlreadyExist = true;
        }
        long max = this.jedis.incrBy(getRealKey(name), this.step);
        long min = max - this.step + 1L;
        return new SeqRange(min, max);
    }

    @Override
    public void init() {
        checkParam();
        this.jedis = new Jedis(this.ip, this.port);
        if (!DataUtils.isEmpty(this.auth)) {
            this.jedis.auth(this.auth);
        }
    }

    private void checkParam() {
        if (isEmpty(this.ip)) {
            throw new SecurityException("[RedisSeqRangeMgr-checkParam] ip is empty.");
        }
        if (null == this.port) {
            throw new SecurityException("[RedisSeqRangeMgr-checkParam] port is null.");
        }
    }

    private String getRealKey(String name) {
        return KEY_PREFIX + name;
    }

    private boolean isEmpty(String str) {
        return (null == str || str.trim().length() == 0);
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getAuth() {
        return this.auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public long getStepStart() {
        return this.stepStart;
    }

    public void setStepStart(long stepStart) {
        this.stepStart = stepStart;
    }
}

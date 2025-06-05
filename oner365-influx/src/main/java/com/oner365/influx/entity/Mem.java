package com.oner365.influx.entity;

import java.io.Serializable;
import java.time.Instant;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

/**
 * 持久化对象
 *
 * @author zhaoyong
 *
 */
@Measurement(name = "mem")
public class Mem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(tag = true)
    private String host;

    @Column(name = "_value")
    private Double usedPercent;

    @Column(timestamp = true)
    private Instant time;

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the usedPercent
     */
    public Double getUsedPercent() {
        return usedPercent;
    }

    /**
     * @param usedPercent the usedPercent to set
     */
    public void setUsedPercent(Double usedPercent) {
        this.usedPercent = usedPercent;
    }

    /**
     * @return the time
     */
    public Instant getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Instant time) {
        this.time = time;
    }

}

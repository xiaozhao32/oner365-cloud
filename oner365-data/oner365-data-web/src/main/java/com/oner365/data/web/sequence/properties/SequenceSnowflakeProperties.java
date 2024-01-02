package com.oner365.data.web.sequence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * sequence snowflake properties
 *
 * @author zhaoyong
 */
@Component
@ConfigurationProperties(prefix = "sequence.snowflake")
public class SequenceSnowflakeProperties extends BaseSequenceProperties {

    private long datacenterId;

    private long workerId;

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

}

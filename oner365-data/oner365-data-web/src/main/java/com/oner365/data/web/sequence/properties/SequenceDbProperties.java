package com.oner365.data.web.sequence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * sequence db properties
 *
 * @author zhaoyong
 */
@Component
@ConfigurationProperties(prefix = "sequence.db")
public class SequenceDbProperties extends BaseSequenceProperties {

    private String tableName = "hibernate_sequence";

    private int retryTimes = 1;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

}

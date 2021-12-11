package com.oner365.common.sequence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * sequence redis properties
 *
 * @author zhaoyong
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class SequenceRedisProperties extends BaseSequenceProperties {

    private String host;

    private Integer port;

    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

package com.oner365.rocketmq.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Rocketmq配置类
 *
 * @author zhaoyong
 */
@Configuration
public class RocketmqProperties {

    /**
     * topic
     */
    @Value("${rocketmq.consumer.topic}")
    private String topic;

    /**
     * group
     */
    @Value("${rocketmq.producer.group}")
    private String group;

    public RocketmqProperties() {
        super();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}

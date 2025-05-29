package com.oner365.kafka.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Kafka配置类
 *
 * @author zhaoyong
 */
@ConfigurationProperties
public class KafkaProperties {

    /**
     * group
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String group;

    /**
     * topics
     */
    @Value("${spring.kafka.topics}")
    private String topics;

    /**
     * bootstrap-servers
     */
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public KafkaProperties() {
        super();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

}

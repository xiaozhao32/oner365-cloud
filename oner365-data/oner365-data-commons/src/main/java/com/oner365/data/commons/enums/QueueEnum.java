package com.oner365.data.commons.enums;

/**
 * 枚举 - 队列方式
 *
 * @author zhaoyong
 */
public enum QueueEnum implements BaseEnum {

    /** Kafka */
    KAFKA("Kafka"),
    /** Rabbitmq */
    RABBITMQ("Rabbitmq"),
    /** Pulsar */
    PULSAR("Pulsar"),
    /** Mqtt */
    MQTT("Mqtt"),
    /** Rocketmq */
    ROCKETMQ("Rocketmq"),
    /** Activemq */
    ACTIVEMQ("Activemq");

    /**
     * 名称
     */
    private final String name;

    /**
     * 构造方法
     * @param name 名称
     */
    QueueEnum(String name) {
        this.name = name;
    }

    /**
     * get name
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

}

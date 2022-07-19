package com.oner365.monitor.enums;

import java.util.Arrays;
import java.util.Optional;

import com.oner365.common.enums.BaseEnum;

/**
 * 枚举 - Rabbitmq类型
 *
 * @author zhaoyong
 */
public enum RabbitmqTypeEnum implements BaseEnum {

    /** channels */
    CHANNELS("channels", "使用通道"),
    /** connections */
    CONNECTIONS("connections", "客户端连接"),
    /** exchanges */
    EXCHANGES("exchanges", "交换"),
    /** queues */
    QUEUES("queues", "队列");

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String name;

    /**
     * 构造方法
     *
     * @param code  编码
     * @param name 名称
     */
    RabbitmqTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * get name
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取枚举
     *
     * @param code 编码
     * @return StatusEnum
     */
    public static RabbitmqTypeEnum getCode(String code) {
        Optional<RabbitmqTypeEnum> result = Arrays.stream(RabbitmqTypeEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

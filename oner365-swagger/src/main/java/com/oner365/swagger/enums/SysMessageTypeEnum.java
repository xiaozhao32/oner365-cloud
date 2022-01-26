package com.oner365.swagger.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 消息类型
 *
 * @author zhaoyong
 */
public enum SysMessageTypeEnum implements Serializable {

    /** default */
    DEFAULT("default", "默认类型"),
    /** queues */
    QUEUES("queues", "队列类型");

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
    SysMessageTypeEnum(String code, String name) {
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
    public String getName() {
        return name;
    }

    /**
     * 获取枚举
     *
     * @param code 编码
     * @return StatusEnum
     */
    public static SysMessageTypeEnum getCode(String code) {
        Optional<SysMessageTypeEnum> result = Arrays.stream(SysMessageTypeEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

package com.oner365.gateway.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 状态
 *
 * @author zhaoyong
 */
public enum StatusEnum implements Serializable {

    /** 无效 */
    NO("0", "无效"),
    /** 有效 */
    YES("1", "有效");

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
    StatusEnum(String code, String name) {
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
    public static StatusEnum getCode(String code) {
        Optional<StatusEnum> result = Arrays.stream(StatusEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

package com.oner365.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 存在不存在
 *
 * @author zhaoyong
 */
public enum ExistsEnum implements Serializable {

    /** 失败 */
    NO(0, "no"),
    /** 成功 */
    YES(1, "yes");

    /**
     * 编码
     */
    private final Integer code;

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
    ExistsEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get code
     *
     * @return code
     */
    public Integer getCode() {
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
    public static ExistsEnum getCode(Integer code) {
        Optional<ExistsEnum> result = Arrays.stream(ExistsEnum.values()).filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

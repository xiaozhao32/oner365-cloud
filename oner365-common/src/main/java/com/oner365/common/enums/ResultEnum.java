package com.oner365.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 成功失败
 *
 * @author zhaoyong
 */
public enum ResultEnum implements Serializable {

    /** 失败 */
    ERROR(0, "failure"),
    /** 成功 */
    SUCCESS(1, "success");

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
    ResultEnum(Integer code, String name) {
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
     * @return ResultEnum
     */
    public static ResultEnum getCode(Integer code) {
        Optional<ResultEnum> result = Arrays.stream(ResultEnum.values()).filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

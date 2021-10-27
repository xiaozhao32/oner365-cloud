package com.oner365.gateway.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 成功失败
 * 
 * @author zhaoyong
 */
public enum ResultEnum implements Serializable {

    /** 成功 */
    SUCCESS(1, "success"),
    /** 失败 */
    ERROR(0, "failure");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 名称
     */
    private String name;

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
    public Integer getOrdinal() {
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
    public static ResultEnum getCode(Integer code) {
        Optional<ResultEnum> result = Arrays.stream(ResultEnum.values()).filter(e -> e.getOrdinal().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

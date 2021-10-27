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

    /** 成功 */
    YES(1L, "yes"),
    /** 失败 */
    NO(0L, "no");

    /**
     * 编码
     */
    private Long code;

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
    ExistsEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get code
     * 
     * @return code
     */
    public Long getOrdinal() {
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
    public static ExistsEnum getCode(Long code) {
        Optional<ExistsEnum> result = Arrays.stream(ExistsEnum.values()).filter(e -> e.getOrdinal().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

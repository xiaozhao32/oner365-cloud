package com.oner365.common.enums;

import java.io.Serializable;

/**
 * 枚举 - 状态
 *
 * @author zhaoyong
 */
public enum StatusEnum implements Serializable {

    /** 无效 */
    NO("无效"),
    /** 有效 */
    YES("有效");

    /**
     * 名称
     */
    private final String name;

    /**
     * 构造方法
     *
     * @param name 名称
     */
    StatusEnum(String name) {
        this.name = name;
    }

    /**
     * get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

}

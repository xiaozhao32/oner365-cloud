package com.oner365.data.commons.enums;

/**
 * 枚举 - 状态
 *
 * @author zhaoyong
 */
public enum StatusEnum implements BaseEnum {

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
    @Override
    public String getName() {
        return name;
    }

}

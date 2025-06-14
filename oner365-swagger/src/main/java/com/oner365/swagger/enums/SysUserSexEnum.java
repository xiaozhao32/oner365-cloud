package com.oner365.swagger.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 性别状态
 *
 * @author zhaoyong
 */
public enum SysUserSexEnum implements Serializable {

    /** 男 */
    MALE("0", "男"),
    /** 女 */
    FEMALE("1", "女");

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
     * @param code 编码
     * @param name 名称
     */
    SysUserSexEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get code
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取枚举
     * @param code 编码
     * @return MessageStatusEnum
     */
    public static SysUserSexEnum getCode(String code) {
        Optional<SysUserSexEnum> result = Arrays.stream(SysUserSexEnum.values())
            .filter(e -> e.getCode().equals(code))
            .findFirst();
        return result.orElse(null);
    }

}

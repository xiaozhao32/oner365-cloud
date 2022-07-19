package com.oner365.sys.enums;

import java.util.Arrays;
import java.util.Optional;

import com.oner365.common.enums.BaseEnum;

/**
 * 枚举 - 消息状态
 *
 * @author zhaoyong
 */
public enum MessageStatusEnum implements BaseEnum {

    /** 未读 */
    READ_NONE("0", "未读"),
    /** 已读 */
    READ("1", "已读");

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
    MessageStatusEnum(String code, String name) {
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
     * @return MessageStatusEnum
     */
    public static MessageStatusEnum getCode(String code) {
        Optional<MessageStatusEnum> result = Arrays.stream(MessageStatusEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

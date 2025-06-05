package com.oner365.api.enums;

import java.util.Arrays;
import java.util.Optional;

import com.oner365.data.commons.enums.BaseEnum;

/**
 * 枚举 - 任务状态
 *
 * @author zhaoyong
 */
public enum TaskStatusEnum implements BaseEnum {

    /** 暂停 */
    PAUSE("0", "暂停"),
    /** 正常 */
    NORMAL("1", "正常");

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
    TaskStatusEnum(String code, String name) {
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
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取枚举
     * @param code 编码
     * @return StatusEnum
     */
    public static TaskStatusEnum getCode(String code) {
        Optional<TaskStatusEnum> result = Arrays.stream(TaskStatusEnum.values())
            .filter(e -> e.getCode().equals(code))
            .findFirst();
        return result.orElse(null);
    }

}

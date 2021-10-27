package com.oner365.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 错误编码和信息
 * 
 * @author zhaoyong
 */
public enum ErrorInfoEnum implements Serializable {

    /** 参数错误 */
    PARAM(2001, "参数错误"),
    /** 数据已存在 */
    IS_EXISTS(2002, "数据已存在"),
    /** 用户名不能为空 */
    USER_NAME_NOT_NULL(2003, "用户名不能为空"),
    /** 密码不能为空 */
    PASSWORD_NOT_NULL(2004, "密码不能为空"),
    /** 用户名或密码错误 */
    USER_PASSWORD_ERROR(2005, "用户名或密码错误"),
    /** IP不在范围之内 */
    USER_IP_RANGE(2006, "IP不在范围之内"),
    /** 您的密码不正确 */
    PASSWORD_ERROR(2007, "您的密码不正确"),
    /** 验证码不正确 */
    CAPCHA_ERROR(2008, "验证码不正确"),
    /** 保存失败 */
    SAVE_ERROR(2009, "保存失败"),
    /** 查询失败 */
    QUERY_ERROR(2010, "查询失败"),
    /** 更新失败 */
    UPDATE_ERROR(2011, "更新失败"),
    /** 删除失败 */
    DELETE_ERROR(2012, "删除失败");

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
    ErrorInfoEnum(Integer code, String name) {
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
    public static ErrorInfoEnum getCode(Integer code) {
        Optional<ErrorInfoEnum> result = Arrays.stream(ErrorInfoEnum.values()).filter(e -> e.getOrdinal().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

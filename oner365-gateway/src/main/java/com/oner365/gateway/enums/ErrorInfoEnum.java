package com.oner365.gateway.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 错误编码和信息
 * 
 * @author zhaoyong
 */
public enum ErrorInfoEnum implements Serializable {

    /** token 验证不正确! */
    CODE_401(401, "token 验证不正确!"),
    /** 请求地址不存在，请联系管理员! */
    CODE_404(404, "请求地址不存在，请联系管理员!"),
    /** 服务器错误，请联系管理员! */
    CODE_500(500, "服务器错误，请联系管理员!"),
    /** 服务未找到，请联系管理员! */
    CODE_503(503, "服务未找到，请联系管理员!");

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
    public static ErrorInfoEnum getCode(Integer code) {
        Optional<ErrorInfoEnum> result = Arrays.stream(ErrorInfoEnum.values()).filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}

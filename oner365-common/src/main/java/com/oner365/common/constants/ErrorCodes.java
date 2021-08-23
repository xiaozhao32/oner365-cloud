package com.oner365.common.constants;

/**
 * 定义本模块API的错误编码
 * @author lt
 */
public class ErrorCodes {

    /**
     * 参数错误
     */
    public static final int ERR_PARAM = 2001;

    /**
     * 数据已存在
     */
    public static final int ERR_IS_EXISTS = 2002;

    /**
     * 用户名不能为空
     */
    public static final int ERR_USER_NAME_NOT_NULL = 2003;

    /**
     * 密码不能为空
     */
    public static final int ERR_PASSWORD_NOT_NULL = 2004;

    /**
     * 用户未找到
     */
    public static final int ERR_USER_NOT_FOUND = 2005;

    /**
     * 您的IP不在范围之内
     */
    public static final int ERR_USER_IP_RANGE = 2006;

    /**
     * 您的密码不正确
     */
    public static final int ERR_PASSWORD_ERROR = 2007;

    private ErrorCodes() {

    }
}

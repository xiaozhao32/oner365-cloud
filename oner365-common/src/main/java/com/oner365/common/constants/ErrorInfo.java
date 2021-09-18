package com.oner365.common.constants;

/**
 * 定义本模块API的错误信息
 * @author lt
 */
public class ErrorInfo {

    /**
     * 参数错误
     */
    public static final String ERR_PARAM = "参数错误";

    /**
     * 数据已存在
     */
    public static final String ERR_IS_EXISTS = "数据已存在";

    /**
     * 用户名不能为空
     */
    public static final String ERR_USER_NAME_NOT_NULL = "用户名不能为空";

    /**
     * 密码不能为空
     */
    public static final String ERR_PASS_NOT_NULL = "密码不能为空";

    /**
     * 用户未找到
     */
    public static final String ERR_USER_NOT_FOUND = "用户名或密码错误";

    /**
     * 您的IP不在范围之内
     */
    public static final String ERR_USER_IP_RANGE = "您的IP不在范围之内";

    /**
     * 您的密码不正确
     */
    public static final String ERR_PASS_ERROR = "您的密码不正确";
    
    /**
     * 验证码不正确
     */
    public static final String ERR_CAPTCHA_ERROR = "验证码不正确!";

    /**
     * 保存失败
     */
    public static final String ERR_SAVE_ERROR = "保存失败!";
    
    /**
     * 查询失败
     */
    public static final String ERR_QUERY_ERROR = "查询失败!";
    
    /**
     * 删除失败
     */
    public static final String ERR_DELETE_ERROR = "删除失败!";
    
    private ErrorInfo() {

    }
}

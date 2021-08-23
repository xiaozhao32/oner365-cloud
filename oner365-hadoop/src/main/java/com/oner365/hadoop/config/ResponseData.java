package com.oner365.hadoop.config;

import java.io.Serializable;

/***
 * 服务响应对象
 * @author liutao
 */
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码 默认正常返回
     */
    private int code = 1;

    /**
     * 提示信息 默认成功
     */
    private String message = "success";

    /**
     * 返回数据结果
     */
    private T result;

    /**
     * 构造方法
     */
    public ResponseData() {

    }

    /**
     * 返回编码
     * @param code 编码
     */
    public ResponseData(int code) {
        this.code = code;
    }

    /**
     * 返回成功结果
     * @param result 结果
     */
    public ResponseData(T result) {
        this.result = result;
    }

    /**
     * 返回编码和消息
     * @param code 编码
     * @param message 消息
     */
    public ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回自定义结果
     * @param result T
     * @param code 编码
     * @param message 消息
     */
    public ResponseData(T result, int code, String message) {
        this.result = result;
        this.code = code;
        this.message = message;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}

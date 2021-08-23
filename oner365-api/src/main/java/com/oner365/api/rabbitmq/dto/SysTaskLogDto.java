package com.oner365.api.rabbitmq.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 任务日志dto
 * 
 * @author liutao
 */
public class SysTaskLogDto implements Serializable {
    private static final long serialVersionUID = 1L;


    /** 
     *    任务编号  
     */
    private String taskId;
    
    /** 
     *    执行任务状态
     */
    private String status;
    
    /** 
     *    执行任务信息
     */
    private String taskMessage;
    
    /** 
     *    执行任务异常信息
     */
    private String exceptionInfo;
    
    /** 
     *    执行任务服务ip
     */
    private String executeIp;
    
    /** 
     *    执行任务服务名称
     */
    private String executeServerName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(String taskMessage) {
        this.taskMessage = taskMessage;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public String getExecuteIp() {
        return executeIp;
    }

    public void setExecuteIp(String executeIp) {
        this.executeIp = executeIp;
    }

    public String getExecuteServerName() {
        return executeServerName;
    }

    public void setExecuteServerName(String executeServerName) {
        this.executeServerName = executeServerName;
    }
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

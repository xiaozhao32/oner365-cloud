package com.oner365.api.rabbitmq.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.oner365.common.enums.StatusEnum;

/**
 * 更新定时任务执行状态dto
 * 
 * @author liutao
 */
public class UpdateTaskExecuteSatusDto implements Serializable {
    private static final long serialVersionUID = 1L;


    /** 
     *    任务编号  
     */
    private String taskId;
    
    /** 
     *    执行任务状态
     */
    private StatusEnum executeStatus;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public StatusEnum getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(StatusEnum executeStatus) {
        this.executeStatus = executeStatus;
    }
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

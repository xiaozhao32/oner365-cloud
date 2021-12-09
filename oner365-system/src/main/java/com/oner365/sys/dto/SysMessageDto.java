package com.oner365.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.google.common.base.MoreObjects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 消息对象
 * @author zhaoyong
 */
@ApiModel(value = "系统消息")
public class SysMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 队列类型
     */
    @ApiModelProperty(value = "队列类型", required = true)
    private String queueType;

    /**
     * 队列标识
     */
    @ApiModelProperty(value = "队列标识")
    private String queueKey;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型")
    private String messageType;

    /**
     * 消息名称
     */
    @ApiModelProperty(value = "消息名称")
    private String messageName;

    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型id")
    private String typeId;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String context;

    /**
     * 发送者
     */
    @ApiModelProperty(value = "发送者")
    private String sendUser;

    /**
     * 接收者
     */
    @ApiModelProperty(value = "接收者")
    private String receiveUser;

    /**
     * 创建时间 create_time
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间 update_time
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * Constructor
     */
    public SysMessageDto() {
        super();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the queueType
     */
    public String getQueueType() {
        return queueType;
    }

    /**
     * @param queueType the queueType to set
     */
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    /**
     * @return the queueKey
     */
    public String getQueueKey() {
        return queueKey;
    }

    /**
     * @param queueKey the queueKey to set
     */
    public void setQueueKey(String queueKey) {
        this.queueKey = queueKey;
    }

    /**
     * @return the messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the messageName
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * @param messageName the messageName to set
     */
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    /**
     * @return the typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return the sendUser
     */
    public String getSendUser() {
        return sendUser;
    }

    /**
     * @param sendUser the sendUser to set
     */
    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    /**
     * @return the receiveUser
     */
    public String getReceiveUser() {
        return receiveUser;
    }

    /**
     * @param receiveUser the receiveUser to set
     */
    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    /**
     * @return the createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}

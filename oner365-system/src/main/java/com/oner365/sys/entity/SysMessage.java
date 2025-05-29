package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.enums.MessageTypeEnum;

/**
 * 消息对象
 *
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_message")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
    private String id;

    /**
     * 队列类型
     */
    @Column(name = "queue_type", nullable = false, length = 64)
    private String queueType;

    /**
     * 队列标识
     */
    @Column(name = "queue_key", nullable = false, length = 64)
    private String queueKey;

    /**
     * 消息类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageTypeEnum messageType;

    /**
     * 消息名称
     */
    @Column(name = "message_name", length = 64)
    private String messageName;

    /**
     * 类型ID
     */
    @Column(name = "type_id", length = 64)
    private String typeId;

    /**
     * 消息内容
     */
    @Column(name = "context")
    private String context;

    /**
     * 发送者
     */
    @Column(name = "send_user", length = 32)
    private String sendUser;

    /**
     * 接收者
     */
    @Column(name = "receive_user", length = 32)
    private String receiveUser;

    /**
     * 状态 status
     */
    @Enumerated
    @Column(name = "status", nullable = false)
    private MessageStatusEnum status;

    /**
     * 创建时间 create_time
     */
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间 update_time
     */
    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;

    /**
     * Constructor
     */
    public SysMessage() {
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
    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageTypeEnum messageType) {
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
     * @return the status
     */
    public MessageStatusEnum getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(MessageStatusEnum status) {
        this.status = status;
    }

}

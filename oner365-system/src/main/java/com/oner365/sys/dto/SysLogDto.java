package com.oner365.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.google.common.base.MoreObjects;

/**
 * 系统日志对象
 *
 * @author zhaoyong
 */
public class SysLogDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    private String id;

    /**
     * 操作ip
     */
    private String operationIp;

    /**
     * 请求方式
     */
    private String methodName;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 请求地址
     */
    private String operationPath;

    /**
     * 请求内容
     */
    private String operationContext;

    /**
     * 创建时间 create_time
     */
    private LocalDateTime createTime;

    /**
     * Constructor
     */
    public SysLogDto() {
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
     * @return the operationIp
     */
    public String getOperationIp() {
        return operationIp;
    }

    /**
     * @param operationIp the operationIp to set
     */
    public void setOperationIp(String operationIp) {
        this.operationIp = operationIp;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the operationName
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * @param operationName the operationName to set
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
     * @return the operationPath
     */
    public String getOperationPath() {
        return operationPath;
    }

    /**
     * @param operationPath the operationPath to set
     */
    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }

    /**
     * @return the operationContext
     */
    public String getOperationContext() {
        return operationContext;
    }

    /**
     * @param operationContext the operationContext to set
     */
    public void setOperationContext(String operationContext) {
        this.operationContext = operationContext;
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
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}

package com.oner365.elasticsearch.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.boot.logging.LogLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.oner365.data.commons.util.DateUtil;

/**
 * 应用日志
 *
 * @author zhaoyong
 */
@Document(indexName = "applicationlog")
@Setting(refreshInterval = "-1")
public class ApplicationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 线程名称
     */
    @Field(name = "thread_name")
    private String threadName;

    /**
     * 版本
     */
    @Field(name = "@version")
    private String version;

    /**
     * 消息内容
     */
    @Field(name = "message", type = FieldType.Keyword)
    private String message;

    /**
     * 日志级别
     */
    @Field(name = "level", type = FieldType.Keyword)
    private LogLevel level;

    /**
     * 类名称
     */
    @Field(name = "logger_name", type = FieldType.Keyword)
    private String loggerName;

    /**
     * 项目名称
     */
    @Field(name = "LOG_NAME", type = FieldType.Keyword)
    private String projectName;

    /**
     * 创建时间
     */
    @Field(name = "createTime", type = FieldType.Date, pattern = DateUtil.FULL_UTC_TIME_FORMAT)
    private LocalDateTime createTime;

    /**
     * 构造方法
     */
    public ApplicationLog() {
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
     * @return the threadName
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * @param threadName the threadName to set
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
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

    /**
     * @return the loggerName
     */
    public String getLoggerName() {
        return loggerName;
    }

    /**
     * @param loggerName the loggerName to set
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
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
     * @return the level
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(LogLevel level) {
        this.level = level;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}

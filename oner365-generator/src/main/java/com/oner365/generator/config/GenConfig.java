package com.oner365.generator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取代码生成相关配置
 *
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "gen")
public class GenConfig {

    /** 作者 */
    private String author;

    /** 生成包路径 */
    private String packageName;

    /** 自动去除表前缀，默认是false */
    private boolean autoRemovePre;

    /** 表前缀(类名不会包含表前缀) */
    private String tablePrefix;

    public GenConfig() {
        super();
    }

    public String getAuthor() {
        return author;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the autoRemovePre
     */
    public boolean getAutoRemovePre() {
        return autoRemovePre;
    }

    /**
     * @param autoRemovePre the autoRemovePre to set
     */
    public void setAutoRemovePre(boolean autoRemovePre) {
        this.autoRemovePre = autoRemovePre;
    }

    /**
     * @return the tablePrefix
     */
    public String getTablePrefix() {
        return tablePrefix;
    }

    /**
     * @param tablePrefix the tablePrefix to set
     */
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

}

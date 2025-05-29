package com.oner365.spark.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Spark 配置类
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "spring.spark")
public class SparkConfigProperties {

    private String master;

    private String appName;

    private String home;

    private String eventLogDir;

    public SparkConfigProperties() {
        super();
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getEventLogDir() {
        return eventLogDir;
    }

    public void setEventLogDir(String eventLogDir) {
        this.eventLogDir = eventLogDir;
    }

}

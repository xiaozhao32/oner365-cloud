package com.oner365.swagger.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置
 *
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /** 名称 */
    private String name;

    /** 地址 */
    private String url;

    /** 描述 */
    private String description;

    /** 版本 */
    private String version;

    /** 邮箱 */
    private String email;

    /**
     * 构造方法
     */
    public SwaggerProperties() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

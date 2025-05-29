package com.oner365.data.commons.config.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 白名单配置
 *
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "ignore")
public class IgnoreWhiteProperties {

    /**
     * 不校验列表
     */
    private List<String> whites = new ArrayList<>();

    /**
     * 构造方法
     */
    public IgnoreWhiteProperties() {
        super();
    }

    /**
     * @return the whites
     */
    public List<String> getWhites() {
        return whites;
    }

    /**
     * @param whites the whites to set
     */
    public void setWhites(List<String> whites) {
        this.whites = whites;
    }

}

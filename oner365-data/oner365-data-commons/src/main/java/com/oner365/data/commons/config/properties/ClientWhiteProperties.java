package com.oner365.data.commons.config.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 白名单配置
 *
 * @author liutao
 */
@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientWhiteProperties {

    /**
     * 校验列表
     */
    private List<String> whites = new ArrayList<>();

    /**
     * 私有key ssl证书提取
     */
    private String privateKey;

    /**
     * 构造方法
     */
    public ClientWhiteProperties() {
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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

}

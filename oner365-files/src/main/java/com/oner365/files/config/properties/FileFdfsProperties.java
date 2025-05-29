package com.oner365.files.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * File Fdfs properties
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "fdfs")
public class FileFdfsProperties {

    /**
     * id
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 账号
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    /**
     * 存储地址
     */
    @Value("${fdfs.storage.path}")
    private String path;

    /**
     * 构造方法
     */
    public FileFdfsProperties() {
        super();
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}

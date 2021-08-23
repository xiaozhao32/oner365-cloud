package com.oner365.monitor.deploy;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 部署对象
 * @author zhaoyong
 */
public class DeployServer implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String serviceName;

    /**
     * 构建参数 {
     *         IP, 端口, 帐号, 密码
     * }
     */
    public DeployServer() {
        
    }
    
    public DeployServer(String ip, Integer port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}

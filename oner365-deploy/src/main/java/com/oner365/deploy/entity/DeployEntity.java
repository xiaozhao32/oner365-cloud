package com.oner365.deploy.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 部署对象
 *
 * @author zhaoyong
 *
 */
public class DeployEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 部署路径 */
    private String name;

    /** 项目路径 */
    private String location;

    /** 版本 */
    private String version;

    /** 部署路径 */
    private String suffix;

    /** 部署环境 */
    private String active;

    /** 项目名称 */
    private List<String> projects = new ArrayList<>();

    /** 项目端口 */
    private Map<String, Integer> proejctPorts;

    /** jar包 */
    private List<String> libs = new ArrayList<>();

    private String serverAddr;

    /**
     * 构造方法
     */
    public DeployEntity() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public List<String> getLibs() {
        return libs;
    }

    public void setLibs(List<String> libs) {
        this.libs = libs;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * @return the proejctPorts
     */
    public Map<String, Integer> getProejctPorts() {
        return proejctPorts;
    }

    /**
     * @param proejctPorts the proejctPorts to set
     */
    public void setProejctPorts(Map<String, Integer> proejctPorts) {
        this.proejctPorts = proejctPorts;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

}

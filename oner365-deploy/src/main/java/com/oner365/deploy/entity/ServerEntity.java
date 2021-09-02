package com.oner365.deploy.entity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * Server 对象
 * @author zhaoyong
 *
 */
public class ServerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** 部署路径 */
    private String serverName;
    
    private Boolean isDeploy;
    
    private List<Server> serverList;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Boolean getIsDeploy() {
        return isDeploy;
    }

    public void setIsDeploy(Boolean isDeploy) {
        this.isDeploy = isDeploy;
    }
    
    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

package com.oner365.deploy.service;

import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;

/**
 * 部署服务
 *
 * @author zhaoyong
 *
 */
public interface DeployService {

    /**
     * 本地部署
     * @return DeployEntity
     */
    DeployEntity getDeployEntity();

    /**
     * 服务器部署
     * @return ServerEntity
     */
    ServerEntity getServerEntity();

}

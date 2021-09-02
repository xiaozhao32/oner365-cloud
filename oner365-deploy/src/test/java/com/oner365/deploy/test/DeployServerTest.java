package com.oner365.deploy.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.utils.DeployMethod;
import com.oner365.deploy.utils.DeployUtils;

/**
 * 自动化本地项目
 *
 * 部署到本地：请先执行 maven build 生成jar到 target 下在运行
 *
 * @author zhaoyong
 *
 */
public class DeployServerTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployServerTest.class);
    
    @Test
    public void deployNativeTest() {
        DeployEntity deploy = DeployUtils.getDeployEntity();
        ServerEntity server = DeployUtils.getServerEntity();
        LOGGER.info("Deploy project: {}", server);
        LOGGER.info("Server: {}", server);
        // 部署服务器目录
        if (server.getIsDeploy()) {
            DeployMethod.deployServer(deploy, server);
        }
    }
}

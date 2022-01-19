package com.oner365.deploy.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.service.DeployService;
import com.oner365.deploy.service.impl.DeployServiceImpl;
import com.oner365.deploy.utils.DeployMethod;

/**
 * 自动化本地项目
 *
 * 部署到本地：请先执行 maven build 生成jar到 target 下在运行
 *
 * @author zhaoyong
 *
 */
public class DeployTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeployTest.class);
  
  private DeployService deployService = new DeployServiceImpl();

  /**
   * 本地部署
   */
  @Test
  public void deployNativeTest() {
    DeployEntity entity = deployService.getDeployEntity();
    LOGGER.info("Deploy project: {}", entity);
    // 部署目录
    DeployMethod.deployNative(entity);
  }

  /**
   * 服务器部署
   */
  @Test
  public void deployServerTest() {
    DeployEntity deploy = deployService.getDeployEntity();
    ServerEntity server = deployService.getServerEntity();
    LOGGER.info("Deploy project: {}", server);
    LOGGER.info("Server: {}", server);
    // 部署服务器开关
    if (server.getIsDeploy()) {
      DeployMethod.deployServer(deploy, server);
    }
  }
}

package com.oner365.deploy.test;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.service.DeployService;
import com.oner365.deploy.utils.DeployMethod;

/**
 * 自动化本地项目
 *
 * 部署到本地：请先执行 maven build 生成jar到 target 下在运行
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
@ActiveProfiles("lt")
class DeployTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeployTest.class);
  
  @Resource
  private DeployService deployService;

  /**
   * 本地部署
   */
  @Test
  void deployNativeTest() {
    DeployEntity deploy = deployService.getDeployEntity();
    ServerEntity server = deployService.getServerEntity();
    LOGGER.info("Deploy project: {}", deploy);
    Assertions.assertNotNull(server);
    // 部署目录
    DeployMethod.deployNative(deploy, server);
  }

  /**
   * 服务器部署
   */
  @Test
  void deployServerTest() {
    DeployEntity deploy = deployService.getDeployEntity();
    ServerEntity server = deployService.getServerEntity();
    LOGGER.info("Deploy project: {}", server);
    LOGGER.info("Server: {}", server);
    Assertions.assertNotNull(server);
    // 部署服务器开关
    if (server.getIsDeploy()) {
      DeployMethod.deployServer(deploy, server);
    }
  }
}

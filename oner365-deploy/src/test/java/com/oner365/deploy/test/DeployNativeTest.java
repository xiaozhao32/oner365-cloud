package com.oner365.deploy.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.deploy.entity.DeployEntity;
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
public class DeployNativeTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployNativeTest.class);
    
    @Test
    public void deployNativeTest() {
        DeployEntity entity = DeployUtils.getDeployEntity();
        LOGGER.info("Deploy project: {}", entity);
        // 部署目录
        DeployMethod.deployNative(entity);
    }
}
